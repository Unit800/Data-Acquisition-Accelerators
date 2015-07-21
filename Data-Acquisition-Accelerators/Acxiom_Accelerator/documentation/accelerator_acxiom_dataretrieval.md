Configuring the data retrieval components of the IBM i2 Accelerator for Acxiom IDOD
===================================================================================

The data retrieval components of the IBM i2 Accelerator for Acxiom IDOD send a user's query to Acxiom, then create a valid results subset and return it to the Intelligence Analysis Platform. Follow these instructions first to deploy and configure the data retrieval components of the IBM i2 Accelerator for Acxiom IDOD.

Before you begin
----------------

-   Contact Acxiom to obtain an Acxiom API service user ID to use with the IBM i2 Accelerator for Acxiom IDOD. For information on the Acxiom API and how to arrange access to it, see the Acxiom Developer Connection portal at https://developer.myacxiom.com/overview.
-   Deploy a new installation of Intelligence Analysis Platform 3.0.7 Developer Essentials, according to the instructions in the GitHub repository for IBM i2 Intelligence Analysis Platform Developer Essentials. The IBM i2 Accelerator for Acxiom IDOD requires a new Developer Essentials deployment without a data access on-demand connector and cannot be used with existing deployments.

About this task
---------------

The IBM i2 Accelerator for Acxiom IDOD consists of three components:
-   A user interface extension that creates a custom element in the Intelligence Portal to enable users to query Acxiom consumer data. The user interface extension displays a web page that provides the query interface to the user as a web content form.
-   A web service that handles the user's query to the Acxiom API, and produces a valid results subset for the Intelligence Analysis Platform.
-   An Intelligence Analysis Platform connector that implements the `IExternalDataSubsetLocator` interface to retrieve the results subset from the web service, and return it to the Intelligence Analysis Platform for display in the user interface extension.

The instructions in this task explain how to set up the web service and the Intelligence Analysis Platform connector, which are the data retrieval components of the accelerator. These components are deployed on the `acxiomread` server in the same web archive (WAR) file. When you have completed this task, you must also configure and deploy the Intelligence Portal user interface extension.

Procedure
---------

1.  <span class="ph cmd">Copy the `Acxiom_Accelerator` folder from the GitHub repository for IBM i2 Data Acquisition Accelerators to your disk. </span> This folder contains the directories with the data retrieval components and the user interface extension component of the IBM i2 Accelerator for Acxiom IDOD. The folder also contains the documentation for the accelerator.
2.  <span class="ph cmd">Copy the `acxiom-example` directory from the `Acxiom_Accelerator\eclipse-project` directory in the downloaded folder, to the `IAP-Deployment-Toolkit\SDK\sdk-projects` directory in Developer Essentials, so that its location is `IAP-Deployment-Toolkit\SDK\sdk-projects\acxiom-example`.</span> This directory has the data retrieval components of the accelerator.
3.  <span class="ph cmd">Deploy the Counter Fraud schema in your new Developer Essentials deployment.</span>
    1.  <span class="ph cmd">Copy the files `counter-fraud-schema.xml` and `counter-fraud-schema-charting-schemes.xml` from the `Acxiom_Accelerator\artifacts` directory in the downloaded folder, into the directory `IAP-Deployment-Toolkit\configuration\fragments\common\WEB-INF\classes`.</span>
    2.  <span class="ph cmd">Using a text editor, open the file `IAP-Deployment-Toolkit\configuration\fragments\common\WEB-INF\classes\ApolloServerSettingsMandatory.properties` and specify the following properties:</span>

        ``` pre
        SchemaResource=counter-fraud-schema.xml
        ChartingSchemesResource=counter-fraud-schema-charting-schemes.xml
        ```

    3.  <span class="ph cmd">Refresh your Developer Essentials deployment with the new schema. Open a command prompt as Administrator, navigate to the `IAP-Deployment-Toolkit\scripts` directory, and run the following commands:</span>

        ``` pre
        python deploy.py -s write -t clear-data
        python deploy.py -s read -t clear-data
        python deploy.py -s write -t deploy-liberty
        python deploy.py -s read -t deploy-liberty
        ```

4.  <span class="ph cmd">If the `shared` directory is not already in your Eclipse workspace, add it now.</span>
    1.  <span class="ph cmd">In Eclipse, click <span class="ph menucascade">**Window** \> **Preferences**</span>.</span>
    2.  <span class="ph cmd">In the <span class="keyword wintitle">Preferences</span> window, select <span class="ph menucascade">**General** \> **Workspace** \> **Linked Resources**</span>.</span>
    3.  <span class="ph cmd">Add an entry to the **Defined path variables** list:</span>
        -   Name: `TOOLKIT_ROOT`
        -   Location: `C:\IBM\iap-3.0.7\IAP-Deployment-Toolkit`

    4.  <span class="ph cmd">In Eclipse, click <span class="ph menucascade">**File** \> **Import**</span>.</span>
    5.  <span class="ph cmd">In the <span class="keyword wintitle">Import</span> window, click <span class="ph menucascade">**General** \> **Existing Projects into Workspace**</span>, and then click **Next**.</span>
    6.  <span class="ph cmd">Click **Browse** at the top of the window, and then select the `IAP-Deployment-Toolkit\SDK\sdk-projects\shared` directory.</span>
    7.  <span class="ph cmd">Click **Finish** to complete the import process.</span>

    The Acxiom project in Developer Essentials requires links to the Intelligence Analysis Platform libraries and to the API documentation, which are configured in the `shared` directory. The shared libraries and the Acxiom project rely on the presence of `TOOLKIT_ROOT` in your development environment.
5.  Optional: <span class="ph cmd">If the development version of the Intelligence Analysis Platform is running, stop the servers from Eclipse.</span> Stop the read server first, followed by the write server.
6.  <span class="ph cmd">Make a copy of the `configuration\environment\newread\` directory from the Deployment Toolkit for the development deployment, in the same location. Name the copy `acxiomread`, so that the location is `configuration\environment\acxiomread\`. </span>
7.  <span class="ph cmd">Complete the `environment.properties` configuration file in the `acxiomread` directory with the same values that you specified when you configured the standard read server.</span>
8.  <span class="ph cmd">Edit the `port-def.props` configuration file in the `acxiomread` directory. Change all the port numbers so that they are unique in your system. </span> For example, you could use the port numbers 20035 to 20048.
9.  <span class="ph cmd">Open the topology file that represents your deployment of the Intelligence Analysis Platform in an XML editor.</span> By default, this file is at `IAP-Deployment-Toolkit\configuration\environment\topology.xml`.
10. <span class="ph cmd">Edit the topology file to add the following `<application>` element immediately before the closing tag of the `<applications>` element:</span>

    ``` pre
       ...
       <application name="acxiomread" host-name="localhost">
          <wars>
          </wars>
       </application>
    </applications>
    ```

    The new `<application>` element declares that your deployment contains an extra application. The element is also a target for the deployment scripts, which can now automate much of the rest of the configuration process.

11. <span class="ph cmd">You need to add an `<iap-data-source>` element to the topology file that represents the new data source. Open a command prompt as Administrator, navigate to the `IAP-Deployment-Toolkit\scripts` directory, and run the following command:</span>

    ``` pre
    python da-setup.py -t add-daod-data-source -a acxiomread -d acxiom-example
    ```

    <span class="importanttitle">Important:</span> For successful integration with Eclipse, the value that you provide to the `-d` option must match the name of your IBM i2 Accelerator for Acxiom IDOD project in Eclipse.

    When the command runs successfully, it modifies `topology.xml` to add information about a new data source, which it associates with the `acxiomread` application. By default, the data source gets the same name as the value that you provide to the `-d` option.

12. <span class="ph cmd">Reopen the topology file, and edit the `<iap-data-source>` element that defines the new data access on-demand data source so that its attributes reflect the functionality of the IBM i2 Accelerator for Acxiom IDOD:</span>

    ``` pre
    <iap-data-source id="acxiom-example-id" ar="false">
       <DataSource EdrsPresent="true" ScsPresent="true" SesPresent="true"
                   ScsBrowseSupported="true" ScsSearchSupported="true"
                   ScsNetworkSearchSupported="false"
                   ScsDumbbellSearchSupported="false"
                   ScsFilteredSearchSupported="false"
                   EdrsGetContextSupported="true"
                   EdrsGetLatestItemsSupported="false" Id="" Version="0">
          <Shape>DAOD</Shape>
          <Name>acxiom-example</Name>
       </DataSource>
    </iap-data-source>
    ```

13. <span class="ph cmd">Edit the Lucene index settings for the new application so that they are distinct from the settings for all the other applications in the topology file:</span>

    ``` pre
    <application name="acxiomread" host-name="localhost">
       <lucene-indexes>
          <lucene-index id="daod-acxiom" ... />
       </lucene-indexes>
       <wars>
          <war target="daod" name="acxiom-example"
               iap-datasource-id="acxiom-example-id">
             <lucene-index-ids>
                <lucene-index-id value="daod-acxiom"/>
             </lucene-index-ids>
             ...
    ```

14. <span class="ph cmd">Run the following command to register the new data source with other parts of the Intelligence Analysis Platform deployment:</span>

    ``` pre
    python deploy.py -s write -t register-data-sources
    ```

    Registering the data source also generates a GUID that is used to refer to it from elsewhere in the deployment, including in its URL.

15. <span class="ph cmd">In Windows Explorer, navigate to the `IAP-Deployment-Toolkit\configuration\environment\dsid` directory.</span>

    The `dsid` directory is populated and maintained by the deployment scripts. This directory contains settings files for each of the data sources that are defined in `topology.xml`.

16. <span class="ph cmd">Open the `dsid.acxiom-example-id.properties` file in a text editor. Copy the value of the `DataSourceId` property, which is the GUID that registration generated.</span>
17. <span class="ph cmd">Open `IAP-Deployment-Toolkit\configuration\fragments\write\ApolloClientSettings.xml` in a text editor. Add these lines to the end of the file, just before the `</settings>` closing tag:</span>

    ``` pre
    <AcxiomUIURL>Acxiom.html</AcxiomUIURL>
    <AcxiomDaodExternalContextRoot>DataSourceId</AcxiomDaodExternalContextRoot>
    ```

    Replace `DataSourceId` with the identifier that you copied from the `dsid.acxiom-example-id.properties` file.

    This setting provides code in the user interface extension for the accelerator with the location of the resource that it must use to respond to user requests.

18. <span class="ph cmd">Redeploy the Intelligence Analysis Platform by running similar commands to the ones that you used to deploy the platform originally:</span>

    ``` pre
    python deploy.py -s write -t deploy-liberty
    python deploy.py -s acxiomread -t deploy-liberty
    ```

    The command that targets the write server redeploys it with information about the new server on the read side. The command that targets the `acxiomread` server deploys that server for the first time.

    <span class="notetitle">Note:</span> Sometimes, the first `deploy-liberty` command can fail. It displays the following error message:
    ``` pre
    AMQ6004: An error occurred during WebSphere MQ initialization or ending.
    ```

    The failure occurs when the command attempts to delete and re-create the WebSphere MQ queue manager. To resolve the problem, use WebSphere MQ Explorer to delete the queue manager, and then rerun the `deploy-liberty` command.

19. <span class="ph cmd">In the **Servers** tab at the bottom of the Eclipse application window, add another server to host the accelerator application:</span>
    1.  <span class="ph cmd">In the Eclipse application window, click the **Servers** tab.</span>
    2.  <span class="ph cmd">Right-click inside the tab, and then click <span class="ph menucascade">**New** \> **Server**</span>.</span>
    3.  <span class="ph cmd">In the <span class="keyword wintitle">New Server</span> window, click <span class="ph menucascade">**IBM** \> **WebSphere Application Server V8.5 Liberty Profile**</span> to select the server type.</span>
    4.  <span class="ph cmd">Change the **Server name** to `acxiomread`, and then click **Next**.</span>
    5.  <span class="ph cmd">From the **Liberty profile server** list, select **acxiomread**, and then click **Finish**.</span>
    6.  <span class="ph cmd">Back in the **Servers** tab, double-click the **acxiomread** server.</span> An **acxiomread** tab opens in the top part of the Eclipse application window.
    7.  <span class="ph cmd">Modify the settings in the new tab. Clear the <span class="ph uicontrol">Run applications directly from the workspace</span> check box, and click <span class="ph menucascade">**Publishing** \> **Never publish automatically**</span>.</span>
    8.  <span class="ph cmd">Press Ctrl+S to save the settings.</span>

20. <span class="ph cmd">Refresh the `shared` and `acxiom-example` projects in the Eclipse user interface to ensure that their current state is displayed.</span>
21. <span class="ph cmd">In your Eclipse workspace, open the <span class="ph filepath">WebSphere Application Server V8.5 Liberty Profile/servers/acxiomread/apps</span> folder, and delete the `acxiom-example.war` application, which is an incomplete application.</span> Next, you will add the complete accelerator application to the workspace.
22. <span class="ph cmd">Repeat the instructions that you followed when you added the `shared` directory to your Eclipse workspace. This time, add the `IAP-Deployment-Toolkit\SDK\sdk-projects\acxiom-example` directory to Eclipse.</span>
23. <span class="ph cmd">Using your Acxiom user credentials, download from Acxiom the `.wsdl` and `.xsd` files for the findpeople and comprehensivereport services, and save them to disk.</span> The Acxiom project in Eclipse contains a generated SOAP client that works with version 2.7 of these files. Download each file by opening a web browser and entering the appropriate URL from the following list to load the file. Then, enter your Axciom user credentials. When each file has loaded, select **Save As** in your web browser and save the file to disk. Rename each file as you save it, using the name specified in the following list:

    https://cert-services.acxiom.com/idod-findpeople-v2.7?wsdl  
    Save as `v201110-FindPeopleService.wsdl`

    https://cert-services.acxiom.com/idod-comprehensivereport-v2.7?wsdl  
    Save as `v201110-ComprehensiveReportService.wsdl`

    https://cert-services.acxiom.com/idod-findpeople-v2.7?xsd=v201101-global-common-Faults.xsd  
    Save as `v201101-global-common-Faults.xsd`

    https://cert-services.acxiom.com/idod-findpeople-v2.7?xsd=v201101-us-common-Name.xsd  
    Save as `v201101-us-common-Name.xsd`

    https://cert-services.acxiom.com/idod-findpeople-v2.7?xsd=v201101-us-common-Phone.xsd  
    Save as `v201101-us-common-Phone.xsd`

    https://cert-services.acxiom.com/idod-findpeople-v2.7?xsd=v201101-us-common-UnparsedAddress.xsd  
    Save as `v201101-us-common-UnparsedAddress.xsd`

    https://cert-services.acxiom.com/idod-comprehensivereport-v2.7?xsd=v201110-us-idod-common.xsd  
    Save as `v201110-us-idod-common.xsd`

    https://cert-services.acxiom.com/idod-comprehensivereport-v2.7?xsd=v201110-us-idod-comprehensivereport-request.xsd  
    Save as `v201110-us-idod-comprehensivereport-request.xsd`

    https://cert-services.acxiom.com/idod-comprehensivereport-v2.7?xsd=v201110-us-idod-comprehensivereport-response.xsd  
    Save as `v201110-us-idod-comprehensivereport-response.xsd`

    https://cert-services.acxiom.com/idod-comprehensivereport-v2.7?xsd=v201110-us-idod-comprehensivereport.xsd  
    Save as `v201110-us-idod-comprehensivereport.xsd`

    https://cert-services.acxiom.com/idod-findpeople-v2.7?xsd=v201110-us-idod-findpeople-request.xsd  
    Save as `v201110-us-idod-findpeople-request.xsd`

    https://cert-services.acxiom.com/idod-findpeople-v2.7?xsd=v201110-us-idod-findpeople-response.xsd  
    Save as `v201110-us-idod-findpeople-response.xsd`

    https://cert-services.acxiom.com/idod-findpeople-v2.7?xsd=v201110-us-idod-findpeople.xsd  
    Save as `v201110-us-idod-findpeople.xsd`

24. <span class="ph cmd">Copy all the downloaded `.wsdl` and `.xsd` files to the Acxiom Eclipse project, placing them in the directory <span class="ph filepath">acxiom-example/Java Resources/src/resources/com/ibm/i2/connectors/acxiom/resources/wsdl</span>. Then, refresh the `acxiom-example` project.</span>
25. <span class="ph cmd">Edit each of the `.wsdl` and `.xsd` files in the Eclipse project directory so that the schemaLocation attribute references your local copy of the file, rather than the Acxiom URL for the file.</span> 

For example, in the file `v201110-us-idod-findpeople.xsd`, change the schemaLocation attribute from: 

schemaLocation="https://cert-services.acxiom.com/idod-findpeople-v2.7?xsd=v201110-us-idod-findpeople.xsd" 

to 

schemaLocation="v201110-us-idod-findpeople.xsd". 

Edit all of the `.wsdl` and `.xsd` files in the same way.
26. <span class="ph cmd">Generate a default keystore on the `acxiomread` server.</span>
    1.  <span class="ph cmd">In the Eclipse Design view, open the `server.xml` file for the `acxiomread` server.</span>
    2.  <span class="ph cmd">Click **Add**, select **Keystore** from the list of elements, and click **OK**.</span>
    3.  <span class="ph cmd">Enter the ID defaultKeyStore. Click **Set** and enter a password of your choice. Then, save the `server.xml` file.</span> Do not use your Acxiom user credentials for this step.
    4.  <span class="ph cmd">Start the `acxiomread` server to create the default keystore. Note the location of the `keystore.jks` file for the default keystore, for example `../resources/security/keystore.jks`.</span>

27. <span class="ph cmd">Obtain the Acxiom certificate from the Acxiom website.</span> These instructions use Mozilla Firefox. If you are using another web browser, consult the documentation for your browser for instructions on how to export a certificate.
    1.  <span class="ph cmd">In Firefox, go to https://cert-services.acxiom.com:446/idod-comprehensivereport-v2.7 and supply your Acxiom user credentials.</span>
    2.  <span class="ph cmd">Click the lock icon that appears before the URL in the browser, then click <span class="ph uicontrol">More Information</span>.</span>
    3.  <span class="ph cmd">In the <span class="keyword wintitle">Page Info</span> window, select the **Security** tab, then click **View Certificate**.</span>
    4.  <span class="ph cmd">In the <span class="keyword wintitle">Certificate Viewer</span> window, select the **Details** tab, then click **Export**.</span>
    5.  <span class="ph cmd">Save the certificate to disk as a CRT file.</span>

28. <span class="ph cmd">Add the Acxiom certificate to the default keystore, using the Java keytool utility.</span> You must use the keytool utility from the same version of Java that is used by the `acxiomread` server.
    1.  <span class="ph cmd">Open a command prompt as Administrator.</span>
    2.  <span class="ph cmd">Navigate to the Java `bin` directory in your Java installation, and locate the keytool utility.</span>
    3.  <span class="ph cmd">Run the following command:</span>

        ``` pre
        keytool -importcert -file path_to_certificate -keystore path_to_keystore -alias "Alias"
        ```

        where path\_to\_certificate is the file path for your exported Acxiom certificate, and path\_to\_keystore is the file path for the `keystore.jks` file for the keystore that you created on the `acxiomread` server.

    4.  <span class="ph cmd">To verify that the Acxiom certificate was added, run the following command to view the certificate in the default keystore:</span>

        ``` pre
        keytool -list -v -keystore path_to_keystore
        ```

        where path\_to\_keystore is the file path for the `keystore.jks` file for the default keystore that you created on the `acxiomread` server.

29. <span class="ph cmd">In the IBM i2 Accelerator for Acxiom IDOD project in Eclipse, browse to the file `UserCredentials.properties` in the package `acxiom-example/src/main`. Set the values of the `AcxiomUsername` and `AcxiomPassword` properties to your Acxiom user credentials.</span>
30. <span class="ph cmd">In the **Servers** tab at the bottom of the Eclipse application window, right-click the **acxiomread** server and select <span class="ph uicontrol">Add and Remove</span>.</span>
31. <span class="ph cmd">In the <span class="keyword wintitle">Add and Remove</span> window, move **acxiom-example** from the **Available** list to the **Configured** list, and then click **Finish** to close the window.</span>
    <span class="notetitle">Note:</span> If the **Configured** list already contains an entry for **acxiom-example**, Eclipse displays a warning about moving the entry from the **Available** list. You can safely ignore the warning for the accelerator.

32. <span class="ph cmd">In Eclipse, start the write server, followed by the read server, and finally the `acxiomread` server.</span>

What to do next
---------------

The deployment of the data retrieval components of the IBM i2 Accelerator for Acxiom IDOD is complete. To interact with the data retrieval service, you must now configure the user interface extension for the accelerator. Follow the deployment instructions for the user interface extension in the document "Configuring the user interface extension for the IBM i2 Accelerator for Acxiom IDOD", in the GitHub repository for IBM i2 Data Acquisition Accelerators.



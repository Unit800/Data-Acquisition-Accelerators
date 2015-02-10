Configuring the data retrieval components of the DomainTools Accelerator
========================================================================

The data retrieval components of the DomainTools Accelerator send a user's query to DomainTools, then create a valid results subset and return it to the Intelligence Analysis Platform. Follow these instructions first to deploy and configure the data retrieval components of the DomainTools Accelerator.

Before you begin
----------------

-   Contact DomainTools to obtain a DomainTools API service key (apiKey) and a user ID to use with the DomainTools Accelerator. For information on the DomainTools API and how to arrange access to it, see www.domaintools.com/api/.
-   Deploy a new installation of Intelligence Analysis Platform 3.0.7 Developer Essentials, according to the instructions in the GitHub repository for IBM i2 Intelligence Analysis Platform Developer Essentials. The DomainTools Accelerator requires a new Developer Essentials deployment without a data access on-demand connector and cannot be used with existing deployments. Only the Law Enforcement schema that is deployed by default as part of Developer Essentials is supported for use with the DomainTools Accelerator.

About this task
---------------

The DomainTools Accelerator consists of three components:

-   A user interface extension that creates a custom element in the Intelligence Portal to enable users to query DomainTools. The user interface extension displays a web page that provides the query interface to the user as a web content form.
-   A RESTful web service that handles the user's query to DomainTools, and produces a valid results subset for the Intelligence Analysis Platform.
-   An Intelligence Analysis Platform connector that implements the `IExternalDataSubsetLocator` interface to retrieve the results subset from the web service, and return it to the Intelligence Analysis Platform for display in the user interface extension.

The instructions in this task explain how to set up the RESTful web service and the Intelligence Analysis Platform connector, which are the data retrieval components of the DomainTools Accelerator. These components are deployed on the `dtread` server in the same web archive (WAR) file. When you have completed this task, you must also configure and deploy the Intelligence Portal user interface extension.

Procedure
---------

1.  Copy the `DomainTools_Accelerator` folder from the GitHub repository for IBM i2 Data Acquisition Accelerators to your disk, and note its location. The installation instructions reference certain resources, the location of which might differ based on where the folder is located.
2.  Also, copy the `DomainTools_Accelerator` folder to the `IAP-Deployment-Toolkit\SDK\sdk-projects` directory in Developer Essentials.

The DomainTools project in Developer Essentials requires links to the Intelligence Analysis Platform libraries, and to the API documentation. These links are configured in a separate project directory that you must import into Eclipse before you can build the project.

1.  If the `shared` directory is not already in your Eclipse workspace, add it now.
    1.  In Eclipse, click **Window** \> **Preferences** to open the Preferences window.
    2.  In the Preferences window, select **General** \> **Workspace** \> **Linked Resources**.
    3.  Add an entry to the **Defined path variables** list:

        -   Name: `TOOLKIT_ROOT`
        -   Location: `C:\IBM\iap-3.0.7\IAP-Deployment-Toolkit`

        The shared libraries and the DomainTools project rely on the presence of `TOOLKIT_ROOT` in your development environment.

    4.  In Eclipse, click **File** \> **Import** to open the Import window.
    5.  In the Import window, click **General** \> **Existing Projects into Workspace**, and then click **Next**.
    6.  Click **Browse** at the top of the window, and then select the `IAP-Deployment-Toolkit\SDK\sdk-projects\shared` directory.
    7.  Click **Finish** to complete the import process.

Because it involves a new read server, the DomainTools Accelerator requires significant changes to your deployment of the Intelligence Analysis Platform. The scripts in the Deployment Toolkit can modify some settings automatically, but you must also make some manual alterations, and then redeploy the platform by hand.

1.  Optional: If the development version of the Intelligence Analysis Platform is running, stop the servers from Eclipse. Stop the read server first, followed by the write server.
2.  Make a copy of the `configuration\environment\newread\` directory from the Deployment Toolkit for the development deployment. Name the copy `dtread`, at the location `configuration\environment\dtread\`.
3.  Complete the `environment.properties` configuration file for the `dtread` server with the same values that you specified when you configured the standard read server. The configuration file is at `configuration\environment\dtread\environment.properties`.
4.  Edit the `port-def.props` configuration file for the `dtread` server, in the `configuration\environment\dtread\` directory. Change all the port numbers so that they are unique in your system. For example, you could use the port numbers 20035 to 20048.
5.  Open the topology file that represents your deployment of the Intelligence Analysis Platform in an XML editor. By default, this file is at `IAP-Deployment-Toolkit\configuration\environment\topology.xml`.
6.  Edit the topology file to add the following `<application>` element immediately before the closing tag of the `<applications>` element:

    ``` {.pre .codeblock}
       ...
       <application name="dtread" host-name="localhost">
          <wars>
          </wars>
       </application>
    </applications>
    ```

    The new `<application>` element declares that your deployment contains an extra application. The element is also a target for the deployment scripts, which can now automate much of the rest of the configuration process.

7.  You need to add an `<iap-data-source>` element to the topology file that represents the new data source. Open a command prompt as Administrator, and navigate to the `IAP-Deployment-Toolkit\scripts` directory.
8.  Run the following command:

    ``` {.pre .codeblock}
    python da-setup.py -t add-daod-data-source -a dtread -d domaintools
    ```

    Important: For successful integration with Eclipse, the value that you provide to the `-d` option must match the name of your DomainTools Accelerator project in Eclipse.

    When the command runs successfully, it modifies `topology.xml` to add information about a new data source, which it associates with the `dtread` application. By default, the data source gets the same name as the value that you provide to the `-d` option.

9.  Reopen the topology file, and edit the `<iap-data-source>` element that defines the new data access on-demand data source so that its attributes reflect the functionality of the DomainTools Accelerator:

    ``` {.pre .codeblock}
    <iap-data-source id="domaintools-id" ar="false">
       <DataSource EdrsPresent="true" ScsPresent="false" SesPresent="true"
                   ScsBrowseSupported="true" ScsSearchSupported="true"
                   ScsNetworkSearchSupported="false"
                   ScsDumbbellSearchSupported="false"
                   ScsFilteredSearchSupported="false"
                   EdrsGetContextSupported="true"
                   EdrsGetLatestItemsSupported="false" Id="" Version="0">
          <Shape>DAOD</Shape>
          <Name>domaintools</Name>
       </DataSource>
    </iap-data-source>
    ```

10. Edit the Lucene index settings for the new application so that they are distinct from the settings for all the other applications in the topology file:

    ``` {.pre .codeblock}
    <application name="dtread" host-name="localhost">
       <lucene-indexes>
          <lucene-index id="daod-domaintools" ... />
       </lucene-indexes>
       <wars>
          <war target="daod" name="domaintools"
               iap-datasource-id="domaintools-id">
             <lucene-index-ids>
                <lucene-index-id value="daod-domaintools"/>
             </lucene-index-ids>
             ...
    ```

11. Run the following command to register the new data source with other parts of the Intelligence Analysis Platform deployment:

    ``` {.pre .codeblock}
    python deploy.py -s write -t register-data-sources
    ```

    Registering the data source also generates a GUID that is used to refer to it from elsewhere in the deployment, including in its URL.

12. In Windows Explorer, navigate to the `IAP-Deployment-Toolkit\configuration\environment\dsid` directory.

    The `dsid` directory is populated and maintained by the deployment scripts. This directory contains settings files for each of the data sources that are defined in `topology.xml`.

13. Open the `dsid.domaintools-id.properties` file in a text editor. Copy the value of the `DataSourceId` property, which is the GUID that registration generated.
14. Open `IAP-Deployment-Toolkit\configuration\fragments\write\ApolloClientSettings.xml` in a text editor. Add this line to the end of the file, just before the `</settings>` closing tag:

    ``` {.pre .codeblock}
    <DomainToolsUIURL>http://address/DataSourceId/DomainTools.html</DomainToolsUIURL>
    ```

    Replace `address` with the full host name of the server on which IBM HTTP Server was installed as part of the Developer Essentials deployment. Replace `DataSourceId` with the identifier that you copied from the `dsid.domaintools-id.properties` file.

    This setting provides code in the user interface extension for the DomainTools Accelerator with the location of the resource that it must use to respond to user requests.

15. Redeploy the Intelligence Analysis Platform by running similar commands to the ones that you used to deploy the platform originally:

    ``` {.pre .codeblock}
    python deploy.py -s write -t deploy-liberty
    python deploy.py -s dtread -t deploy-liberty
    ```

    The command that targets the write server redeploys it with information about the new server on the read side. The command that targets the `dtread` server deploys that server for the first time.

    Note: Sometimes, the first `deploy-liberty` command can fail. It displays the following error message:

    ``` {.pre .codeblock}
    AMQ6004: An error occurred during WebSphere MQ initialization or ending.
    ```

    The failure occurs when the command attempts to delete and re-create the WebSphere MQ queue manager. To resolve the problem, use WebSphere MQ Explorer to delete the queue manager, and then rerun the `deploy-liberty` command.

16. On the write server, modify the `plugin-cfg.html` file in the `c:\IBM\HTTPServer\plugins\iap\config` directory. To the element `<UriGroup Name="dtread_Cluster_URIs">`, add the following elements:

    ``` {.pre .codeblock}
    <Uri AffinityCookie="JSESSIONID" AffinityURLIdentifier="jsessionid" Name="/DataSourceId/DomainTools.html"/>
    <Uri AffinityCookie="JSESSIONID" AffinityURLIdentifier="jsessionid" Name="/DataSourceId/rest/daodSubsets"/> 
    ```

    where `DataSourceId` is the DSID value that was generated for DomainTools. This value is in the `DataSourceId` property in the `dsid.domaintools-id.properties` file in the `IAP-Deployment-Toolkit\configuration\environment\dsid` directory.

17. Use the Services application in Windows (`services.msc`) to restart HTTP Server. Do not attempt to start the application server or the web applications yet.

The commands so far added an incomplete application to your Intelligence Analysis Platform deployment. (The application includes all the infrastructure for supporting data retrieval, but no actual implementation.) In Eclipse, you must create a server to host the application, remove the incomplete application from the workspace, and then replace it with the DomainTools Accelerator application.

1.  In the **Servers** tab at the bottom of the Eclipse application window, add another server:
    1.  In the Eclipse application window, click the **Servers** tab.
    2.  Right-click inside the tab, and then click **New** \> **Server** to open the New Server window.
    3.  In the New Server window, click **IBM** \> **WebSphere Application Server V8.5 Liberty Profile** to select the server type.
    4.  Change the **Server name** to `dtread`, and then click **Next**.
    5.  From the **Liberty profile server** list, select **dtread**, and then click **Finish**.
    6.  Back in the **Servers** tab, double-click the **dtread** server. A **dtread** tab opens in the top part of the Eclipse application window.
    7.  Modify the settings in the new tab:
        -   Clear the **Run applications directly from the workspace** check box.
        -   Click **Publishing** \> **Never publish automatically**.

    8.  Press Ctrl+s to save the settings.

2.  Refresh the Eclipse user interface to ensure that it reflects the current state of the project.
3.  In your Eclipse workspace, open the WebSphere Application Server V8.5 Liberty Profile/servers/dtread/apps folder, and delete the `domaintools.war` application.
4.  Repeat the instructions that you followed when you added the `shared` directory to your Eclipse workspace. This time, add the `IAP-Deployment-Toolkit\SDK\sdk-projects\domaintools` directory to Eclipse.
5.  In Eclipse, browse to the file `DomainToolsSettings.properties` in the `domaintools` project. The file is located in **Java Resources** \> **src**. Update the values of the `apiUsername` and `apiKey` properties with the user ID and API service key you obtained from DomainTools.
6.  In the **Servers** tab at the bottom of the Eclipse application window, right-click the **dtread** server and select Add and Remove.
7.  In the Add and Remove window, move **domaintools** from the **Available** list to the **Configured** list, and then click **Finish** to close the window.
    Note: If the **Configured** list already contains an entry for **domaintools**, Eclipse displays a warning about moving the entry from the **Available** list to it. You can safely ignore the warning for the DomainTools Accelerator.
8.  In Eclipse, start the write server, followed by the read server, and finally the `dtread` server.

What to do next
---------------

The deployment of the data retrieval components of the DomainTools Accelerator is complete. To interact with the data retrieval service, you must now configure the user interface extension for the DomainTools Accelerator. Follow the deployment instructions for the user interface extension in the document "Configuring the user interface extension for the DomainTools Accelerator", in the GitHub repository for IBM i2 Data Acquisition Accelerators.

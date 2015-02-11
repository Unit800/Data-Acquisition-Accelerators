Configuring the user interface extension for the DomainTools Accelerator
========================================================================

The user interface extension component of the DomainTools Accelerator creates a custom element in the Intelligence Portal for IBM i2 Intelligence Analysis Platform. The user interface extension enables users to query DomainTools. When you have deployed and configured the data retrieval components of the DomainTools Accelerator, follow these instructions to deploy and configure the Intelligence Portal user interface extension.

Before you begin
----------------

-   Configure the data retrieval components of the DomainTools Accelerator, according to the instructions in the document "Configuring the data retrieval components of the DomainTools Accelerator", in the GitHub repository for IBM i2 Data Acquisition Accelerators. These components are a RESTful web service and an Intelligence Analysis Platform connector that search DomainTools and return the results.
-   Install the following prerequisites:
    -   Microsoft Visual Studio Express 2013 for Web
    -   Microsoft Silverlight 5 SDK
    -   Microsoft Silverlight 5 Developer Runtime

    These tools are available as free downloads from the Microsoft website.

About this task
---------------

The Intelligence Portal supports extensions to the user interface in the form of web pages. The portal hosts web pages in new tabs that users access through custom user interface elements.

The user interface extension for the DomainTools Accelerator connects to the RESTful web service that calls the DomainTools API. The user selects the **DomainTools** menu to open a tab containing a web content form. The user selects a search type and enters values into the text box, then clicks **Submit** to send a POST request to the web service. The Intelligence Analysis Platform connector then retrieves the results subset from the web service and returns it to the user interface extension, which displays the results subset in a new tab in the user interface. The user can select an item from the results using a left mouse click, then use a right mouse click to bring up a context menu with appropriate analysis options for the item type.

The user interface extension is written in C\# and uses Microsoft Silverlight. It is deployed on the write server in the `write.war` Web archive file.

Procedure
---------

When you installed Developer Essentials to deploy the data retrieval components of the DomainTools Accelerator, you added a `ui` directory to the Deployment Toolkit that contains a configuration file for user interface extensions. You must edit this file to provide the locations of `MSBuild.exe` (which can compile XAP files) and the directory that contains the extension code for the DomainTools Accelerator.

1.  Open the configuration file for user interface extensions in a text editor. By default, this file is at `IAP-Deployment-Toolkit\configuration\environment\ui\environment.properties`.
2.  Set the properties appropriately for your environment. Assuming that you used the default locations given in the deployment instructions for Developer Essentials, the correct values look like this example:

    ``` {.pre .codeblock}
    ui.project.dir=${toolkit.base.dir}/SDK/sdk-projects/domaintools UI
    msbuild.exe.path=C:/Windows/Microsoft.NET/Framework/v4.0.30319/MSBuild.exe
    ```

To add the DomainTools Accelerator user interface extension to the Intelligence Portal, you first extract the files from the standard web application: `Apollo.xap`. Then, you add the custom functionality to the standard content before you recompile and redeploy a new version of the XAP file.

1.  Open a command prompt as Administrator, and navigate to the `IAP-Deployment-Toolkit\scripts` directory.
2.  Run the following command:

    ``` {.pre .codeblock}
    python dev-ui.py -t dev-unpack-xap
    ```

    This command extracts the assemblies from the existing `Apollo.xap` file to the `extractedxap` directory of the DomainTools Accelerator project, which is located at

    ``` {.pre .codeblock}
    IAP-Deployment-Toolkit\SDK\sdk-projects\domaintools UI\extractedxap
    ```

3.  In Visual Studio, open the solution file at IAP-Deployment-Toolkit\\SDK\\sdk-projects\\domaintools UI\\domaintools\-visual\-studio\-project\\DomainTools.sln. The Visual Studio solution contains the `DomainTools` project that provides the user interface extension.
4.  Build the solution in debug mode. Visual Studio puts the DLL files that it generates in the directory

    ``` {.pre .codeblock}
    IAP-Deployment-Toolkit\SDK\sdk-projects\domaintools UI\build
    ```

    Note: If the build fails, it is likely that the references are pointing to a different location. When this happens, items in the reference list are marked as unresolved. To resolve the references, in Visual Studio Solution Explorer, add references in the `DomainTools` project to the binaries. The binaries are located in the `extractedxap` directory of the DomainTools Accelerator project. Then repeat the process for unresolved references in the `ExtensibilityHelper` project.

5.  Return to the command prompt, and run the following command to copy the components for the user interface extension to the `IAP-Deployment-Toolkit\configuration\fragments` location:

    ``` {.pre .codeblock}
    python dev-ui.py -t dev-update-fragments
    ```

    The `dev-update-fragments` task copies the built DLL files for the user interface extension to the directory `IAP-Deployment-Toolkit\configuration\fragments\xap-supplement`, and copies the HTML content for the user interface extension to the directory `IAP-Deployment-Toolkit\configuration\fragments\write`.

    Note: If you later decide to remove the DomainTools Accelerator, you must manually delete the built DLL files from `configuration\fragments\xap-supplement` and manually delete the recompiled XAP file from the `configuration\fragments\write` directory. You must then redo the next three steps.

6.  In Eclipse, ensure that the `dtread` server, the read server, and the write server are stopped. In the **Servers** tab, stop the `dtread` server, then the read server, and finally the write server.
7.  At the command prompt, run the following command to redeploy the write server:

    ``` {.pre .codeblock}
    python deploy.py -s write -t update-liberty
    ```

    As part of its work, the `update-liberty` task compiles your custom components from the `xap-supplement` directory into a new version of the XAP file.

8.  Back in Eclipse, in the **Servers** tab, start the write server, followed by the read server, and finally the `dtread` server.
9.  To test the updated deployment, open a web browser and navigate to http://localhost/apollo. Log in to the Intelligence Portal with any of the user names and passwords that are specified in `liberty-example-users.xml`, which you used during deployment of Developer Essentials.
10. In the menu bar, click **DomainTools** \> **Launch DomainTools Tab** to display the custom user interface.
11. Follow the on-screen instructions to request data from DomainTools and present it in the **DomainTools** tab.


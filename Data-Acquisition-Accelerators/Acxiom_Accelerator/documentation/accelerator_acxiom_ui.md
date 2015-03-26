Configuring the user interface extension for the IBM i2 Accelerator for Acxiom IDOD
===================================================================================

The user interface extension component of the IBM i2 Accelerator for Acxiom IDOD creates a custom element in the Intelligence Portal for IBM i2 Intelligence Analysis Platform. The user interface extension enables users to search consumer data from Acxiom. When you have deployed and configured the data retrieval components of the IBM i2 Accelerator for Acxiom IDOD, follow these instructions to deploy and configure the Intelligence Portal user interface extension.

Before you begin
----------------

-   Configure the data retrieval components of the IBM i2 Accelerator for Acxiom IDOD, according to the instructions in the document "Configuring the data retrieval components of the IBM i2 Accelerator for Acxiom IDOD", in the GitHub repository for IBM i2 Data Acquisition Accelerators. These components are a web service and an Intelligence Analysis Platform connector that search Acxiom data and return the results.
-   Install the following prerequisites:
    -   Microsoft Visual Studio Express 2013 for Web
    -   Microsoft Silverlight 5 SDK
    -   Microsoft Silverlight 5 Developer Runtime

    These tools are available as free downloads from the Microsoft website.

About this task
---------------

The Intelligence Portal supports extensions to the user interface in the form of web pages. The portal hosts web pages in new tabs that users access through custom user interface elements.

The user interface extension for the IBM i2 Accelerator for Acxiom IDOD connects to the web service that calls the Acxiom API. The user selects the **Acxiom Search** menu to open a tab containing a web content form. The user selects a search type and enters values into the text box, then clicks **Submit** to send a POST request to the web service. The Intelligence Analysis Platform connector then retrieves the results subset from the web service and returns it to the user interface extension, which displays the results subset in a new tab in the user interface.

The user interface extension is written in C\# and uses Microsoft Silverlight. It is deployed on the write server in the `write.war` Web archive file.

Procedure
---------

1.  <span class="ph cmd">Copy the `acxiom-UI` directory from the `Acxiom_Accelerator` folder that you downloaded from the GitHub repository for IBM i2 Data Acquisition Accelerators. Place the copied directory in the `IAP-Deployment-Toolkit\SDK\sdk-projects` directory in Developer Essentials, so that the location is `IAP-Deployment-Toolkit\SDK\sdk-projects\acxiom-UI`.</span> This directory has the user interface extension component of the accelerator.
2.  <span class="ph cmd">Create an `extractedxap` directory in the `acxiom-UI` directory, so that the location is `IAP-Deployment-Toolkit\SDK\sdk-projects\acxiom-UI\extractedxap`.</span>
3.  <span class="ph cmd">Open the configuration file for user interface extensions in a text editor. </span> By default, this file is at `IAP-Deployment-Toolkit\configuration\environment\ui\environment.properties`.
4.  <span class="ph cmd">Edit the configuration file to provide the locations of `MSBuild.exe` (which can compile XAP files) and the directory that contains the extension code for the IBM i2 Accelerator for Acxiom IDOD. Assuming that you used the default locations given in the deployment instructions for Developer Essentials, the correct values look like this example:</span>

    ``` pre
    ui.project.dir=C:/IBM/iap-3.0.7/IAP-Deployment-Toolkit/SDK/sdk-projects/acxiom-UI
    msbuild.exe.path=C:/Windows/Microsoft.NET/Framework/v4.0.30319/MSBuild.exe
    ```

5.  <span class="ph cmd">Open a command prompt as Administrator, and navigate to the `IAP-Deployment-Toolkit\scripts` directory.</span>
6.  <span class="ph cmd">Run the following command:</span>

    ``` pre
    python dev-ui.py -t dev-unpack-xap
    ```

    This command extracts the assemblies from the existing `Apollo.xap` file to the `extractedxap` directory of the accelerator project, which you created at `IAP-Deployment-Toolkit\SDK\sdk-projects\acxiom-UI\extractedxap`.

7.  <span class="ph cmd">In Visual Studio, open the solution file at `IAP-Deployment-Toolkit\SDK\sdk-projects\acxiom-UI\acxiom-visual-studio-project\Acxiom.sln`.</span> The Visual Studio solution contains the `Acxiom` project that provides the user interface extension.
8.  <span class="ph cmd">Build the solution in debug mode.</span> Visual Studio puts the DLL files that it generates in the directory `IAP-Deployment-Toolkit\SDK\sdk-projects\acxiom-UI\build`.
    <span class="notetitle">Note:</span> If the build fails, it is likely that the references are pointing to a different location. When this happens, items in the reference list are marked as unresolved. To resolve the references, in Visual Studio Solution Explorer, add references in the `Acxiom` project to the binaries. The binaries are located in the `extractedxap` directory of the accelerator project. Then repeat the process for unresolved references in the `ExtensibilityHelper` project.

9.  <span class="ph cmd">Return to the command prompt, and run the following command to copy the components for the user interface extension to the `IAP-Deployment-Toolkit\configuration\fragments` location:</span>

    ``` pre
    python dev-ui.py -t dev-update-fragments
    ```

    The `dev-update-fragments` task copies the built DLL files for the user interface extension to the directory `IAP-Deployment-Toolkit\configuration\fragments\xap-supplement`, and copies the HTML content for the user interface extension to the directory `IAP-Deployment-Toolkit\configuration\fragments\write`.
    <span class="notetitle">Note:</span> If you later decide to remove the IBM i2 Accelerator for Acxiom IDOD, you must manually delete the built DLL files from `configuration\fragments\xap-supplement` and manually delete the recompiled XAP file from the `configuration\fragments\write` directory. You must then redo the next three steps.

10. <span class="ph cmd">In Eclipse, in the **Servers** tab, stop the `acxiomread` server, then the read server, and finally the write server.</span>
11. <span class="ph cmd">At the command prompt, run the following command to redeploy the write server:</span>

    ``` pre
    python deploy.py -s write -t update-liberty
    ```

    As part of its work, the `update-liberty` task compiles your custom components from the `xap-supplement` directory into a new version of the XAP file.

12. <span class="ph cmd">Back in Eclipse, in the **Servers** tab, start the write server, followed by the read server, and finally the `acxiomread` server.</span>
13. <span class="ph cmd">To test the updated deployment, open a web browser and navigate to http://localhost/apollo. Log in to the Intelligence Portal with any of the user names and passwords that are specified in `liberty-example-users.xml`, which you used during deployment of Developer Essentials.</span>
14. <span class="ph cmd">In the menu bar, click <span class="ph menucascade">**Acxiom Search** \> **Acxiom Search property**</span> to display the custom user interface.</span>
15. <span class="ph cmd">Follow the on-screen instructions to request data from Acxiom and present it in the **Acxiom Search** tab.</span>



About the IBM i2 Accelerator for Acxiom IDOD
============================================

The IBM i2 Accelerator for Acxiom IDOD makes consumer data from Acxiom available through the Intelligence Portal for IBM i2 Intelligence Analysis Platform. For information on the demographic and behavioral data provided by Acxiom, see www.acxiom.com.

The IBM i2 Accelerator for Acxiom IDOD consists of Intelligence Analysis Platform extensions that are deployed using IBM i2 Intelligence Analysis Platform Developer Essentials. For information about Developer Essentials, see the GitHub repository for IBM i2 Intelligence Analysis Platform Developer Essentials.

To query the Acxiom data that your organization has licensed, the IBM i2 Accelerator for Acxiom IDOD requires an Acxiom API service user ID for access to the Acxiom APIs. For information on the Acxiom APIs and instructions on how to obtain an API key, see the Acxiom Developer Connection portal at https://developer.myacxiom.com/overview.

Capabilities
------------

The IBM i2 Accelerator for Acxiom IDOD makes consumer data from Acxiom available to users as a custom element in the Intelligence Portal. Users search the Acxiom data and receive the results in the form of entities and links that show data related to the search terms.

To access the Acxiom data, users select the **Acxiom Search** menu to open a tab in the Intelligence Portal. Users then select a search type, enter search terms, and click **Submit**. The results are displayed in a new tab in the Intelligence Portal.

Users can run the following predefined searches to retrieve consumer data through the IBM i2 Accelerator for Acxiom IDOD:
-   First name, Last name, City, State, ZIP code
-   First name, Last name, ZIP code
-   First name, Last name, Address, City, State
-   First name, Last name, Address, ZIP code
-   Full Social Security Number, First name, Last name
-   First 5 digits Social Security Number, First name, Last name

Design
------

The IBM i2 Accelerator for Acxiom IDOD consists of three components:
**User interface extension**  
The user interface extension creates a custom element in the Intelligence Portal to enable users to query Acxiom data.

The user interface extension displays a web page, `Acxiom.html`, that provides the query interface to the user as a web content form. When the results are returned to the Intelligence Analysis Platform, the user interface extension retrieves the results subset and displays it in a new tab.

This component of the accelerator is written in C\# and the web page is hosted in Microsoft Silverlight.

**Web service**  
The web service handles the user's query to Acxiom.

When the user submits a query in the Intelligence Portal user interface extension, a POST request is sent to the web service. The web service calls the appropriate Acxiom API, then transforms the response from Acxiom to create a valid results subset (QueryResult) for the Intelligence Analysis Platform. After storing the results subset in a file on disk, the web service initializes the subset in the Intelligence Analysis Platform so that it is available through the `IExternalDataSubsetLocator` interface. Then the web service returns the ID of the subset to the user interface extension.

This component of the accelerator is written in Java.

**Intelligence Analysis Platform connector**  
The Intelligence Analysis Platform connector implements the `IExternalDataSubsetLocator` interface to retrieve the results subset from the web service.

The user interface extension passes the ID of the subset to the Intelligence Analysis Platform connector, which retrieves the subset from the file in which it was stored by the web service. The Intelligence Analysis Platform connector then returns the results subset to the Intelligence Analysis Platform for display in the user interface extension.

This component of the accelerator is written in Java.

The web service and the Intelligence Analysis Platform connector are the data retrieval components of the accelerator. These components are deployed on the `acxiomread` server in the same web archive (WAR) file. The user interface extension is deployed on the write server in the `write.war` web archive file.

Logging
-------

The following IBM i2 Accelerator for Acxiom IDOD packages can be monitored using the default Intelligence Analysis Platform logging, or using your own customized logging:
-   com.i2group.connector.acxiom
-   com.i2group.connector.acxiom.request

By default, the transactions that occur when the accelerator is running are recorded in the `IBM_I2_Analysis_Repository.log` file in the log directory of the `acxiomread` server. The default logging level is WARN, which logs potentially harmful situations, error events, and fatal error events, but not informational messages.

Logging for the accelerator is specified in a `log4j.properties` file. The default properties file is located in the directory `IAP-Deployment-Toolkit\configuration\fragments\common\WEB-INF\classes`. You can create your own custom `log4j.properties` file and place it in the directory `IAP-Deployment-Toolkit\configuration\fragments\acxiom\WEB-INF\classes`.

For information on working with Log4j, see the Apache Logging Services Project website at logging.apache.org.



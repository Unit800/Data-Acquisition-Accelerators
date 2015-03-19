About the IBM i2 DomainTools Accelerator
========================================

The IBM i2 DomainTools Accelerator integrates the functionality of DomainTools research tools into the Intelligence Portal for IBM i2 Intelligence Analysis Platform. For information on the domain and DNS data and research tools provided by DomainTools, see www.domaintools.com.

The IBM i2 DomainTools Accelerator consists of Intelligence Analysis Platform extensions that are deployed using IBM i2 Intelligence Analysis Platform Developer Essentials. For information about Developer Essentials, see the GitHub repository for IBM i2 Intelligence Analysis Platform Developer Essentials.

To query the DomainTools database, the IBM i2 DomainTools Accelerator requires access to the DomainTools API. For information on the DomainTools API and how to arrange access to it, see www.domaintools.com/api/.

Capabilities
------------

The IBM i2 DomainTools Accelerator makes DomainTools research functions available to users as a custom element in the Intelligence Portal. Users search the DomainTools database and receive the results in the form of entities and links that show data related to the search terms.

To access the DomainTools functionality, users select the **DomainTools** menu to open a tab in the Intelligence Portal. Users then select a search type, enter search terms, and click **Submit**. The results are displayed in a new tab in the Intelligence Portal. Right-clicking an item in the results list displays a context menu with appropriate analysis options for the item type. When the user chooses an analysis option, the IBM i2 DomainTools Accelerator returns the results of the analysis.

The following DomainTools research functions can be accessed through the IBM i2 DomainTools Accelerator:

**Find related domains by IP address**  
Search for domain names linked to a specified IP address. This function uses the DomainTools `host-domains` API.

**Find related domains by shared hosting**  
Search for domain names that share the same Internet host as a specified domain. This function uses the DomainTools `reverse-ip` API.

**Find related domains by Whois records**  
Search for domain names related to one or more search terms. Multiple search terms must be separated by vertical bars ( | ). This function uses the DomainTools `reverse-whois` API.

**Perform Whois record lookup for a domain**  
Retrieve the ownership record for a specified domain name, with basic registration details. This function uses the DomainTools `whois` API.

**Perform Whois record history lookup for a domain**  
Retrieve the change history of the ownership record for a specified domain name. This function uses the DomainTools `whois-history` API.

Design
------

The IBM i2 DomainTools Accelerator consists of three components:

**User interface extension**  
The user interface extension creates a custom element in the Intelligence Portal to enable users to query DomainTools.

The user interface extension displays a web page, `DomainTools.html`, that provides the query interface to the user as a web content form. When the results are returned to the Intelligence Analysis Platform, the user interface extension retrieves the results subset and displays it in a new tab. The user can browse the results and carry out further analysis on individual items. The results of the analysis are displayed in the original tab.

This component of the accelerator is written in C\# and the web page is hosted in Microsoft Silverlight.

**RESTful web service**  
The RESTful web service handles the user's query to DomainTools.

When the user submits a query in the Intelligence Portal user interface extension, a POST request is sent to the web service. The web service calls the appropriate DomainTools API, then transforms the response from DomainTools to create a valid results subset (QueryResult) for the Intelligence Analysis Platform. After storing the results subset in a file on disk, the web service initializes the subset in the Intelligence Analysis Platform so that it is available through the `IExternalDataSubsetLocator` interface. Then the web service returns the ID of the subset to the user interface extension.

This component of the accelerator is written in Java.

**Intelligence Analysis Platform connector**  
The Intelligence Analysis Platform connector implements the `IExternalDataSubsetLocator` interface to retrieve the results subset from the web service.

The user interface extension passes the ID of the subset to the Intelligence Analysis Platform connector, which retrieves the subset from the file in which it was stored by the web service. The Intelligence Analysis Platform connector then returns the results subset to the Intelligence Analysis Platform for display in the user interface extension.

This component of the accelerator is written in Java.

The RESTful web service and the Intelligence Analysis Platform connector are the data retrieval components of the accelerator. These components are deployed on the `dtread` server in the same web archive (WAR) file. The user interface extension is deployed on the write server in the `write.war` web archive file.

Mapping of DomainTools results to Law Enforcement schema items
--------------------------------------------------------------

The following table shows the entities and link types from the Law Enforcement schema into which the IBM i2 DomainTools Accelerator transforms the items in the results returned from DomainTools.

|DomainTools|Law Enforcement schema|
|:----------|:---------------------|
|**host-domains** IpAddress|CommunicationsDevice entity|
|**host-domains** Domain name|CommunicationsDevice entity|
|**host-domains** IpAddress associated with a domain name|DuplicateOf (CommunicationsDevice to CommunicationsDevice) link|
|**reverse-ip** IpAddress|CommunicationsDevice entity|
|**reverse-ip** Domain name|CommunicationsDevice entity|
|**reverse-ip** IpAddress associated with a domain name|DuplicateOf (CommunicationsDevice to CommunicationsDevice) link|
|**reverse-whois** Domain name|CommunicationsDevice entity|
|**whois** Registrant|Organization entity|
|**whois** Record|Document entity|
|**whois** Domain name|CommunicationsDevice entity|
|**whois** Registrant associated with a record|SubjectOf (Organization to Document) link|
|**whois** Domain name associated with a record|SubjectOf (CommunicationsDevice to Document) link|
|**whois** Registrant associated with a domain name|AccessTo (Organization to CommunicationsDevice) link|
|**whois-history** Registrant|Organization entity|
|**whois-history** Record|Document entity|
|**whois-history** Domain name|CommunicationsDevice entity|
|**whois-history** Registrant associated with a record|SubjectOf (Organization to Document) link|
|**whois-history** Domain name associated with a record|SubjectOf (CommunicationsDevice to Document) link|
|**whois-history** Registrant associated with a domain name|AccessTo (Organization to CommunicationsDevice) link|

Logging
-------

The following IBM i2 DomainTools Accelerator packages can be monitored using the default Intelligence Analysis Platform logging, or using your own customized logging:

-   com.i2group.domaintools
-   com.i2group.domaintools.methods
-   com.i2group.domaintools.rest
-   com.i2group.domaintools.transforms

By default, the transactions that occur when the accelerator is running are recorded in the `IBM_I2_Analysis_Repository.log` file in the log directory of the `dtread` server. The default logging level is WARN, which logs potentially harmful situations, error events, and fatal error events, but not informational messages.

Logging for the accelerator is specified in a `log4j.properties` file. The default properties file is located in the directory `IAP-Deployment-Toolkit\configuration\fragments\common\WEB-INF\classes`. You can create your own custom `log4j.properties` file and place it in the directory `IAP-Deployment-Toolkit\configuration\fragments\domaintools\WEB-INF\classes`.

For information on working with Log4j, see the Apache Logging Services Project website at logging.apache.org.

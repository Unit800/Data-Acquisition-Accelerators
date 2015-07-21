/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using System;
using System.Globalization;
using System.Linq;
using System.Windows.Browser;
using i2.Apollo.Common;
using i2.Apollo.Common.Data;
using i2.Apollo.Common.DependencyInjection;
using i2.Apollo.Common.Intent;
using i2.Apollo.Common.Models.Explore;
using i2.Apollo.Common.Models.NetworkSearch;
using i2.Apollo.Common.Notifications;
using i2.Apollo.CommonControls;
using i2.Apollo.Controls.Intent;
using i2.Apollo.Controls.TabFramework;

namespace Acxiom.DataAccessIntent
{
    /// <summary>
    /// A handler for DAOD intents sent by the Acxiom html page.
    /// </summary>
    [ImplementedBy(typeof(DataAccessIntentHandler), singleton:true)]
    public interface IDataAccessIntentHandler
    {
    }

    /// <summary>
    /// Implementation of <see cref="IDataAccessIntentHandler"/>.
    /// </summary>
    public class DataAccessIntentHandler : IDataAccessIntentHandler
    {
        private const string ACXIOM_BROWSE_SEARCH_TYPE = "0";  //browse search
        private const string ACXIOM_PROPERTY_SEARCH_TYPE = "1"; //Property search
        private const string ACXIOM_QUERY_SEARCH_TYPE = "2"; //Query search

        private readonly IIntentManager mIntentManager;
        private readonly INetworkSearchConfigBuilder mNetworkSearchConfigBuilder;
        private readonly IDataSourcesAndSchema mDataSourcesAndSchema;
        private readonly IExploreConfigBuilder mExploreConfigBuilder;
        private readonly INotificationService mNotificationService;
        private readonly IExplorationIntentFactory mExplorationIntentFactory;
        private readonly IAcxiomConstants mAcxiomConstants;
        
        private static string mAcxiomTransformSourcePath; // Optional - Transform XSLT Path where the external xml files are located
        private string mAcxiomDaodXmlSourcePath; //Source Path where the external xml files are located
        private IDataSource mDataSource = null; //ACXIOM External Data Source
 
        /// <summary>
        /// Initializes a new instance of the <see cref="DataAccessIntentHandler"/> class.
        /// </summary>
        public DataAccessIntentHandler(IIntentManager intentManager, 
            INetworkSearchConfigBuilder networkSearchConfigBuilder,
            IDataSourcesAndSchema dataSourcesAndSchema,
            IExploreConfigBuilder exploreConfigBuilder,
            INotificationService notificationService,
            IExplorationIntentFactory explorationIntentFactory,
            IAcxiomConstants acxiomConstants)
        {
            mIntentManager = intentManager;
            mNetworkSearchConfigBuilder = networkSearchConfigBuilder;
            mDataSourcesAndSchema = dataSourcesAndSchema;
            mExploreConfigBuilder = exploreConfigBuilder;
            mNotificationService = notificationService;
            mExplorationIntentFactory = explorationIntentFactory;
            mAcxiomConstants = acxiomConstants;

            mAcxiomExternalDataSource = mAcxiomConstants.AcxiomDaodExternalContextRoot;//defaults to daod
            mAcxiomDaodXmlSourcePath = mAcxiomConstants.AcxiomDaodXmlSourcePath; //defaults to c:/i2-integration/xml/
            mAcxiomTransformSourcePath = mAcxiomConstants.AcxiomDaodTransformSourcePath; //defaults to empty 
        }

        /// <summary>
        /// Launch Daod Intent; search and show acxiom search results in new tab
        /// </summary>
        public void ProcessSearch(string searchType, string searchTermValues, string subsetName)
        {
            SetupDaodSearch(searchType, searchTermValues, subsetName);
            ExecuteSearchIntent();
        }

        //Launch Daod Intent in browse mode
        private void BrowseSuccess(IExternalDataSubsetIdentifier t, IExploreConfig exploreConfig)
        {
            var newConfig = mExploreConfigBuilder
                .StartNew()
                .SetDataSource(exploreConfig.DataSource)
                .SetExternalDataSubsetIdentifier(t)
                .Build();

            var browseSearchIntent = mExplorationIntentFactory.CreateExploreIntent(newConfig);
            // Open the tab next to the current one
            var openTabOptions = new OpenTabOptions
                                    {
                                        Location = OpenTabLocation.OpenNearCurrent
                                    };

            mIntentManager.Run(browseSearchIntent, openTabOptions);
        }

        //Launch Daod Intent in property view mode
        private void PBSSuccess(IExternalDataSubsetIdentifier t, IExploreConfig exploreConfig)
        {
            var newConfig = mExploreConfigBuilder
                .StartNew()
                .SetDataSource(exploreConfig.DataSource)
                .SetExternalDataSubsetIdentifier(t)
                .Build();

            var propertySearchIntent = mExplorationIntentFactory.CreatePropertySearchIntent(newConfig);
            // Open the tab next to the current one
            var openTabOptions = new OpenTabOptions
                                    {
                                        Location = OpenTabLocation.OpenNearCurrent
                                    };

            mIntentManager.Run(propertySearchIntent, openTabOptions);
        }

        //Launch Daod Intent in visual query mode
        private void VQSuccess(IExternalDataSubsetIdentifier t, IExploreConfig exploreConfig)
        {

            var networkSearchConfig = mNetworkSearchConfigBuilder
                .StartNew()
                .SetDataSource(exploreConfig.DataSource)
                .SetExternalDataSubsetIdentifier(t)
                .Build();

            var visualQuerySearchIntent = new OpenNetworkSearchWithConfig(networkSearchConfig, false);
            // Open the tab next to the current one
            var openTabOptions = new OpenTabOptions
                                    {
                                        Location = OpenTabLocation.OpenNearCurrent
                                    };

            mIntentManager.Run(visualQuerySearchIntent, openTabOptions);
        }

        private void SetupDaodSearch(string searchType, string searchTermValues, string subsetName)
        {

            if (String.IsNullOrEmpty(mAcxiomTransformSourcePath))
            {
                SearchTermValues = searchTermValues; //use the search term string ; name/value properties
            }
            else
            {
                SearchTermValues = mAcxiomDaodXmlSourcePath + searchTermValues; //append file name and then name/value strings
            }
            SubsetName = subsetName; //set the subset name
            
            // i.e, like a "DAOD Example ", "Acxiom for test", etc...
            mDataSource = mDataSourcesAndSchema.DataSources.SingleOrDefault(x => x.Id.Equals(ExtDatasource));

            if (mDataSource == null)
            {
                string extractDataSourceNotFound = string.Format(AcxiomStringResources.ErrorExternalDataSourceNotFound, ExtDatasource);
                mNotificationService.PresentInformationNotificationWithoutDiagnosticsToTheUser(extractDataSourceNotFound);
                throw new ArgumentException(string.Format(CultureInfo.InvariantCulture,
                                                          AcxiomStringResources.ErrorExternalDataSourceNotFound,
                                                          ExtDatasource));
            }

            switch (searchType)
            {
                case ACXIOM_BROWSE_SEARCH_TYPE:
                    IsBrowseSearch = true;
                    IsPropertySearch = false;
                    IsVisualQuerySearch = false;
                    break;

                case ACXIOM_PROPERTY_SEARCH_TYPE:
                    IsPropertySearch = true;
                    IsBrowseSearch = false;
                    IsVisualQuerySearch = false;
                    break;

                case ACXIOM_QUERY_SEARCH_TYPE:
                    IsVisualQuerySearch = true;
                    IsBrowseSearch = false;
                    IsPropertySearch = false;
                    break;
                default:  //not a valid option, default to browse
                    IsBrowseSearch = true;
                    IsPropertySearch = false;
                    IsVisualQuerySearch = false;
                    break;
            }
        }

        private void ExecuteSearchIntent()
        {
            ExternalDataSubsetIdentifier token;
            if (String.IsNullOrEmpty(mAcxiomTransformSourcePath))
            {
                token = new ExternalDataSubsetIdentifier(SearchTermValues, SubsetName); //NO XsltTransformPath needed, use search string only, Name is used to label the Daod Intent tab
            }
            else
            {
                token = new ExternalDataSubsetIdentifier(SearchTermValues + ";" + mAcxiomTransformSourcePath, SubsetName); //SearchTermPath and XsltTransformPath with Full qualify path, Name is used to label the Daod Intent tab
            }

            mExploreConfigBuilder.StartNew().SetExternalDataSubsetIdentifier(token);

            //Search using External Data source ( i.e, daod, acxiom ) so that a browse, Property or Visual search can be performed
            string usingDataSourceName = string.Format(AcxiomStringResources.DataSourceName, mDataSource.Name);
            mNotificationService.PresentAutoExpireSuccessfulActionNotificationToUser(usingDataSourceName);

            var ec = mExploreConfigBuilder
                    .SetDataSource(mDataSource)
                    .Build();

            if (IsPropertySearch)
            {
                PBSSuccess(token, ec);//launch the data intent as "Property Search View"
            }
            else if (IsVisualQuerySearch)
              {
                  VQSuccess(token, ec);//launch the data intent as "Visual Query Search View"
              }
              else
              {
                  BrowseSuccess(token, ec); //this is the default - launch the data intent as "Browse View"
              }
        }

        private static string mSearchTermValues = ""; //name/value pairs, defaults to empty  
        
        // Gets or sets the source Path where the external xml files are located and/or properties 
        private string SearchTermValues
        {
            get { return mSearchTermValues; }
            set
            {
                mSearchTermValues = value;
            }
        }

        private string mSubetName = "{subset_default_name}"; //set default for now;

        // Gets or sets the subset name.
        private string SubsetName
        {
            get { return mSubetName; }
            set
            {
                mSubetName = value;
            }
        }

        private bool mIsBrowseSearch = true; //default is 'Browse'

        // Gets or sets whether to use a Browse.
        private bool IsBrowseSearch
        {
            get { return mIsBrowseSearch; }
            set
            {
                mIsBrowseSearch = value;
            }
        }

        private bool mIsPropertySearch = false; //not using property search

        // Gets or sets whether to use a property search or not.
        private bool IsPropertySearch
        {
            get { return mIsPropertySearch; }
            set
            {
                mIsPropertySearch = value;
            }
        }

        private bool mIsVisualQuerySearch = false; //not using Visual Query

        // Gets or sets whether to use a Query Search.
        private bool IsVisualQuerySearch
        {
            get { return mIsVisualQuerySearch; }
            set
            {
                mIsVisualQuerySearch = value;
            }
        }

        private string mAcxiomExternalDataSource = ""; //Acxiom external data source (DAOD) name - default to empty
 
        // Gets or sets the External Data source name.
        private string ExtDatasource
        {
            get { return mAcxiomExternalDataSource; }
            set
            {
                mAcxiomExternalDataSource = value;
            }
        }
    
    }
}

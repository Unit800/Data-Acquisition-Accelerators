/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using System;
using System.Globalization;
using System.Linq;
using i2.Apollo.Common.Data;
using i2.Apollo.Common.DependencyInjection;
using i2.Apollo.Common.Intent;
using i2.Apollo.Common.Models.Explore;
using i2.Apollo.Common.Models.NetworkSearch;
using i2.Apollo.Common.Notifications;
using i2.Apollo.Controls.Intent;
using i2.Apollo.Controls.TabFramework;

namespace Acxiom.DataAccessIntent
{
    /// <summary>
    /// A handler for DAOD intents sent by the Acxiom html page.
    /// </summary>
    [ImplementedBy(typeof(DataAccessIntentHandler))]
    public interface IDataAccessIntentHandler
    {
        /// <summary>
        /// Launch Daod Intent, search and show acxiom search results in new tab
        /// </summary>
        void ProcessSearch(string searchType, string searchTermValues, string subsetName);
    }

    /// <summary>
    /// Implementation of <see cref="IDataAccessIntentHandler"/>.
    /// </summary>
    public class DataAccessIntentHandler : IDataAccessIntentHandler
    {
        private const string ACXIOM_BROWSE_SEARCH_TYPE = "0";  // Browse search
        private const string ACXIOM_PROPERTY_SEARCH_TYPE = "1"; // Property search
        private const string ACXIOM_QUERY_SEARCH_TYPE = "2"; // Query search

        private readonly IIntentManager mIntentManager;
        private readonly INetworkSearchConfigBuilder mNetworkSearchConfigBuilder;
        private readonly IDataSourcesAndSchema mDataSourcesAndSchema;
        private readonly IExploreConfigBuilder mExploreConfigBuilder;
        private readonly INotificationService mNotificationService;
        private readonly IExplorationIntentFactory mExplorationIntentFactory;
        private readonly IAcxiomConstants mAcxiomConstants;
        private readonly IDataSource mDataSource;

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

            mDataSource = mDataSourcesAndSchema.DataSources.SingleOrDefault(x => x.Id.Equals(mAcxiomConstants.AcxiomDaodExternalContextRoot));

            if (mDataSource == null)
            {
                string extractDataSourceNotFound = string.Format(AcxiomStringResources.ErrorExternalDataSourceNotFound, mAcxiomConstants.AcxiomDaodExternalContextRoot);
                mNotificationService.PresentInformationNotificationWithoutDiagnosticsToTheUser(extractDataSourceNotFound);
                throw new ArgumentException(string.Format(CultureInfo.InvariantCulture,
                                                          AcxiomStringResources.ErrorExternalDataSourceNotFound,
                                                          mAcxiomConstants.AcxiomDaodExternalContextRoot));
            }
        }

        /// <inheritdoc />
        public void ProcessSearch(string searchType, string searchTermValues, string subsetName)
        {
            var acxiomSearchTermValues = searchTermValues;
            if (!String.IsNullOrEmpty(mAcxiomConstants.AcxiomDaodTransformSourcePath))
            {
                acxiomSearchTermValues = string.Format("{0}{1};{2}", mAcxiomConstants.AcxiomDaodXmlSourcePath, acxiomSearchTermValues, mAcxiomConstants.AcxiomDaodTransformSourcePath);
            }

            var externalDataSubsetIdentifier = new ExternalDataSubsetIdentifier(acxiomSearchTermValues, subsetName);

            var usingDataSourceName = string.Format(AcxiomStringResources.DataSourceName, mDataSource.Name);
            mNotificationService.PresentAutoExpireSuccessfulActionNotificationToUser(usingDataSourceName);

            //Search using External Data source ( i.e, daod, acxiom ) so that a browse, Property or Visual search can be performed
            switch (searchType)
            {
                case ACXIOM_BROWSE_SEARCH_TYPE:
                    BrowseSuccess(externalDataSubsetIdentifier);
                    break;

                case ACXIOM_PROPERTY_SEARCH_TYPE:
                    PBSSuccess(externalDataSubsetIdentifier);
                    break;

                case ACXIOM_QUERY_SEARCH_TYPE:
                    VQSuccess(externalDataSubsetIdentifier);
                    break;

                default:  //not a valid option, default to browse
                    BrowseSuccess(externalDataSubsetIdentifier);
                    break;
            }
        }

        // Launch Daod Intent in browse mode
        private void BrowseSuccess(IExternalDataSubsetIdentifier externalDataSubsetIdentifier)
        {
            var newConfig = mExploreConfigBuilder
                .StartNew()
                .SetDataSource(mDataSource)
                .SetExternalDataSubsetIdentifier(externalDataSubsetIdentifier)
                .Build();

            var browseSearchIntent = mExplorationIntentFactory.CreateExploreIntent(newConfig);

            RunIntentNearCurrent(browseSearchIntent);
        }

        // Launch Daod Intent in property view mode
        private void PBSSuccess(IExternalDataSubsetIdentifier externalDataSubsetIdentifier)
        {
            var newConfig = mExploreConfigBuilder
                .StartNew()
                .SetDataSource(mDataSource)
                .SetExternalDataSubsetIdentifier(externalDataSubsetIdentifier)
                .Build();

            var propertySearchIntent = mExplorationIntentFactory.CreatePropertySearchIntent(newConfig);

            RunIntentNearCurrent(propertySearchIntent);
        }

        // Launch Daod Intent in visual query mode
        private void VQSuccess(IExternalDataSubsetIdentifier externalDataSubsetIdentifier)
        {
            var networkSearchConfig = mNetworkSearchConfigBuilder
                .StartNew()
                .SetDataSource(mDataSource)
                .SetExternalDataSubsetIdentifier(externalDataSubsetIdentifier)
                .Build();

            var visualQuerySearchIntent = new OpenNetworkSearchWithConfig(networkSearchConfig, false);

            RunIntentNearCurrent(visualQuerySearchIntent);
        }

        private void RunIntentNearCurrent(IIntent intent)
        {
            // Open the tab next to the current one
            var openTabOptions = new OpenTabOptions
            {
                Location = OpenTabLocation.OpenNearCurrent
            };

            mIntentManager.Run(intent, openTabOptions);
        }
    }
}
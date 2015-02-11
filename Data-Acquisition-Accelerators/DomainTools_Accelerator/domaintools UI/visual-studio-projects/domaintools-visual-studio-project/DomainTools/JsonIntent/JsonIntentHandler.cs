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
using i2.Apollo.CommonControls;
using i2.Apollo.Controls.Intent;
using i2.Apollo.Controls.TabFramework;

namespace DomainTools.JsonIntent
{
    /// <summary>
    /// A handler for JSON intents sent by the DomainTools html page.
    /// </summary>
    [ImplementedBy(typeof(JsonIntentHandler), singleton:true)]
    public interface IJsonIntentHandler
    {
    }

    /// <summary>
    /// Implementation of <see cref="IJsonIntentHandler"/>.
    /// </summary>
    public class JsonIntentHandler : IJsonIntentHandler

    {
        private readonly IIntentManager mIntentManager;
        private readonly IJsonSerializerWrapper mJsonSerializerWrapper;
        private readonly INetworkSearchConfigBuilder mNetworkSearchConfigBuilder;
        private readonly IDataSourcesAndSchema mDataSourcesAndSchema;
        private readonly IExploreConfigBuilder mExploreConfigBuilder;
        private readonly IExplorationIntentFactory mExplorationIntentFactory;
        private readonly IJavaScriptHostedCallRunner mJavaScriptHostedCallRunner;

        /// <summary>
        /// Initializes a new instance of the <see cref="JsonIntentHandler"/> class.
        /// </summary>
        public JsonIntentHandler(IIntentManager intentManager, 
            IJsonSerializerWrapper jsonSerializerWrapper,
            INetworkSearchConfigBuilder networkSearchConfigBuilder,
            IDataSourcesAndSchema dataSourcesAndSchema,
            IExploreConfigBuilder exploreConfigBuilder,
            IExplorationIntentFactory explorationIntentFactory,
            IJavaScriptHostedCallRunner javaScriptHostedCallRunner)
        {
            mIntentManager = intentManager;
            mJsonSerializerWrapper = jsonSerializerWrapper;
            mNetworkSearchConfigBuilder = networkSearchConfigBuilder;
            mDataSourcesAndSchema = dataSourcesAndSchema;
            mExploreConfigBuilder = exploreConfigBuilder;
            mExplorationIntentFactory = explorationIntentFactory;
            mJavaScriptHostedCallRunner = javaScriptHostedCallRunner;
        }

        [ScriptableMember(ScriptAlias = "handleIntent")]
        public void HandleIntent(string data)
        {
            // Ensure any errors in the execution are handled in Silverlight
            mJavaScriptHostedCallRunner.RunJavaScriptHostedCall(() => ProcessJsonIntent(data));
        }

        private void ProcessJsonIntent(string data)
        {
            var jsonIntent = mJsonSerializerWrapper.Deserialize<JsonSubsetIntent>(data);

            var dataSource = mDataSourcesAndSchema.DataSources.SingleOrDefault(x => x.Id.Equals(jsonIntent.DataSourceId));

            if (dataSource == null)
            {
                throw new ArgumentException(string.Format(CultureInfo.InvariantCulture,
                                                          "Unable to find datasource with id '{0}'",
                                                          jsonIntent.DataSourceId));
            }

            var token = new ExternalDataSubsetIdentifier(jsonIntent.Token, jsonIntent.SubsetName);

            IIntent intent;

            // Switch based on the string defined in the DomainTools.html
            switch (jsonIntent.PortalIntentToLaunch)
            {
                case "VisualQuery":
                    var networkSearchConfig = mNetworkSearchConfigBuilder
                        .StartNew()
                        .SetDataSource(dataSource)
                        .SetExternalDataSubsetIdentifier(token)
                        .Build();

                    intent = new OpenNetworkSearchWithConfig(networkSearchConfig, false);
                    break;

                case "Browse":
                    var exploreConfig = mExploreConfigBuilder
                        .StartNew()
                        .SetDataSource(dataSource)
                        .SetExternalDataSubsetIdentifier(token)
                        .Build();

                    intent = mExplorationIntentFactory.CreateExploreIntent(exploreConfig);
                    break;
                default:
                    throw new ArgumentException("Unknown portal intent to launch: " + jsonIntent.PortalIntentToLaunch);
            }

            // Open the tab next to the current one
            var openTabOptions = new OpenTabOptions
                                     {
                                         Location = OpenTabLocation.OpenNearCurrent
                                     };

            mIntentManager.Run(intent, openTabOptions);
        }
    }
}

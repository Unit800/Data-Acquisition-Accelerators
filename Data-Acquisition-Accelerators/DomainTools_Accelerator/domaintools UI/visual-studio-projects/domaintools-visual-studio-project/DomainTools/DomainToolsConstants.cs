/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using System;
using System.Globalization;
using i2.Apollo.Common;
using i2.Apollo.Common.DependencyInjection;
using i2.Apollo.Common.Preferences;

namespace DomainTools
{
    /// <summary>
    /// Interface for the external contract for <see cref="DomainToolsConstants"/>.
    /// </summary>
    [ImplementedBy(typeof(DomainToolsConstants), singleton: true)]
    public interface IDomainToolsConstants
    {
        /// <summary>
        /// Gets the location of the external subset generation UI.
        /// </summary>
        Uri ExternalSubsetGenerationUri { get; }

        /// <summary>
        /// Gets the name of the JSON intent handler which is registered via <see cref="IHtmlPage.RegisterScriptableObject"/>.
        /// </summary>
        string JsonIntentHandlerName { get; }
    }
    
    /// <summary>
    /// Constants used throughout DomainTools to get External Subset Generation Uri and Json Intent Handler Name
    /// </summary>
    public class DomainToolsConstants : IDomainToolsConstants
    {
        // To override these preferences without modifying the xap/dll's add entries to ApolloClientSettings.xml
        private static readonly ApplicationPreference<string> SubsettingExampleGenerationUILocationPreference =
            ApplicationPreference.Register("DomainToolsUIURL",
                                           ApplicationPreference.DefaultStringParser,
                                           "http://localhost:20032/da-subset-rest-example-id/DomainTools.html");

        /// <summary>
        /// Initializes a new instance of the <see cref="DomainToolsConstants"/> class.
        /// </summary>
        public DomainToolsConstants(IApplicationPreferences applicationPreferences)
        {
            var generationUi = applicationPreferences.GetValue(SubsettingExampleGenerationUILocationPreference);
            ExternalSubsetGenerationUri = new Uri(generationUi, UriKind.RelativeOrAbsolute);
        }

        /// <inheritdoc />
        public Uri ExternalSubsetGenerationUri { get; private set; }

        /// <inheritdoc />
        public string JsonIntentHandlerName
        {
            get { return "SubsettingExampleJsonIntentHandler"; }
        }
    }
}

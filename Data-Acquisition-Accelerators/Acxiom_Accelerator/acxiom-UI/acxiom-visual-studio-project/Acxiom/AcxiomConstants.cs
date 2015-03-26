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

namespace Acxiom
{
    /// <summary>
    /// Interface for the external apollo client settings for <see cref="AcxiomConstants"/>.
    /// </summary>
    [ImplementedBy(typeof(AcxiomConstants), singleton: true)]
    public interface IAcxiomConstants
    {
        /// <summary>
        /// Gets the location of the external subset generation UI.
        /// </summary>
        Uri ExternalSubsetGenerationUri { get; }

        /// <summary>
        /// Gets external data source (DAOD) context root.
        /// </summary>
        string AcxiomDaodExternalContextRoot { get; }

        /// <summary>
        /// Gets the source Path where the external xml files are located.
        /// </summary>
        string AcxiomDaodXmlSourcePath { get; }

        /// <summary>
        /// Gets the transform XSLT Path where the external xml files are located.
        /// </summary>
        string AcxiomDaodTransformSourcePath { get; }

    }
    
    /// <summary>
    /// Constants used throughout Acxiom to get External Subset Generation Uri and Daod Intent Handler Name
    /// </summary>
    public class AcxiomConstants : IAcxiomConstants
    {
        private static readonly string DEFAULT_EXT_CONTEXT_ROOT = "daod";
        private static readonly string DEFAULT_XML_SRC_PATH = "c:/i2-integration/xml/";
        private static readonly string DEFAULT_NO_TRANSFORM_PATH = ""; //default to empty

        // To override these preferences without modifying the xap/dll's add entries to ApolloClientSettings.xml
        private static readonly ApplicationPreference<string> GenerationUILocationPreference =
            ApplicationPreference.Register("AcxiomUIURL",
                                           ApplicationPreference.DefaultStringParser,
                                           "http://localhost/Acxiom.html");
        
        private static readonly ApplicationPreference<string> DaodExternalContextRoot =
            ApplicationPreference.Register("AcxiomDaodExternalContextRoot", 
                                            ApplicationPreference.DefaultStringParser,
                                            DEFAULT_EXT_CONTEXT_ROOT);

        private static readonly ApplicationPreference<string> DaodXmlSourcePath =
            ApplicationPreference.Register("AcxiomDaodXmlSourcePath", 
                                            ApplicationPreference.DefaultStringParser, 
                                            DEFAULT_XML_SRC_PATH);

        private static readonly ApplicationPreference<string> DaodTransformSourcePath =
            ApplicationPreference.Register("AcxiomDaodTransformSourcePath", 
                                            ApplicationPreference.DefaultStringParser, 
                                            DEFAULT_NO_TRANSFORM_PATH);

        /// <summary>
        /// Initializes a new instance of the <see cref="AcxiomConstants"/> class.
        /// </summary>
        public AcxiomConstants(IApplicationPreferences applicationPreferences)
        {
            var generationUi = applicationPreferences.GetValue(GenerationUILocationPreference);
            ExternalSubsetGenerationUri = new Uri(generationUi, UriKind.RelativeOrAbsolute);
            AcxiomDaodExternalContextRoot = applicationPreferences.GetValue(DaodExternalContextRoot);
            AcxiomDaodXmlSourcePath = applicationPreferences.GetValue(DaodXmlSourcePath);
            AcxiomDaodTransformSourcePath = applicationPreferences.GetValue(DaodTransformSourcePath);
        }

        /// <inheritdoc />
        public Uri ExternalSubsetGenerationUri { get; private set; }

        /// <inheritdoc />
        public string AcxiomDaodExternalContextRoot { get; private set; }

        /// <inheritdoc />
        public string AcxiomDaodXmlSourcePath  { get; private set; }

        /// <inheritdoc />
        public string AcxiomDaodTransformSourcePath { get; private set; }

     }
}

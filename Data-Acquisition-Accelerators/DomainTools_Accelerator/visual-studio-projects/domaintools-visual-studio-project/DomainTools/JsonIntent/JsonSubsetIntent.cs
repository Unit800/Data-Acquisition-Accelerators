/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using System.Runtime.Serialization;

namespace DomainTools.JsonIntent
{
    /// <summary>
    /// Object to describe a json intent from DomainTools.html.
    /// </summary>
    [DataContract]
    public class JsonSubsetIntent
    {
        /// <summary>
        /// Gets or sets the portal intent type to launch.
        /// </summary>
        [DataMember(Name = "portalIntentToLaunch")]
        public string PortalIntentToLaunch { get; set; }

        /// <summary>
        /// Gets or sets the Token.
        /// </summary>
        [DataMember(Name = "token")]
        public string Token { get; set; }

        /// <summary>
        /// Gets or sets the datasource id.
        /// </summary>
        [DataMember(Name = "dataSourceId")]
        public string DataSourceId { get; set; }

        /// <summary>
        /// Gets or sets the subset name.
        /// </summary>
        [DataMember(Name = "subsetName")]
        public string SubsetName { get; set; }
    }
}

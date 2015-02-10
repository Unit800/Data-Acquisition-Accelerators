/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using i2.Apollo.Common.Intent;
using i2.Apollo.Common.Models.Explore;
using i2.Apollo.Common.Models.GenericModels;
using System.Collections.Generic;

namespace DomainTools
{
    /// <summary>
    /// The external contract for <see cref="DomainToolsTabIntent"/>.
    /// </summary>
    public interface IDomainToolsTabIntent : IIntent
    {
        /// <summary>
        /// The Type of the item which is used in data object.
        /// </summary>
        string Type { get; }

        /// <summary>
        /// Gets the subset data object;
        /// </summary>
        string Query { get; }
    }

    /// <summary>
    /// A portal intent for opening a tab to display details about an item that
    /// originates in the data in an XML file.
    /// </summary>
    public class DomainToolsTabIntent : IDomainToolsTabIntent
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="DomainToolsTabIntent"/> class.
        /// </summary>
        /// <param name="type">The type of ojbect we are interested in getting.</param>
        /// <param name="query">The subset data we are interested in querying.</param>
        public DomainToolsTabIntent(string type, string query)
        {
            Type = type;
            Query = query;
        }

        /// <inheritdoc />
        public string Type { get; private set; }

        /// <inheritdoc />
        public string Query { get; private set; }
    }
}

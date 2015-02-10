/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2014 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using System;
using System.Linq;
using i2.Apollo.Common.Data;

namespace DomainTools
{
    public class DomainToolsOriginIdentifier
    {
        // The retrieval block type for the subsetting example datasource
        private const string OriginKey = "IAP.DomainTools";

        /// <summary>
        /// Initializes a new instance of the <see cref="DomainToolsOriginIdentifier"/> class.
        /// </summary>
        public DomainToolsOriginIdentifier(IOriginIdentifier originIdentifier)
        {
            if (!OriginatesFromExampleDataSource(originIdentifier))
            {
                throw new ArgumentException("The origin provided is not from the domain tools connectors.");
            }

            // If either of these fail the structure is different than originally specified in the example.
            Source = originIdentifier.Key.ElementAt(0);
            Id = originIdentifier.Key.ElementAt(1);
        }

        public string Id { get; private set; }
        public string Source { get; private set; }

        /// <summary>
        /// Returns whether of not the specified <see cref="ICardProvenance"/> is from the subsetting example.
        /// </summary>
        public static bool OriginatesFromExampleDataSource(ICardProvenance cardProvenance)
        {
            return OriginatesFromExampleDataSource(cardProvenance.OriginIdentifier);
        }

        /// <summary>
        /// Returns whether of not the specified <see cref="IOriginIdentifier"/> is from the subsetting example.
        /// </summary>
        public static bool OriginatesFromExampleDataSource(IOriginIdentifier originIdentifier)
        {
            return originIdentifier.OriginType.Equals(OriginKey);
        }
    }
}

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

namespace Acxiom
{
    /// <summary>
    /// The external contract for <see cref="AcxiomTabIntent"/>.
    /// </summary>
    public interface IAcxiomTabIntent : IIntent
    {
    }

    /// <summary>
    /// A portal intent for opening a tab to display details about an item that
    /// originates in the data in an XML file.
    /// </summary>
    public class AcxiomTabIntent : IAcxiomTabIntent
    {
    }
}

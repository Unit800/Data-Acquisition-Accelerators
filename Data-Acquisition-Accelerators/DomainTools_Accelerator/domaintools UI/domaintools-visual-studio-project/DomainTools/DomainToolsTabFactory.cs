/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using i2.Apollo.Common;
using i2.Apollo.Common.Intent;
using i2.Apollo.Controls.TabFramework;

namespace DomainTools
{
    /// <summary>
    /// A factory for generating Intelligence Portal tabs that display
    /// item details.
    /// </summary>
    public class DomainToolsTabFactory : ITabFactory
    {
        private readonly IProvider<IDomainToolsViewModel> mTabViewModelProvider;

        /// <summary>
        /// Initializes a new instance of the <see cref="DomainToolsTabFactory"/> class.
        /// </summary>
        public DomainToolsTabFactory(IProvider<IDomainToolsViewModel> tabViewModelProvider)
        {
            mTabViewModelProvider = tabViewModelProvider;
        }

        /// <summary>
        /// Creates content for a tab with the specified intent, or returns
        /// null if creation is not possible.
        /// </summary>
        /// <remarks>
        /// The intent manager calls this method when it searches for a
        /// factory that can create an <see cref="ITabContent"/> for the
        /// specified intent.
        /// </remarks>
        public ITabContent CreateContent(IIntent intent)
        {
            // This class can handle IDomainToolsTabIntent instances.
            // This code opens a tab with the implementation of
            // IDomainToolsTabViewModel as the view model behind the
            // tab content. HandleIntent() is then called on that view model.
            if (intent is IDomainToolsTabIntent)
            {
                return mTabViewModelProvider.GetInstance();
            }

            // This tab factory cannot create content for the given intent.
            return null;
        }
    }
}

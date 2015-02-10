/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using i2.Apollo.Common.Data;
using i2.Apollo.Common.DependencyInjection;
using i2.Apollo.Common.Intent;
using i2.Apollo.Controls.TabControl;
using i2.Apollo.Controls.Navigation;
using i2.Apollo.Controls.TabFramework;
using System.Collections.Generic;

namespace DomainTools
{
    /// <summary>
    /// Interface defining a tab view model for handling <see cref="IDomainToolsTabIntent"/>s.
    /// </summary>
    [ImplementedBy(typeof(DomainToolsTabViewModel))]
    public interface IDomainToolsViewModel : ITabContent
    {
    }

    /// <summary>
    /// A tab view model for handling <see cref="IDomainToolsTabIntent"/>s.
    /// </summary>
    public class DomainToolsTabViewModel : TabContentViewModelBase<IDomainToolsTabView>, IDomainToolsViewModel
    {
        private readonly IDomainToolsConstants mDomainToolsConstants;
        private readonly string mBaseURL;

        /// <summary>
        /// Initializes a new instance of the <see cref="DomainToolsTabViewModel"/> class.
        /// </summary>
        public DomainToolsTabViewModel(IDomainToolsTabView view, 
            IDomainToolsConstants subsettingExampleConstants)
            : base(isCloseable: true)
        {
            mDomainToolsConstants = subsettingExampleConstants;
            Header = DomainToolsStringResources.TabHeader;
            HeaderTooltip = Header;

            mBaseURL = mDomainToolsConstants.ExternalSubsetGenerationUri.ToString();
            PageLocation = mBaseURL;

            SetAsViewModelForView(view);
        }

        /// <inheritdoc />
        public bool CanHandleIntent(IIntent intent)
        {
            // Decides if this View Model can handle the given intent
            return intent is IDomainToolsTabIntent;
        }

        /// <inheritdoc />
        public void HandleIntent(IIntent intent, OpenTabOptions options)
        {
            // Handle the intent
            var tabIntent = (IDomainToolsTabIntent) intent;
            string url = mBaseURL;
            if (tabIntent.Type != null)
            {
                url += "?type=" + tabIntent.Type;
                if (tabIntent.Query != null)
                {
                    url += "&query=" + tabIntent.Query;
                }
            }
            PageLocation = url;
            SetAsViewModelForView(View);
        }

        private string mPageLocation;

        /// <summary>
        /// Gets the URL of the web page that displays the subset.
        /// </summary>
        /// <remarks>
        /// This value is bound to the HtmlSource property of the HtmlControl
        /// in SubsettingTabView.xaml, so we display the subsetting web page.
        /// </remarks>
        public string PageLocation
        {
            get { return mPageLocation; }
            private set
            {
                mPageLocation = value;
                OnPropertyChanged(this, x => x.PageLocation);
            }
        }

        #region ITabViewModel Implementation

        /// <inheritdoc />
        public TabType Type
        {
            get { return TabType.Unknown; }
        }

        /// <inheritdoc />
        public INavigationContentEvents NavigationEvents
        {
            get { return null; }
        }

        /// <inheritdoc />
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                View.Dispose();
            }

            base.Dispose(disposing);
        }

        #endregion
    }
}

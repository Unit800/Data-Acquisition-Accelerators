/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using Acxiom.DataAccessIntent;
using i2.Apollo.Common;
using i2.Apollo.CommonControls;
using i2.Apollo.Common.Data;
using i2.Apollo.Common.DependencyInjection;
using i2.Apollo.Common.Intent;
using i2.Apollo.Common.Models.Explore;
using i2.Apollo.Common.Models.NetworkSearch;
using i2.Apollo.Common.Mvvm;
using i2.Apollo.Common.Notifications;
using i2.Apollo.Common.Preferences;
using i2.Apollo.Common.ServiceCommunication;
using i2.Apollo.Common.ServiceControllers;
using i2.Apollo.Controls.Intent;
using i2.Apollo.Controls.TabControl;
using i2.Apollo.Controls.Navigation;
using i2.Apollo.Controls.TabFramework;
using System;
using System.Collections.Generic;
using System.Diagnostics.CodeAnalysis;
using System.Globalization;
using System.Linq;
using System.Windows.Browser;
using System.Windows.Input;

namespace Acxiom
{
    /// <summary>
    /// Interface defining a tab view model for handling <see cref="IAcxiomTabIntent"/>s.
    /// </summary>
    [ImplementedBy(typeof(AcxiomTabViewModel))]
    public interface IAcxiomViewModel : ITabContent
    {
    }

    /// <summary>
    /// A tab view model for handling <see cref="IAcxiomTabIntent"/>s.
    /// </summary>
    public class AcxiomTabViewModel : TabContentViewModelBase<IAcxiomTabView>, IAcxiomViewModel
    {

        /// <summary>
        /// This object provides the interface that the JavaScript portion will call back into Silverlight to launch Data Intent
        /// </summary>
        [SuppressMessage("Microsoft.Design", "CA1034:NestedTypesShouldNotBeVisible")]
        public class JavaScriptHandler
        {
            private readonly AcxiomTabViewModel mViewModel;
            private DataAccessIntentHandler mIntentHandler = null;
            private readonly IJavaScriptHostedCallRunner mJavaScriptHostedCallRunner;

            /// <summary>
            /// Initializes a new instance of the <see cref="JavaScriptHandler"/> class.
            /// </summary>
            public JavaScriptHandler(AcxiomTabViewModel viewModel)
            {
                mViewModel = viewModel;
                mJavaScriptHostedCallRunner = mViewModel.mContainer.Resolve<IJavaScriptHostedCallRunner>();
            }

            /// <summary>
            /// Launch a Data Intent tab to show the external data
            /// </summary>
            [ScriptableMember]
            public void LaunchDataIntent(string searchType, string searchTermValues, string subsetName)
            {
                // Ensure any errors in the execution are handled in Silverlight
                mJavaScriptHostedCallRunner.RunJavaScriptHostedCall(() => ExecuteDataIntentAsHostedJavascriptCall(searchType, searchTermValues, subsetName));
            }

            private void ExecuteDataIntentAsHostedJavascriptCall(string searchType, string searchTermValues, string subsetName)
            {
                if (mIntentHandler == null)
                {
                    var intentManager = mViewModel.mContainer.Resolve<IIntentManager>();
                    var exploreConfigBuilder = mViewModel.mContainer.Resolve<IExploreConfigBuilder>();
                    var explorationIntentFactory = mViewModel.mContainer.Resolve<IExplorationIntentFactory>();
                    var notificationService = mViewModel.mContainer.Resolve<INotificationService>();
                    var dataSourcesAndSchema = mViewModel.mContainer.Resolve<IDataSourcesAndSchema>();
                    var networkSearchConfigBuilder = mViewModel.mContainer.Resolve<INetworkSearchConfigBuilder>();
                    var acxiomConstants = mViewModel.mContainer.Resolve<IAcxiomConstants>();

                    mIntentHandler = new DataAccessIntentHandler(intentManager, networkSearchConfigBuilder, dataSourcesAndSchema, 
                                                                 exploreConfigBuilder, notificationService,
                                                                 explorationIntentFactory, acxiomConstants);

                }
                //execute Daod Intent, search and show results in new tab
                mIntentHandler.ProcessSearch(searchType, searchTermValues, subsetName);

            }

            /// <summary>
            /// Get Acxiom ContextRoot.
            /// </summary>
            [ScriptableMember]
            public string GetExternalContextRoot()
            {
                return mViewModel.mAcxiomConstants.AcxiomDaodExternalContextRoot;
            }

            /// <summary>
            /// Get source path where the external xml files are located -
            /// </summary>
            [ScriptableMember]
            public string GetXmlSourcePath()
            {
                return mViewModel.mAcxiomConstants.AcxiomDaodXmlSourcePath;
            }

            /// <summary>
            /// Get transform XSLT file Path
            /// </summary>
            [ScriptableMember]
            public string GetTransformSourcePath()
            {
                return mViewModel.mAcxiomConstants.AcxiomDaodTransformSourcePath;
            }

        }

        private readonly IIntentManager mIntentManager;
        private readonly IDependencyInjectionContainer mContainer;

        private readonly IAcxiomConstants mAcxiomConstants;
        private readonly string mBaseURL;
        private readonly IDispatcherTimer mDispatcherTimer;
        private Queue<Action> mPendingActions = new Queue<Action>();
        private readonly JavaScriptHandler mJavaScriptHandler;

        /// <summary>
        /// Initializes a new instance of the <see cref="AcxiomTabViewModel"/> class.
        /// </summary>
        public AcxiomTabViewModel(IAcxiomTabView view,
            IIntentManager intentManager,
            IDispatcherTimer dispatcherTimer,
            IDependencyInjectionContainer container,
            IAcxiomConstants acxiomConstants)
            : base(isCloseable: true)
        {
            mIntentManager = intentManager;
            mContainer = container;
            mDispatcherTimer = dispatcherTimer;
            mAcxiomConstants = acxiomConstants;
            Header = AcxiomStringResources.TabHeader; //Tab Header text
            HeaderTooltip = AcxiomStringResources.TabHeaderTooltip; //Tab header tooltip

            mBaseURL = mAcxiomConstants.ExternalSubsetGenerationUri.ToString();
            PageLocation = mBaseURL;
            mJavaScriptHandler = new JavaScriptHandler(this);

            SetAsViewModelForView(view);
            //All is ok, start to render html UI
            mDispatcherTimer.Interval = TimeSpan.FromMilliseconds(100);
            mDispatcherTimer.Tick += CheckForHtmlReady;
            mDispatcherTimer.Start();
            

        }
        /// <inheritdoc />
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (mDispatcherTimer.IsEnabled)
                {
                    // Stop the timer as we are closing the Tab ( ICA html )
                    mDispatcherTimer.Stop();
                }

            }
            base.Dispose(disposing);
        }

        private void CheckForHtmlReady(object sender, EventArgs e)
        {
            if (View.IsDomReady)
            {
                mDispatcherTimer.Stop();

                //dom Ready, pass along our SilverLight Object methods
                View.SetSilverlightInterface(mJavaScriptHandler); //pass along the JavaScript object to be used in the Javascript

                // remove any pendiung requests; html has been loaded
                while (mPendingActions.Any())
                {
                    mPendingActions.Dequeue()();
                }
            }
        }

        /// <inheritdoc />
        public bool CanHandleIntent(IIntent intent)
        {
            // Decides if this View Model can handle the given intent
            return intent is IAcxiomTabIntent;
        }

        /// <inheritdoc />
        public void HandleIntent(IIntent intent, OpenTabOptions opts)
        {
            PageLocation = mBaseURL;
            SetAsViewModelForView(View);
        }

        private string mPageLocation;

        /// <summary>
        /// Gets the URL of the web page that displays the subset.
        /// </summary>
        /// <remarks>
        /// This value is bound to the HtmlSource property of the HtmlControl
        /// in AcxiomTabView.xaml, displays acxiom main web page.
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

        #endregion
    }
}

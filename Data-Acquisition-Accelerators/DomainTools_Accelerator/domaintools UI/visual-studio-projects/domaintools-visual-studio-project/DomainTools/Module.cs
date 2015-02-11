/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using System.Windows.Browser;
using ExtensibilityHelper;
using i2.Apollo.Common;
using i2.Apollo.Common.DependencyInjection;
using i2.Apollo.Common.Notifications;
using i2.Apollo.CommonControls;
using i2.Apollo.Controls;
using i2.Apollo.Controls.Intent;
using i2.Apollo.SilverlightCommon;
using System;
using System.Collections.Generic;
using DomainTools.JsonIntent;

namespace DomainTools
{
    /// <summary>
    /// The startup module for DomainTools.
    /// </summary>
    public class Module : IDependencyModule
    {
        /// <summary>
        /// Gets a list of the modules that this module depends on.
        /// </summary>
        public static IEnumerable<string> ModuleDependencies
        {
            get
            {
                yield return typeof(SilverlightCommonModule).FullName;
                yield return typeof(CommonModule).FullName;
                yield return typeof(ControlsModule).FullName;
            }
        }

        private readonly IDependencyInjectionContainer mContainer;
        private readonly IApplicationPhases mApplicationPhases;
        private readonly PortalExtensibilityHelper mExtensibilityHelper;

        /// <summary>
        /// Initializes a new instance of the <see cref="Module"/> class.
        /// </summary>
        public Module(
            IDependencyInjectionContainer container, 
            IApplicationPhases applicationPhases,
            PortalExtensibilityHelper extensibilityHelper)
        {
            mContainer = container;
            mApplicationPhases = applicationPhases;
            mExtensibilityHelper = extensibilityHelper;
        }

        /// <summary>
        /// Initializes this module.  All its dependent modules will have
        /// been initialized by this point.
        /// </summary>
        public void Initialize()
        {
            RegisterPhasedWork();
        }

        private void RegisterPhasedWork()
        {
            // IntentManager is only available after we're logged in.
            mApplicationPhases.QueueSynchronousWork(
                phase: ApplicationPhase.RegisterSpokes,
                action: RegisterIntentHandler,
                exceptionMapper: x => NotificationMessages.GetErrorMessage(x, NotificationMessageKeys.ApplicationInitialization));
            
            // Use the ExtensibilityHelper to add a button into the header bar.
            mApplicationPhases.QueueSynchronousWork(
                phase: ApplicationPhase.AfterUIPresented, 
                action: CreateHeaderCommand,
                exceptionMapper: x => NotificationMessages.GetErrorMessage(x, NotificationMessageKeys.ApplicationInitialization));

            // Add an event listener to the hosting web page that uses the
            // IJsonSubsetIntentHandler to process JSON messages sent to it.
            mApplicationPhases.QueueSynchronousWork(
                phase: ApplicationPhase.AfterUIPresented,
                action: InjectPostMessageToIntentHandlerToHostPage,
                exceptionMapper: x => NotificationMessages.GetErrorMessage(x, NotificationMessageKeys.ApplicationInitialization));
        }

        private void RegisterIntentHandler()
        {
            var intentManager = mContainer.Resolve<IIntentManager>();

            // Register the DomainToolTabFactory with the Intent Manager, in order for DomainToolTabIntent to be handled.
            var domainToolTabFactory = mContainer.Resolve<DomainToolsTabFactory>();
            intentManager.Register(domainToolTabFactory);
        }

        private void InjectPostMessageToIntentHandlerToHostPage()
        {
            var jsonHandler = mContainer.Resolve<IJsonIntentHandler>();
            var htmlPage = mContainer.Resolve<IHtmlPage>();

            var constants = mContainer.Resolve<IDomainToolsConstants>();

            // Register an object which can handle the intents coming in via post message
            // to be called by our injected JavaScript
            htmlPage.RegisterScriptableObject(constants.JsonIntentHandlerName, jsonHandler);

            // The domain we expect to receive messages from.
            var expectedDomain = constants
                .ExternalSubsetGenerationUri
                .GetComponents(UriComponents.SchemeAndServer, UriFormat.Unescaped);

            // Accept messages from the specified domain
            // Use GetJavascriptContent to get our scriptable .Net object for handling JSON intents then handleIntent
            // Note: addEventListener will only work in IE9+ if you need to support IE8 attachEvent can be used.
            var theFunction = "window.addEventListener(\"message\",function(e){" +
                  "    if(e.origin !== \"" + expectedDomain + "\"){" +
                  "        return;" +
                  "    }" +
                  "    var jsonIntentHandler = GetJavascriptContent()[\"" + constants.JsonIntentHandlerName + "\"];" +
                  "    jsonIntentHandler.handleIntent(e.data);" +
                  "" +
                  "}, false);";

            HtmlPage.Window.Invoke("eval", theFunction);
        }

        private void CreateHeaderCommand()
        {
            var intentManager = mContainer.Resolve<IIntentManager>();

            // Create the command that the inserted header button will use.
            var command = new SimpleCommand
                {
                    Action = () =>
                    {
                        var intent = new DomainToolsTabIntent(null, null);
                        intentManager.Run(intent);
                    }
                };

            // Set up the button's content and pass in the command to be used.
            mExtensibilityHelper.RegisterHeaderCommand(
                headerButtonCaption: DomainToolsStringResources.GroupHeading,
                headerButtonTooltip: DomainToolsStringResources.GroupHeadingTooltip,
                buttonCommand: command);
        }        
    }
}

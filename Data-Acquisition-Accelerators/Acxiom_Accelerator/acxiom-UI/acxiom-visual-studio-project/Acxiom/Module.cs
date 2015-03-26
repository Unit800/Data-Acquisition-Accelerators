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

namespace Acxiom
{
    /// <summary>
    /// The startup module for Acxiom.
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
        }

        private void RegisterIntentHandler()
        {
            var intentManager = mContainer.Resolve<IIntentManager>();

            // Register the AcxiomTabFactory with the Intent Manager, in order for AcxiomTabIntent to be handled.
            var acxiomTabFactory = mContainer.Resolve<AcxiomTabFactory>();
            intentManager.Register(acxiomTabFactory);
        }

        private void CreateHeaderCommand()
        {
            var intentManager = mContainer.Resolve<IIntentManager>();

            // Create the command that the inserted header button will use.
            var command = new SimpleCommand
                {
                    Action = () =>
                    {
                        var intent = new AcxiomTabIntent();
                        intentManager.Run(intent);
                    }
                };

            // Set up the button's content and pass in the command to be used.
            mExtensibilityHelper.RegisterHeaderCommand(
                headerButtonCaption: AcxiomStringResources.GroupHeading,
                headerButtonTooltip: AcxiomStringResources.GroupHeadingTooltip,
                buttonCommand: command);
        }        
    }
}

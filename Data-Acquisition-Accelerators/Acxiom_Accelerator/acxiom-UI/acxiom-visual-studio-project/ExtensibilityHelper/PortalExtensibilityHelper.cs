/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using i2.Apollo.Common;
using i2.Apollo.Common.DependencyInjection;
using i2.Apollo.Common.Notifications;
using i2.Apollo.Controls;
using i2.Apollo.Controls.Views;
using System.Windows.Controls.Primitives;
using System;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using System.Xml;

namespace ExtensibilityHelper
{
    /// <summary>
    /// Provides an API to support extending the IBM i2 Intelligence Analysis Platform, Intelligence Portal.
    /// </summary>
    public class PortalExtensibilityHelper
    {
        private readonly IDependencyInjectionContainer mContainer;

        /// <summary>
        /// Initializes a new instance of the <see cref="PortalExtensibilityHelper"/> class.
        /// </summary>
        public PortalExtensibilityHelper(
            IDependencyInjectionContainer container)
        {
            mContainer = container;
        }
        
        /// <summary>
        /// Adds a new button to the Intelligence Portal header.
        /// </summary>
        /// <param name="headerButtonCaption">The caption to display in the button.</param>
        /// <param name="headerButtonTooltip">The text to display in the tooltip when a user hovers the mouse over the button.</param>
        /// <param name="buttonCommand">The command to run when the button is clicked.</param>
        public void RegisterHeaderCommand(
            string headerButtonCaption,
            string headerButtonTooltip,
            SimpleCommand buttonCommand)
        {
            // We may well be called before the header bar is actually on screen, so
            // we need to loop until it's there (or the initialization code times us out)
            var timer = mContainer.Resolve<IDispatcherTimer>();
            timer.Interval = TimeSpan.FromMilliseconds(10);
            timer.Tick += delegate
            {
                var toolboxView = Application.Current.RootVisual.GetVisualDescendants().OfType<ToolboxView>().SingleOrDefault();
                if (toolboxView == null)
                {
                    // Toolbox not visible yet.
                    return;
                }

                // Toolbox is visible now.
                timer.Stop();

                // Find the grid that contains the buttons
                var layoutGrid = (Grid)toolboxView.Content;
                var grid2 = (Grid)layoutGrid.Children[0];
                var grid3 = (Grid)grid2.Children[0];
                var grid4 = (Grid)grid3.Children[0];

                var destinationGrid = grid4;
                
                // Add a new column definition
                var existingColCount = destinationGrid.ColumnDefinitions.Count;
                destinationGrid.ColumnDefinitions.Add(new ColumnDefinition());
                
                // Add the button.
                var button = new HeaderButton();
                button.HeaderButtonTextBlockBase.Text = headerButtonCaption;
                button.HeaderButtonTextBlockHighlight.Text = headerButtonCaption;
                
                var tooltip = ToolTipService.GetToolTip(button.ButtonControl) as ToolTip;
                if(tooltip != null)
                {
                    tooltip.Content = headerButtonTooltip;
                }

                button.ButtonControl.Command = buttonCommand;

                button.SetValue(Grid.ColumnProperty, existingColCount);
                destinationGrid.Children.Add(button);

            };

            timer.Start();
        }
    }
}

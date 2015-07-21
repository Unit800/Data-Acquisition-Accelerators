/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using i2.Apollo.Common.DependencyInjection;
using i2.Apollo.Common.Mvvm;
using System;


namespace Acxiom
{
    /// <summary>
    /// Interface defining a view for an <see cref="IAcxiomTabView"/>.
    /// </summary>
    [ImplementedBy(typeof(AcxiomTabView))]
    public interface IAcxiomTabView : IView, IDisposable
    {
        /// <summary>
        /// Passes the main Silverlight interface through to the JavaScript code.
        /// </summary>
        void SetSilverlightInterface(object silverlightInterface);
        /// <summary>
        /// Gets a value indicating whether the HTML dom is completed.
        /// </summary>
        bool IsDomReady { get; }
        /// <summary>
        /// write message to console log.
        /// </summary>
        void LogToConsole(string message);

    }

    /// <summary>
    /// A view for an <see cref="IAcxiomTabViewModel"/>.
    /// </summary>
    public partial class AcxiomTabView : IAcxiomTabView
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="AcxiomTabView"/> class.
        /// </summary>
        public AcxiomTabView()
        {
            InitializeComponent();
        }
        /// <inheritdoc />
        public void SetSilverlightInterface(object silverlightInterface)
        {
            HtmlHost.InvokeInIFrame("setSilverlightInterface", silverlightInterface);
        }
        /// <inheritdoc />
        public void LogToConsole(string message)
        {
            HtmlHost.InvokeInIFrame("logToConsole", message);//write to browser console
        }
        /// <inheritdoc />
        public bool IsDomReady
        {
            get
            {
                try
                {
                    return (bool)HtmlHost.InvokeInIFrame("isDomReady");
                }
                catch (InvalidOperationException)
                {
                    return false;
                }
            }
        }
        /// <inheritdoc />
        public void Dispose()
        {
            HtmlHost.Dispose();
        }
    }
}

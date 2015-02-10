/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using i2.Apollo.Common.DependencyInjection;
using i2.Apollo.Common.Mvvm;
using System;


namespace DomainTools
{
    /// <summary>
    /// Interface defining a view for an <see cref="IDomainToolsTabView"/>.
    /// </summary>
    [ImplementedBy(typeof(DomainToolsTabView))]
    public interface IDomainToolsTabView : IView, IDisposable
    {
    }

    /// <summary>
    /// A view for an <see cref="IDomainToolsTabViewModel"/>.
    /// </summary>
    public partial class DomainToolsTabView : IDomainToolsTabView
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="DomainToolsTabView"/> class.
        /// </summary>
        public DomainToolsTabView()
        {
            InitializeComponent();
        }

        /// <inheritdoc />
        public void Dispose()
        {
            HtmlContent.Dispose();
        }
    }
}

/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2014 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
using i2.Apollo.Common.Intent;
using i2.Apollo.Common.Models.Explore;
using i2.Apollo.Common.Models.GenericModels;
using System;
using System.Collections.Generic;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;

namespace SubsettingExample
{
    public class LaunchSubsettingTabIntent : IIntent
    {
        public LaunchSubsettingTabIntent(IEnumerable<IMutableModel> models, IExploreState exploreState)
        {
            Models = models;
            ExploreState = exploreState;
        }

        public IEnumerable<IMutableModel> Models { get; private set; }
        public IExploreState ExploreState { get; private set; }
    }
}

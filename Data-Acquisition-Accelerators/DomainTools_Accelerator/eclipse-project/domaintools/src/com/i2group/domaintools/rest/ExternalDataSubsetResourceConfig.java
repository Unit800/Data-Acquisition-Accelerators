/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.i2group.domaintools.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * The External Data Subset REST application.
 */
public final class ExternalDataSubsetResourceConfig extends Application
{
    private static final ITracer ITRACER = TracingManager.getTracer(ExternalDataSubsetResourceConfig.class);
    @Override
    public Set<Class<?>> getClasses()
    {
        final String methodName = "getClasses";
        ITRACER.debugEntry(methodName);
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(ExternalDataSubsetResource.class);
        ITRACER.debugExit(methodName);
        return classes;
    }
}

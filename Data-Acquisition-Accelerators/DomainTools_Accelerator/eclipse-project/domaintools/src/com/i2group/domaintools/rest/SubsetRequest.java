/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.i2group.domaintools.rest;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * The request object for the subset rest resource.
 */
@XmlType(name = "SubsetRequest")
@XmlRootElement(name = "SubsetRequest")
public final class SubsetRequest
{
    private static final ITracer ITRACER = TracingManager.getTracer(SubsetRequest.class);
    @XmlAttribute(name = "RequestType")
    private String requestType;

    @XmlAttribute(name = "SearchString")
    private String searchString;

    /**
     * Constructs a new {@link SubsetRequest}. This constructor is intended for
     * use by JAXB and should not be called by other code.
     * 
     * @exclude
     */
    public SubsetRequest()
    {
        final String methodName = "SubsetRequest<init>";
        ITRACER.debugEntry(methodName);
        ITRACER.debugExit(methodName);
    }

    /**
     * Constructs a new {@link SubsetRequest}.
     * 
     * @param search
     *            The search string.
     * 
     */
    public SubsetRequest(final String search)
    {
        final String methodName = "SubsetRequest<init>(String search)";
        ITRACER.debugEntry(methodName);
        searchString = search;
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {search};
            ITRACER.debug("searchString is : {0}", values);
        }
        ITRACER.debugExit(methodName);
    }

    /**
     * Gets the search string.
     * 
     * @return See above.
     */
    @XmlTransient
    public String getSearchString()
    {
        return searchString;
    }

    /**
     * Sets the search string.
     * 
     * @param search
     *            The search string.
     */
    public void setSearchString(final String search)
    {
        final String methodName = "setSearchString";
        ITRACER.debugEntry(methodName);
        searchString = search;
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {search};
            ITRACER.debug("searchString set to : {0}", values);
        }
        ITRACER.debugExit(methodName);
    }

    /**
     * Return the requestType.
     * 
     * @return The requestType
     */
    public String getRequestType()
    {
        return requestType;
    }

    /**
     * Set the requestType.
     * 
     * @param rqestType
     *            The requestType
     */
    public void setRequestType(final String rqestType)
    {
        final String methodName = "setRequestType";
        ITRACER.debugEntry(methodName);
        requestType = rqestType;
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {rqestType};
            ITRACER.debug("requestType set to : {0}", values);
        }
        ITRACER.debugExit(methodName);
    }
}

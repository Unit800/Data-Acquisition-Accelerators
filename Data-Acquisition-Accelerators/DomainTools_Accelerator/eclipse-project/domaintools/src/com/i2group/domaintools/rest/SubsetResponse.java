/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.i2group.domaintools.rest;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.i2group.apollo.externaldata.transport.ExternalDataSubsetIdentifier;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * The response object for the subset rest resource.
 */
@XmlType(name = "SubsetResponse")
@XmlRootElement(name = "SubsetResponse")
public final class SubsetResponse
{
    private static final ITracer ITRACER = TracingManager.getTracer(SubsetResponse.class);
    @XmlElement(name = "SubsetIdentifier")
    private ExternalDataSubsetIdentifier subsetIdentifier;

    @XmlAttribute(name = "DataSourceId")
    private String dataSourceId;

    /**
     * Constructs a new {@link SubsetResponse}. This constructor is intended for
     * use by JAXB and should not be called by other code.
     * 
     * @exclude
     */
    public SubsetResponse()
    {
        final String methodName = "SubsetResponse()<init>";
        ITRACER.debugEntry(methodName);
        ITRACER.debugExit(methodName);
    }

    /**
     * Constructs a new {@link SubsetResponse}.
     * 
     * @param subsetId
     *            The subset identifier.
     * @param dataSourceIdParam
     *            The data source identifier.
     */
    public SubsetResponse(final ExternalDataSubsetIdentifier subsetId,
            final String dataSourceIdParam)
    {
        final String methodName = "SubsetResponse(ExternalDataSubsetIdentifier subsetId, String dataSourceIdParam)<init>";
        ITRACER.debugEntry(methodName);
        this.subsetIdentifier = subsetId;
        dataSourceId = dataSourceIdParam;
        ITRACER.debugExit(methodName);
    }

    /**
     * Gets the subset identifier.
     * 
     * @return See above.
     */
    @XmlTransient
    public ExternalDataSubsetIdentifier getSubsetIdentifier()
    {
        return subsetIdentifier;
    }

    /**
     * Sets the subset identifier.
     * 
     * @param subsetId
     *            The subset identifier.
     */
    public void setSubsetIdentifier(
            final ExternalDataSubsetIdentifier subsetId)
    {
        final String methodName = "setSubsetIdentifier";
        ITRACER.debugEntry(methodName);
        this.subsetIdentifier = subsetId;
        ITRACER.debugExit(methodName);
    }

    /**
     * Gets the DataSourceId.
     * 
     * @return See above.
     */
    @XmlTransient
    public String getDataSourceId()
    {
        return dataSourceId;
    }

    /**
     * Sets the DataSourceId.
     * 
     * @param dataSourceIdParam
     *            The DataSourceId.
     */
    public void setDataSourceId(final String dataSourceIdParam)
    {
        final String methodName = "setDataSourceId";
        ITRACER.debugEntry(methodName);
        dataSourceId = dataSourceIdParam;
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {dataSourceIdParam};
            ITRACER.debug("dataSourceId set to : {0}", values);
        }
        ITRACER.debugExit(methodName);
    }
}

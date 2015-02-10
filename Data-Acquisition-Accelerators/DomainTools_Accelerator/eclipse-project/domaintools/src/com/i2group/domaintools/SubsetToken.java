/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.i2group.domaintools;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * JAXB class to represent the subset token.
 */
@XmlType(name = "SubsetToken")
@XmlRootElement(name = "SubsetToken")
public final class SubsetToken
{
    private static final ITracer ITRACER = TracingManager.getTracer(SubsetToken.class);
    @XmlElement(name = "UniquePrefix", required = true)
    private String uniquePrefix;

    @XmlElement(name = "PathToSubset", required = true)
    private String mPathToSubset;

    /**
     * Constructs a new {@link SubsetToken}. This constructor is intended for
     * use by JAXB and should not be called by other code.
     * 
     * @exclude
     */
    public SubsetToken()
    {
        final String methodName = "SubsetToken<init>";
        ITRACER.debugEntry(methodName);
        ITRACER.debugExit(methodName);
    }

    /**
     * Constructs a new {@link SubsetToken}.
     * 
     * @param uPrefix
     *            The unique prefix for this subset.
     * @param pathToSubset
     *            The path to the subset.
     */
    public SubsetToken(final String uPrefix, final String pathToSubset)
    {
        final String methodName = "SubsetToken(String uPrefix, String pathToSubset)<init>";
        ITRACER.debugEntry(methodName);
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {uPrefix, pathToSubset};
            ITRACER.debug("uPrefix is : {0}, pathToSubset is : {1}", values);
        }
        uniquePrefix = uPrefix;
        mPathToSubset = pathToSubset;
        ITRACER.debugExit(methodName);

    }

    /**
     * Sets the unique prefix for this subset.
     * 
     * @param uPrefix
     *            The unique prefix for this subset.
     */
    public void setUniquePrefix(final String uPrefix)
    {
        final String methodName = "setUniquePrefix";
        ITRACER.debugEntry(methodName);
        uniquePrefix = uPrefix;
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {uPrefix};
            ITRACER.debug("setting uniquePrefix to : {0}", values);
        }
        ITRACER.debugExit(methodName);
    }

    /**
     * Gets the unique prefix for this subset.
     * 
     * @return See above.
     */
    @XmlTransient
    public String getUniquePrefix()
    {
        return uniquePrefix;
    }

    /**
     * Sets the path to the subset.
     * 
     * @param pathToSubset
     *            The path to the subset.
     */
    public void setPathToSubset(final String pathToSubset)
    {
        final String methodName = "setPathToSubset";
        ITRACER.debugEntry(methodName);
        mPathToSubset = pathToSubset;
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {pathToSubset};
            ITRACER.debug("setting mPathToSubset to : {0}", values);
        }
        ITRACER.debugExit(methodName);
    }

    /**
     * Gets the path to the subset.
     * 
     * @return See above.
     */
    @XmlTransient
    public String getPathToSubset()
    {
        return mPathToSubset;
    }
}

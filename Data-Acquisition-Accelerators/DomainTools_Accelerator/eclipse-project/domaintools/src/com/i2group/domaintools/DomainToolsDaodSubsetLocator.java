/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.i2group.domaintools;

import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.i2group.apollo.externaldata.connector.IExternalDataSubsetLocator;
import com.i2group.apollo.externaldata.transport.ExternalDataSubsetIdentifier;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * Implementation of {@link IExternalDataSubsetLocator} which simply returns the
 * subset which is already platform compatible.
 */
public final class DomainToolsDaodSubsetLocator implements
        IExternalDataSubsetLocator
{
    private static final ITracer ITRACER = TracingManager.getTracer(DomainToolsDaodSubsetLocator.class);
    private final SubsetTokenMarshaller subsetTokenMarshaller;

    /**
     * Constructs a new {@link ExampleDaodSubsetLocator}.
     */
    public DomainToolsDaodSubsetLocator()
    {
        final String methodName = "DomainToolsDaodSubsetLocator<init>";
        ITRACER.debugEntry(methodName);
        subsetTokenMarshaller = new SubsetTokenMarshaller();
        ITRACER.debugExit(methodName);
    }

    /**
     * Gets an XML {@link Source} in the correct format specified to translate
     * to AR Items.
     * 
     * @param subsetDataToken
     *            The {@link ExternalDataSubsetIdentifier}.
     * 
     * @return See above.
     */
    @Override
    public Source getSubset(final ExternalDataSubsetIdentifier subsetDataToken)
    {
        final String methodName = "getSubset";
        ITRACER.debugEntry(methodName);
        final String subsetTokenString = subsetDataToken.getSubsetToken();
        if (ITRACER.isInfoEnabled())
        {
            Object[] values = { subsetTokenString };
            ITRACER.info("getSubset called for domaintools daod. subsetTokenString is : {0}", values);
        }

        final SubsetToken token = subsetTokenMarshaller
                .unmarshall(subsetTokenString);

        final File subsetFile = new File(token.getPathToSubset());
        ITRACER.debugExit(methodName);
        return new StreamSource(subsetFile);
    }
}

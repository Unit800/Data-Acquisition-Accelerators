/*
 * Copyright (c) 2015 IBM Corp.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    IBM Corp - initial API and implementation and initial documentation
 */
package com.i2group.connector.acxiom;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.i2group.apollo.externaldata.connector.IExternalDataSubsetLocator;
import com.i2group.apollo.externaldata.transport.ExternalDataSubsetIdentifier;
import com.i2group.connector.acxiom.request.FindPeopleRequest;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * An external data adapter that supports searches on an external data source.
 */
public final class AcxiomDataAdapterSubset implements
        IExternalDataSubsetLocator
{
    private static final String FIND_PEOPLE_XSLT_TRANSFORM = "FindPeopleToCounterFraud.xslt";
    //private static final String FIND_PEOPLE_XSLT_TRANSFORM = "FindPeopleToCounterFraud.xslt";
    private static final ITracer ITRACER = TracingManager
            .getTracer(AcxiomDataAdapterSubset.class);

    /**
     * Gets an XML Source that represents the external data subset.
     * 
     * @param subsetDataToken
     *            An ExternalDataSubsetIdentifier that identifies the external
     *            data subset to be located.
     * @return Source Source that represents the external data subset identified
     *         by the specified ExternalDataSubsetIdentifier.
     */
    @Override
    public Source getSubset(final ExternalDataSubsetIdentifier subsetDataToken)
    {
        final String methodName = "getSubset";
        ITRACER.debugEntry(methodName);

        // The subsetToken contains the field values from the UI.
        final String searchString = subsetDataToken.getSubsetToken();

        if (ITRACER.isDebugEnabled())
        {
            final Object[] values = { searchString };
            ITRACER.debug("Search String : {0}", values);
        }

        // Create the Map of search terms
        final Map<String, String> searchTermsMap = AcxiomUtils
                .parseString(searchString);

        // create FindPeopleRequest, initialising with the search terms map used
        // to create the request
        final FindPeopleRequest findPeopleRequest = new FindPeopleRequest(
                searchTermsMap);
        final StreamSource soapResponse = findPeopleRequest.execute();
        StreamSource iapFormatSource = AcxiomUtils
                .transformToPlatformCompatibleXML(soapResponse,
                        FIND_PEOPLE_XSLT_TRANSFORM);

        // Create xpath ArrayList containing the xpath expression to find the
        // OriginId key for a Party entity, so that we can update the values of
        // DPPA and GLBA within the QueryResult.
        final ArrayList<String> xpath = new ArrayList<String>();
        xpath.add("/QueryResult/Partys/Party[1]/Cards/Card[1]/*[local-name()=\'CardProvenance\']/OriginId/Key/String");
        // Finding DPPA and GLBA permission values from the searchToken
        final String dppa = AcxiomUtils.getValueForType(searchTermsMap, "DPPA");
        final String glba = AcxiomUtils.getValueForType(searchTermsMap, "GLBA");

        // This function is called to update the QueryResult to contain the DPPA
        // and GLBA values so that further searches can be
        // made using these values.
        final StreamSource updatedStreamSource = AcxiomUtils.updateQueryResult(
                iapFormatSource, xpath, dppa, glba);

        ITRACER.debugExit(methodName);

        return updatedStreamSource;
    }
}

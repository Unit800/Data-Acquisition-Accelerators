/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.i2group.domaintools.Methods;

import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.i2group.domaintools.DomainToolsLoader;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * Method that represents a call to domaintools api method reverse-whois.
 *
 */
public final class WhoisSearch implements IMethod
{
    private static final ITracer ITRACER = TracingManager.getTracer(WhoisSearch.class);
    private static final String REVERSE_WHOIS = "reverse-whois";

    @Override
    public String getName()
    {
        return REVERSE_WHOIS;
    }

    @Override
    public String execute(final String query)
    {
        final String methodName = "execute";
        ITRACER.debugEntry(methodName);
        final String encodedQuery = DomainToolsLoader.urlEncode(query);
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {query, encodedQuery};
            ITRACER.debug("query is : {0}, encodedQuery is : {1}", values);
        }
        final String response = DomainToolsLoader.makeRequest(REVERSE_WHOIS,
                "&terms=" + encodedQuery + "&mode=purchase");

        final HashMap<String, String> parameters = new HashMap<String, String>();

        final XMLGregorianCalendar timestamp;
        try
        {
            timestamp = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                    new GregorianCalendar());
        }
        catch (DatatypeConfigurationException ex)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Exception occurred when creating XMLGregorianCalendar timestamp.");
                ITRACER.error(ex);
            }
            throw new RuntimeException(ex);
        }
        parameters.put("now", timestamp.toString());

        final String transformed = DomainToolsLoader.transform(response,
                "/com/i2group/domaintools/transforms/reverse-whois.xslt",
                parameters);
        ITRACER.debugExit(methodName);
        return transformed;
    }
}

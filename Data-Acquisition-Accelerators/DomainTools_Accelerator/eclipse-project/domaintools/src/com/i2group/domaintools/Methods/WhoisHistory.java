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
 * Method that represents a call to domaintools api method whois-history.
 * 
 */
public final class WhoisHistory implements IMethod
{
    private static final ITracer ITRACER = TracingManager.getTracer(WhoisHistory.class);
    private static final String WHOIS_HISTORY = "whois-history";

    @Override
    public String getName()
    {
        return WHOIS_HISTORY;
    }

    @Override
    public String execute(final String domain)
    {
        final String methodName = "execute";
        ITRACER.debugEntry(methodName);
        String encodedDomain = DomainToolsLoader.urlEncode(domain);
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {domain, encodedDomain};
            ITRACER.debug("domain is : {0}, encodedDomain is : {1}", values);
        }
        String response = DomainToolsLoader.makeRequest(encodedDomain
                + "/whois/history");

        HashMap<String, String> parameters = new HashMap<String, String>();

        XMLGregorianCalendar timestamp;
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
        parameters.put("domain", domain.toUpperCase());

        String transformed = DomainToolsLoader.transform(response,
                "/com/i2group/domaintools/transforms/whois-history.xslt",
                parameters);
        ITRACER.debugExit(methodName);
        return transformed;
    }

}

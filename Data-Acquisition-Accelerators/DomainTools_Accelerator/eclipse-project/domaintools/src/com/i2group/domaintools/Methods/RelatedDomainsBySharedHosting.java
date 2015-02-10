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
 * Method that represents a call to domaintools api method reverse-ip.
 * 
 */
public final class RelatedDomainsBySharedHosting implements IMethod
{
    private static final ITracer ITRACER = TracingManager.getTracer(RelatedDomainsBySharedHosting.class);
    private static final String REVERSE_IP = "reverse-ip";

    @Override
    public String getName()
    {
        return REVERSE_IP;
    }

    @Override
    public String execute(final String domain)
    {
        final String methodName = "execute";
        ITRACER.debugEntry(methodName);
        final String encodedDomain = DomainToolsLoader.urlEncode(domain);

        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {domain, encodedDomain};
            ITRACER.debug("domain is : {0}, encodedDomain is : {1}", values);
        }

        // Query to obtain results.
        final String response = DomainToolsLoader.makeRequest(encodedDomain
                + "/" + REVERSE_IP);

        final HashMap<String, String> parameters = new HashMap<String, String>();
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

        final String result = DomainToolsLoader.transform(response,
                "/com/i2group/domaintools/transforms/reverse-ip.xslt",
                parameters);
        ITRACER.debugExit(methodName);
        return result;
    }
}

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

import org.apache.wink.client.ClientWebException;

import com.i2group.domaintools.DomainToolsLoader;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;


/**
 * Method that represents a call to domaintools api method whois.
 * 
 */
public final class Whois implements IMethod
{
    private static final ITracer ITRACER = TracingManager.getTracer(Whois.class);
    private static final int STATUS_404 = 404;
    private static final String WHOIS = "whois";

    @Override
    public String getName()
    {
        return WHOIS;
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
        String response;
        try
        {
            response = DomainToolsLoader.makeRequest(encodedDomain + "/"
                    + WHOIS);
        }
        catch (ClientWebException ex)
        {
            // If the request fails, try a live request and see if it is
            // available that way.
            if ((ex.getResponse().getStatusCode() == STATUS_404)
                    && DomainToolsLoader.USELIVEWHOIS)
            {
                if (ITRACER.isWarnEnabled())
                {
                    Object[] values = {WHOIS, ex.getResponse().getStatusCode(), DomainToolsLoader.USELIVEWHOIS};
                    ITRACER.warn("Exception when making {0} request, response code was {1}, useLiveWhois is set to : {3}, so now trying a live request.", values);
                    ITRACER.warn(ex);
                }
                response = DomainToolsLoader.makeRequest(encodedDomain + "/"
                        + WHOIS + "/live");
            }
            else
            {
                if (ITRACER.isErrorEnabled())
                {
                    Object[] values = {WHOIS, ex.getResponse().getStatusCode(), DomainToolsLoader.USELIVEWHOIS};
                    ITRACER.error("Exception when making {0} request, response code was {1}, useLiveWhois is set to : {3}, so throwing excpetion again.", values);
                    ITRACER.error(ex);
                }
                throw ex;
            }
        }

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
        parameters.put("domain", domain.toUpperCase());

        final String transformed = DomainToolsLoader.transform(response,
                "/com/i2group/domaintools/transforms/whois.xslt",
                parameters);
        ITRACER.debugExit(methodName);
        return transformed;
    }
}

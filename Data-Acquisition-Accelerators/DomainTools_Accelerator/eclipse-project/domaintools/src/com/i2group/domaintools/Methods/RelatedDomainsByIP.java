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
 * Method that represents a call to domaintools api method host-domains.
 *
 */
public final class RelatedDomainsByIP implements IMethod
{
    private static final ITracer ITRACER = TracingManager.getTracer(RelatedDomainsByIP.class);
    private static final String HOST_DOMAINS = "host-domains";

    @Override
    public String getName()
    {
        return HOST_DOMAINS;
    }

    @Override
    public String execute(final String ip)
    {
        final String methodName = "execute";
        ITRACER.debugEntry(methodName);
        String encodedIp = DomainToolsLoader.urlEncode(ip);

        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {ip, encodedIp};
            ITRACER.debug("ip is : {0}, encodedIp is : {1}", values);
        }

        // Query to obtain results.
        String response = DomainToolsLoader.makeRequest(encodedIp
                + "/" + HOST_DOMAINS);

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
        String result = DomainToolsLoader.transform(response,
                "/com/i2group/domaintools/transforms/host-domains.xslt",
                parameters);
        ITRACER.debugExit(methodName);
        return result;
    }
}

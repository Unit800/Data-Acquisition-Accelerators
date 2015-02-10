/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.i2group.domaintools;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.i2group.domaintools.Methods.IMethod;
import com.i2group.domaintools.Methods.RelatedDomainsByIP;
import com.i2group.domaintools.Methods.RelatedDomainsBySharedHosting;
import com.i2group.domaintools.Methods.Whois;
import com.i2group.domaintools.Methods.WhoisHistory;
import com.i2group.domaintools.Methods.WhoisSearch;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;
import com.i2group.utils.ResourceHelper;

/**
 * Class with methods for calling the domaintools api and transforming the results into a Law Enforcement schema QueryResult.
 */
public final class DomainToolsLoader
{
    private static final ITracer ITRACER = TracingManager.getTracer(DomainToolsLoader.class);
    private static final String APIUSERNAME = DomainToolsSystemProperties.getApiUserName();
    private static final String APIKEY = DomainToolsSystemProperties.getApiKey();

    // Business logic.
    public static final int MAXRESULTS = DomainToolsSystemProperties.getMaxResults();
    public static final boolean USELIVEWHOIS = DomainToolsSystemProperties.getUseLiveWhoIs();

    private static final RestClient CLIENT = new RestClient();
    private static final TransformerFactory TRANSFORMERFACTORY = TransformerFactory
            .newInstance();

    public static final HashMap<String, IMethod> METHODS = new HashMap<String, IMethod>();

    /**
     * Constructor.
     */
    private DomainToolsLoader()
    {
        super();
        final String methodName = "DomainToolsLoader<init>";
        ITRACER.debugEntry(methodName);
        ITRACER.debugExit(methodName);
    }

    /**
     * Add a method to the list of methods.
     * 
     * @param method
     *            the method to add
     */
    private static void addMethod(final IMethod method)
    {
        final String methodName = "addMethod";
        ITRACER.debugEntry(methodName);

        METHODS.put(method.getName(), method);

        ITRACER.debugExit(methodName);

    }

    // Any new methods should be added here.
    static
    {
        final String methodName = "static initializer 1";
        ITRACER.debugEntry(methodName);
        addMethod(new RelatedDomainsByIP());
        addMethod(new RelatedDomainsBySharedHosting());
        addMethod(new Whois());
        addMethod(new WhoisSearch());
        addMethod(new WhoisHistory());
        ITRACER.debugExit(methodName);
    }

    /**
     * Encodes a string for use in a url.
     * 
     * @param query
     *            The string to encode
     * @return the encoded string
     */
    public static String urlEncode(final String query)
    {
        final String methodName = "urlEncode";
        ITRACER.debugEntry(methodName);
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {query};
            ITRACER.debug("urlEncode called for string {0}", values);
        }
        try
        {
            ITRACER.debugExit(methodName);
            return URLEncoder.encode(query, "UTF-8");
        }
        catch (UnsupportedEncodingException ex)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Exception occurred when encoding the url for the domaintools request.");
                ITRACER.error(ex);
            }
            throw new RuntimeException(ex);
        }
    }

    /**
     * Return the result of applying the specified transform to the passed in
     * xml string.
     * 
     * @param xml
     *            Apply the transform to this string
     * @param xslt
     *            Specified .xslt
     * @param parameters
     *            These parameters can be used in the specified .xslt
     * @return The result of the transform
     */
    public static String transform(final String xml, final String xslt,
            final Map<String, String> parameters)
    {
        final String methodName = "transform";
        ITRACER.debugEntry(methodName);
        try
        {
            Source transform = new StreamSource(
                    ResourceHelper.getResourceAsStream(xslt));
            Source source = new StreamSource(new StringReader(xml));
            StringWriter output = new StringWriter();
            Result result = new StreamResult(output);
            Transformer transformer = TRANSFORMERFACTORY
                    .newTransformer(transform);
            for (Entry<String, String> entry : parameters.entrySet())
            {
                transformer.setParameter(entry.getKey(), entry.getValue());
            }
            transformer.transform(source, result);
            ITRACER.debugExit(methodName);
            return output.toString();
        }
        catch (TransformerException ex)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Exception occurred when applying .xslt transform to domaintools response.");
                ITRACER.error(ex);
            }
            throw new RuntimeException(ex);
        }
    }

    /**
     * Perform a http GET with the specified url.
     * 
     * @param request
     *            The specified url
     * @return The response from the request
     */
    public static String makeRequest(final String request)
    {
        final String methodName = "makeRequest(String request)";
        ITRACER.debugEntry(methodName);
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {request};
            ITRACER.debug("request is : {0}", values);
        }
        String result = makeRequest(request, "");
        ITRACER.debugExit(methodName);
        return result;
    }

    /**
     * Perform a http GET with the specified url and extra parameters.
     * 
     * @param request
     *            Url
     * @param extra
     *            Url parameters
     * @return The response from the request
     */
    public static String makeRequest(final String request, final String extra)
    {
        final String methodName = "makeRequest(String request, String extra)";
        ITRACER.debugEntry(methodName);
        String fullRequest = "/v2/" + request;
        String timestamp = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"))
                .format(new Date());
        String full = "http://api.domaintools.com" + fullRequest
                + "?format=xml" + "&api_username=" + APIUSERNAME + "&api_key="
                + APIKEY + "&timestamp=" + timestamp + extra;
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {request, extra, full};
            ITRACER.debug("request is : {0}, extra is : {1}, full uri is : {2}", values);
        }
        Resource resource = CLIENT.resource(full);
        String result = resource.contentType(MediaType.TEXT_XML)
                .accept(MediaType.TEXT_XML).get(String.class);
        ITRACER.debugExit(methodName);
        return result;
    }
}

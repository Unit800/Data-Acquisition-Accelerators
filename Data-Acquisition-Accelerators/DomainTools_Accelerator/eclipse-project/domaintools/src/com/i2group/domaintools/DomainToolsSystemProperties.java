/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.i2group.domaintools;

import java.io.IOException;
import java.util.Properties;

import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;
import com.i2group.utils.ResourceHelper;

/**
 *  Utility class that exposes the domain tools system properties.
 *
 */
public final class DomainToolsSystemProperties
{
    private static final ITracer ITRACER = TracingManager.getTracer(DomainToolsSystemProperties.class);
    private static final String DOMAIN_TOOLS_SETTINGS_PROPERTIES_FILE_NAME = "DomainToolsSettings.properties";

    private static final Properties PROPERTIES = loadProperties();

    private static final String API_USER_NAME = PROPERTIES.getProperty("apiUsername");
    private static final String API_KEY = PROPERTIES.getProperty("apiKey");

    private static final int MAX_RESULTS = Integer.parseInt(PROPERTIES.getProperty("maxResults"));

    private static final boolean USE_LIVE_WHO_IS = Boolean.parseBoolean(PROPERTIES.getProperty("useLiveWhois"));

    /**
     * Private constructor.
     */
    private DomainToolsSystemProperties()
    {
        final String methodName = "DomainToolsSystemProperties<init>";
        ITRACER.debugEntry(methodName);
        ITRACER.debugExit(methodName);
    }

    /**
     * Load the Domain Tools System Properties.
     * 
     * @return See above.
     */
    private static Properties loadProperties()
    {
        final String methodName = "loadProperties";
        ITRACER.debugEntry(methodName);
        final Properties properties = new Properties();
        try
        {
            properties.load(ResourceHelper
                    .getResourceAsStream(DOMAIN_TOOLS_SETTINGS_PROPERTIES_FILE_NAME));
        }
        catch (IOException ex)
        {
            if (ITRACER.isErrorEnabled())
            {
                final Object[] values = {DOMAIN_TOOLS_SETTINGS_PROPERTIES_FILE_NAME};
                ITRACER.error("Exception occurred when trying to load the domain tools properties file : {0}.", values);
                ITRACER.error(ex);
            }
            throw new RuntimeException("Could not load "
                    + DOMAIN_TOOLS_SETTINGS_PROPERTIES_FILE_NAME
                    + ". A problem occurred: "
                    + ex.getClass().getCanonicalName() + ": " + ex.getMessage());
        }
        ITRACER.debugExit(methodName);
        return properties;
    }

    /**
     * Gets the API user name.
     * 
     * @return See above.
     */
    public static String getApiUserName()
    {
        return API_USER_NAME;
    }

    /**
     * Gets the API Key.
     * 
     * @return See above.
     */
    public static String getApiKey()
    {
        return API_KEY;
    }

    /**
     * Gets the maximum results to be returned.
     * 
     * @return See above.
     */
    public static int getMaxResults()
    {
        return MAX_RESULTS;
    }

    /**
     * Get the Use Live Who Is.
     * 
     * @return See above.
     */
    public static boolean getUseLiveWhoIs()
    {
        return USE_LIVE_WHO_IS;
    }
}

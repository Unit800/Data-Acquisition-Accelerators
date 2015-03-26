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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * General utilities class.
 */
public final class AcxiomUtils
{
    private static final ITracer ITRACER = TracingManager
            .getTracer(AcxiomUtils.class);
    private static final Properties PROPERTIES = getUserProperties();
    private static final String USER_CREDENTIALS_FILE = "UserCredentials.properties";

    /**
     * General utilities class.
     */
    private AcxiomUtils()
    {
        final String methodName = "AcxiomUtils<init>";
        ITRACER.debugEntry(methodName);
        ITRACER.debugExit(methodName);
    }

    /**
     * Function to transform Document to StreamSource.
     * 
     * @param doc
     *            Document that is to be transformed to a StreamSource
     * @return StreamSource StreamSource that is created by transforming the
     *         given Document
     */
    public static StreamSource transformDocumentToStreamSource(
            final Document doc)
    {
        final String methodName = "transformDocumentToStreamSource";
        ITRACER.debugEntry(methodName);

        final DOMSource domSource = new DOMSource(doc);
        final TransformerFactory factory = TransformerFactory.newInstance();

        try
        {
            final Transformer transformer = factory.newTransformer();
            final StreamResult streamResult = new StreamResult();
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            streamResult.setOutputStream(out);
            transformer.transform(domSource, streamResult);
            final ByteArrayInputStream in = new ByteArrayInputStream(
                    out.toByteArray());
            final StreamSource streamSource = new StreamSource(in);

            ITRACER.debugExit(methodName);
            return streamSource;
        }
        catch (TransformerConfigurationException e)
        {
            if (ITRACER.isErrorEnabled())
             {
                 ITRACER.error("Transformer configuration exception has occurred.");
                 ITRACER.error(e);
             }
            throw new RuntimeException(e);
        }
        catch (TransformerException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("An exception has occurred during the transformation process.");
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * This method updates an IAP ready QueryResult to store the DPPA and GLBA
     * permission values in the originId of a Party entity so that they can be
     * used in further searches.
     * 
     * @param streamSource StreamSource The streamSource that is to be updated
     * @param xpaths
     *            ArrayList<String> containing xpath expressions that navigate
     *            to the OriginId Keys.
     * @param dppa
     *            String representing the DPPA permission value that need to be
     *            updated so that further searches can be made.
     * @param glba
     *            String representing the GLBA permission value that need to be
     *            updated so that further searches can be made.
     * @return StreamSource StreamSource containing the updated QueryResult.
     */
    public static StreamSource updateQueryResult(
            final StreamSource streamSource, final ArrayList<String> xpaths,
            final String dppa, final String glba)
    {
        final String methodName = "updateQueryResult";
        ITRACER.debugEntry(methodName);

        final String dppaToken = "DPPA:";
        final String glbaToken = "GLBA:";
        final String dppaTextContent = dppaToken + dppa;
        final String glbaTextContent = glbaToken + glba;

        try
        {
            final DocumentBuilder db = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            final Document doc = db.parse(streamSource.getInputStream());
            final XPathFactory xPathFactory = XPathFactory.newInstance();
            final XPath xpath = xPathFactory.newXPath();
            for (String x : xpaths)
            {
                final XPathExpression expression = xpath.compile(x);
                final NodeList keyList = (NodeList) expression.evaluate(doc,
                        XPathConstants.NODESET);
                for (int i = 1; i < keyList.getLength(); i++)
                {
                    final Node firstChild = keyList.item(i).getFirstChild();
                    final String nodeValue = firstChild.getNodeValue();
                    if (nodeValue.startsWith(dppaToken))
                    {
                        firstChild.setTextContent(dppaTextContent);
                    }
                    else if (nodeValue.startsWith(glbaToken))
                    {
                        firstChild.setTextContent(glbaTextContent);
                    }
                }
            }
            final StreamSource updatedSteamSource = transformDocumentToStreamSource(doc);
            ITRACER.debugExit(methodName);
            return updatedSteamSource;
        }
        catch (ParserConfigurationException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Parser configuration error");
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
        }
        catch (SAXException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Exception occurred parsing the stream source.");
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Exception has occurred attempting to open an InputStream");
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
        }
        catch (XPathExpressionException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("The xpath expression(s) passed to this method is incorrect.");
                ITRACER.error(e);
            }
            if (ITRACER.isDebugEnabled())
            {
                for (String xpath : xpaths)
                {
                    ITRACER.debug(xpath);
                }
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to load properties file as resource.
     * 
     * @return Properties object used to get Acxiom username and password from
     *         properties file.
     */
    private static Properties getUserProperties()
    {
        final String methodName = "getUserProperties";
        ITRACER.debugEntry(methodName);

        try
        {
            final Properties properties = new Properties();
            final InputStream inputStream = AcxiomUtils.class.getClassLoader()
                    .getResourceAsStream(USER_CREDENTIALS_FILE);

            if (ITRACER.isInfoEnabled())
            {
                final Object[] values = { USER_CREDENTIALS_FILE };
                ITRACER.info("Loading {0} as resource", values);
            }

            properties.load(inputStream);

            ITRACER.debugExit(methodName);

            return properties;
        }
        catch (IOException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                final Object[] values = { USER_CREDENTIALS_FILE };
                ITRACER.error("Failed to load {0} as resource", values);
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * Return the user name for Acxiom.
     * 
     * @return String AcxiomUsername
     */
    public static String getUser()
    {
        final String methodName = "getUser";
        ITRACER.debugEntry(methodName);

        final String username = PROPERTIES.getProperty("AcxiomUsername");

        if (username.isEmpty())
        {
            final Exception e = new Exception("No username found");
            if (ITRACER.isErrorEnabled())
            {
                final Object[] values = {USER_CREDENTIALS_FILE };
                ITRACER.error("No username found in {0}", values);
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
        }
        ITRACER.debugExit(methodName);

        return username;
    }

    /**
     * Return the password for Acxiom.
     * 
     * @return String AcxiomPassword
     */
    public static String getPass()
    {
        final String methodName = "getPass";
        ITRACER.debugEntry(methodName);

        final String password = PROPERTIES.getProperty("AcxiomPassword");

        if (password.isEmpty())
        {
            final Exception e = new Exception("No password found");
            if (ITRACER.isErrorEnabled())
            {
                final Object[] values = {USER_CREDENTIALS_FILE };
                ITRACER.error("No password found in {0}", values);
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
        }

        ITRACER.debugExit(methodName);

        return password;
    }

    /**
     * Transforms a StreamSource containing the soapResponse data from Acxiom
     * into platform-compatible XML.
     * 
     * @param soapResponse
     *            StreamSource The streamSource to be transformed to
     *            platform-compatible XML data.
     * @param xsltFileName
     *            String referencing the XSLT file that is used to perform the
     *            transform.
     * @return StreamSource containing the platform-compatible XML data.
     */
    public static StreamSource transformToPlatformCompatibleXML(
            final StreamSource soapResponse, final String xsltFileName)
    {
        final String methodName = "transformToPlatformCompatibleXML";
        ITRACER.debugEntry(methodName);

        final String transformSourceSystemXml = AcxiomXmlTransformer
                .transformSourceSystemXml(xsltFileName, soapResponse);

        final StreamSource transformedXML = new StreamSource(
                new ByteArrayInputStream(transformSourceSystemXml.getBytes()));

        ITRACER.debugExit(methodName);

        // return source version of transformed xml.
        return transformedXML;
    }

    /**
     * Takes a search string and creates a map where the 'key' is the 'type' and
     * the 'value' is the type's value.
     * 
     * @param searchString
     *            String the string containing the key/value pairs to be
     *            processed
     * @return Map<String, String> a map where the 'key' is the 'type' and the
     *         value is the 'types' value.
     */
    public static Map<String, String> parseString(final String searchString)
    {
        final String methodName = "parseString";
        ITRACER.debugEntry(methodName);

        final Map<String, String> typesAndValuesMap = new HashMap<String, String>();

        final String[] terms = searchString.split(";");

        for (String term : terms)
        {
            String[] parts = term.split(":=");

            String type = parts[0];
            String value = parts[1];

            typesAndValuesMap.put(type.toUpperCase(), value);
        }
        ITRACER.debugExit(methodName);
        return typesAndValuesMap;
    }

    /**
     * Returns the corresponding 'value' for the 'type' specified. If the 'type'
     * is not present in the map, then 'null' is returned.
     * 
     * @param typesAndValuesMap
     *            a map where the 'key' is the 'type' and the 'value' is the
     *            type's value
     * @param type
     *            the 'type' for which the 'value' is sought
     * @return String the value corresponding to the key
     */
    public static String getValueForType(
            final Map<String, String> typesAndValuesMap, final String type)
    {
        final String methodName = "getValueForType";
        ITRACER.debugEntry(methodName);

        final String upperCaseType = type.toUpperCase();

        if (typesAndValuesMap.containsKey(type.toUpperCase()))
        {
            final String key = typesAndValuesMap.get(upperCaseType);

            if (ITRACER.isDebugEnabled())
            {
                final Object[] values = { key };
                ITRACER.debug("Found key : {0}", values);
            }

            ITRACER.debugExit(methodName);

            return key;
        }

        if (ITRACER.isDebugEnabled())
        {
            final Object[] values = { type };
            ITRACER.debug(
                    "Type {0} is not present in the map of types and values. Returning null.",
                    values);
        }

        ITRACER.debugExit(methodName);

        return null;
    }

    /**
     * Marshal the response to a ResponseType xml.
     * 
     * @param jaxbContextPath
     *            The path to the schema package.
     * @param response
     *            Java class for ResponseType complex type
     * @return StreamSource StreamSource that is created by transforming the
     *         given Document
     */
    public static StreamSource marshallResponse(final String jaxbContextPath,
            final Object response)
    {
        final String methodName = "marshallResponse";
        ITRACER.debugEntry(methodName);

        try
        {
            final JAXBContext jc = JAXBContext.newInstance(jaxbContextPath);
            final Marshaller m = jc.createMarshaller();
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            m.marshal(response, bos);
            final ByteArrayInputStream bis = new ByteArrayInputStream(
                    bos.toByteArray());

            if (ITRACER.isDebugEnabled())
            {
                ITRACER.debug("Unmarshalling of response successful.");
            }

            ITRACER.debugExit(methodName);
            return new StreamSource(bis);
        }
        catch (JAXBException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Error unmarshalling the response.");
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the first element in the iterable.
     * 
     * @param iterable
     *            the iterable from which to return the first element
     * @param <T>
     *            The type of the element in the iterable.
     * @return first element in the iterable, null if there is no element.
     */
    public static <T> T getFirstItem(final Iterable<T> iterable)
    {
        final String methodName = "getFirstItem";
        ITRACER.debugEntry(methodName);

        T firstItem = null;
        // If the iterable has an nextItem, in this case first item.
        if (iterable.iterator().hasNext())
        {
            if (iterable instanceof Collection)
            {
                if (iterable instanceof List)
                {
                    List<T> list = (List<T>) iterable;
                    firstItem = list.get(0);
                }
            }
            else
            {
                for (T t : iterable)
                {
                    firstItem = t;
                    break;
                }

            }
        }
        ITRACER.debugExit(methodName);
        return firstItem;
    }
}

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

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;
import com.i2group.utils.ResourceHelper;

/**
 * A wrapper around standard Java XSLT support that can transform XML files
 * using XSL.
 */
public final class AcxiomXmlTransformer
{
    private static final ITracer ITRACER = TracingManager
            .getTracer(AcxiomXmlTransformer.class);

    /**
     * Constructs a new {@link AcxiomXmlTransformer}.
     */
    private AcxiomXmlTransformer()
    {
        final String methodName = "AcxiomXmlTransformer<init>";
        ITRACER.debugEntry(methodName);
        ITRACER.debugExit(methodName);
    }

    /**
     * Transform a StreamSource to platform compatible XML using XSLT.
     * 
     * @param xsltFileName
     *            String representing the XSLT file to be used in the transform.
     * @param soapResponse
     *            StreamSource containing a SoapResponse from Acxiom.
     * @return String containing platform-compatible xml.
     */
    public static String transformSourceSystemXml(final String xsltFileName,
            final StreamSource soapResponse)
    {
        final String methodName = "transformSourceSystemXml";
        ITRACER.debugEntry(methodName);

        final InputStream xsltStream = ResourceHelper
                .getResourceAsStream(xsltFileName);
        final StreamSource xsltSource = new StreamSource(xsltStream);
        final StringWriter output = new StringWriter();
        final Result result = new StreamResult(output);
        final TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory
                .newInstance();
        try
        {
            final Transformer transformer = transformerFactory
                    .newTransformer(xsltSource);

            if (ITRACER.isDebugEnabled())
            {
                final Object[] values = { xsltFileName };
                ITRACER.debug("Transforming XML using XSLT : {0}", values);
            }

            transformer.transform(soapResponse, result);

            final String transformedXML = output.toString();

            if (ITRACER.isDebugEnabled())
            {
                final Object[] values = { output };
                ITRACER.debug("Transformed XML : {0}", values);
            }

            ITRACER.debugExit(methodName);

            return transformedXML;
        }
        catch (TransformerConfigurationException ex)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Transformer configuration exception has occurred.");
                ITRACER.error(ex);
            }
            throw new RuntimeException(ex);
        }
        catch (TransformerException ex)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("An exception has occurred during the transformation process. More information at DEBUG.");
                ITRACER.error(ex);
            }
            throw new RuntimeException(ex);
        }
        finally
        {
            try
            {
                xsltStream.close();
            }
            catch (Exception ex)
            {
                throw new RuntimeException(ex);
            }
        }
    }
}

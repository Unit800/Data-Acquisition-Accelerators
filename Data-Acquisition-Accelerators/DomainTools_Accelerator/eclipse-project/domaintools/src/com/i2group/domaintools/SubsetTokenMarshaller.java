/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */
package com.i2group.domaintools;

import javax.xml.bind.JAXBContext;

import com.i2group.apollo.common.jaxb.XmlConvertor;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * Marshalls and unmarshalls the examples subset token.
 */
public final class SubsetTokenMarshaller
{
    private static final ITracer ITRACER = TracingManager.getTracer(SubsetTokenMarshaller.class);
    private JAXBContext context;

    /**
     * Constructs a new {@link SubsetTokenMarshaller}.
     */
    public SubsetTokenMarshaller()
    {
        final String methodName = "SubsetTokenMarshaller<init>";
        ITRACER.debugEntry(methodName);
        try
        {
            context = JAXBContext.newInstance(SubsetToken.class);
            ITRACER.debugExit(methodName);
        }
        catch (Exception ex)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Exception occurred when creating a jaxb context for class SubsetToken.");
                ITRACER.error(ex);
            }
            throw new RuntimeException(ex);
        }
    }

    /**
     * Unmarshalls to {@link SubsetToken} from subset token {@link String}.
     * 
     * @param subsetTokenXmlString
     *            The subset token as a string.
     * @return the {@link SubsetToken}.
     */
    public SubsetToken unmarshall(final String subsetTokenXmlString)
    {
        final String methodName = "unmarshall";
        ITRACER.debugEntry(methodName);
        final XmlConvertor xmlConvertor = new XmlConvertor(context);
        SubsetToken result = (SubsetToken) xmlConvertor.fromXml(subsetTokenXmlString);
        ITRACER.debugExit(methodName);
        return result;
    }

    /**
     * Marshall to a string presentation of the XML from the {@link Data}.
     * 
     * @param subsetToken
     *            The subset token.
     * @return See above.
     */
    public String marshall(final SubsetToken subsetToken)
    {
        final String methodName = "marshall";
        ITRACER.debugEntry(methodName);
        final XmlConvertor xmlConvertor = new XmlConvertor(context);
        final String xml = xmlConvertor.toXml(subsetToken);
        ITRACER.debugExit(methodName);
        return xml;
    }
}

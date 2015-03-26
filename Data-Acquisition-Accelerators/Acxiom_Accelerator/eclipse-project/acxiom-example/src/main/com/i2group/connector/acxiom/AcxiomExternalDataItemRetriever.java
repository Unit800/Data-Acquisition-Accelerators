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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.i2group.apollo.externaldata.edrs.ContextResultMapping;
import com.i2group.apollo.externaldata.edrs.ExternalDataContextResults;
import com.i2group.apollo.externaldata.edrs.IExternalDataItemRetriever;
import com.i2group.apollo.externaldata.transport.ProvenanceGroup;
import com.i2group.apollo.model.provenance.transport.CardProvenance;
import com.i2group.connector.acxiom.request.ComprehensiveReportRequest;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * A mechanism that provides direct access to data in an external data source that is identified through specified provenance information.
 */
public final class AcxiomExternalDataItemRetriever implements
        IExternalDataItemRetriever
{
    private static final ITracer ITRACER = TracingManager
            .getTracer(AcxiomExternalDataItemRetriever.class);
    private static final String COMP_REPORT_XSLT_TRANSFORM = "ComprehensiveReportToCounterFraud.xslt";

    /**
     * Build the ExternalDataContextResults object from the returned Acxiom XML
     * and return it to IAP. Note: In this implementation we do not check the
     * maxNumberOfItems
     * 
     * @param provenanceGroups
     *            The ProvenanceGroups that contain the information that this
     *            service will use to identify data in the external data
     *            provider.
     * @param maxNumberOfItems
     *            The maximum number of Items to be returned in the
     *            ExternalDataResults.
     * @param shouldThrowIfTooManyItems
     *            true to throw an exception (and return no results) when the
     *            number of Items to return exceeds maxNumberOfItems; false to
     *            throw no exception and return up to maxNumberOfItems results.
     *            If maxNumberOfItems is not exceeded, this parameter has no
     *            effect.
     * @return ExternalDataContextResults an ExternalDataResults that contains
     *         Items from an external data provider that are related to
     *         previously retrieved Items. The data for the previously retrieved
     *         Items is identified using information in the specified
     *         ProvenanceGroups.
     */
    @Override
    public ExternalDataContextResults getContext(
            final Collection<ProvenanceGroup> provenanceGroups, final int maxNumberOfItems,
            final boolean shouldThrowIfTooManyItems)
    {
        final String methodName = "getContext";
        ITRACER.debugEntry(methodName);

        ExternalDataContextResults results = buildContentResult(
                provenanceGroups, maxNumberOfItems, shouldThrowIfTooManyItems);

        ITRACER.debugExit(methodName);

        return results;
    }

    /**
     * Build the ExternalDataContextResults object from the returned Acxiom XML
     * and return it to IAP. Note: In this implementation we do not check the
     * maxNumberOfItems
     * 
     * @param provenanceGroups
     *            The ProvenanceGroups that contain the information that this
     *            service will use to identify data in the external data
     *            provider.
     * @param maxNumberOfItems
     *            The maximum number of Items to be returned in the
     *            ExternalDataResults.
     * @param shouldThrowIfTooManyItems
     *            true to throw an exception (and return no results) when the
     *            number of Items to return exceeds maxNumberOfItems; false to
     *            throw no exception and return up to maxNumberOfItems results.
     *            If maxNumberOfItems is not exceeded, this parameter has no
     *            effect.
     * @return ExternalDataContextResults an ExternalDataResults that contains
     *         Items from an external data provider that are related to
     *         previously retrieved Items. The data for the previously retrieved
     *         Items is identified using information in the specified
     *         ProvenanceGroups.
     */
    public ExternalDataContextResults buildContentResult(
            final Collection<ProvenanceGroup> provenanceGroups, final int maxNumberOfItems,
            final boolean shouldThrowIfTooManyItems)
    {
        final String methodName = "buildContentResult";
        ITRACER.debugEntry(methodName);

        final boolean complete = true;
        String dppa = null;
        String glba = null;
        // Get the first provenance group
        final ProvenanceGroup provenanceGroup = AcxiomUtils
                .getFirstItem(provenanceGroups);
        // Get the card provenances for the first provenance group.
        final Collection<CardProvenance> provenances = provenanceGroup
                .getProvenances();
        // Get the firstCardProvenance
        final CardProvenance firstCardProvenance = AcxiomUtils
                .getFirstItem(provenances);
        // Get the originId Key for the firstCardProvenance
        final Collection<String> originKeys = firstCardProvenance.getOriginId()
                .getKey();
        // Get the keys from the card provenances
        final Collection<String> keys = getKeysFromProvenance(provenances);
        StreamSource iapFormatSource = null;

        // set the DPPA and GLBA values to the values found in the OriginId key
        for (String originKey : originKeys)
        {
            if (originKey.startsWith("DPPA"))
            {
                dppa = originKey.split(":")[1];
            }
            else if (originKey.startsWith("GLBA"))
            {
                glba = originKey.split(":")[1];
            }
        }

        // Get the type of the entity that was selected by the user
        final String entityType = firstCardProvenance.getOriginId()
                .getType();
        //Get the originIdKey which is the key used to perform a comprehensive search.
        final String originIdKey = AcxiomUtils.getFirstItem(keys).toString();

        if (ITRACER.isDebugEnabled())
        {
            final Object[] values = { entityType, originIdKey };
            ITRACER.debug("Entity selected : {0}. Origin ID key : {1}", values);
        }

        // We only perform a ComprehensiveReport search on Party entities
        if (entityType.equalsIgnoreCase("Party"))
        { // if it is a valid "Party" entity, perform a ComprehensiveReport
            final ComprehensiveReportRequest compReportRequest = new ComprehensiveReportRequest(
                    originIdKey, dppa, glba);
            StreamSource soapResponse = compReportRequest.execute();
            iapFormatSource = AcxiomUtils.transformToPlatformCompatibleXML(
                    soapResponse, COMP_REPORT_XSLT_TRANSFORM);
        }
        else
        {
            if (ITRACER.isDebugEnabled())
            {
                final Object[] values = { entityType };
                ITRACER.debug("Cannot perform search for entity {0}.", values);
            }
            // create a single entity piece of IAP ready XML. The entity is
            // created from the provenance information of the originating entity
            // that is passed to getContext()
            // In the case that the entity passed to getContext is not valid for
            // a ComprehensiveReport, we must send back the entity that was
            // passed in to prevent an exception shown in the UI.
            iapFormatSource = createSingleEntityQueryResult(entityType,
                    originIdKey);
        }

        // add the IDs of every item contained in our QueryResult. The itemIds
        // are then passed to the ContextResultMapping.
        // The values are taken from the OriginId within the QueryResult
        final Collection<String> itemIds = new HashSet<String>();
        itemIds.addAll(getAllItemIds(iapFormatSource));
        final ContextResultMapping contextItemGroup = new ContextResultMapping(
                provenanceGroup.getId(), itemIds, complete);
        final Collection<ContextResultMapping> contextItemGroups = new HashSet<ContextResultMapping>();
        contextItemGroups.add(contextItemGroup);

        // Create xpath ArrayList containing xpath to the OriginId key for a
        // Party entity, so that we can update the values of
        // DPPA and GLBA within the QueryResult.
        final ArrayList<String> xpaths = new ArrayList<String>();
        xpaths.add("/QueryResult/Partys/Party[1]/Cards/Card[1]/*[local-name()=\'CardProvenance\']/OriginId/Key/String");
        xpaths.add("/QueryResult/Partys/Party[2]/Cards/Card[1]/*[local-name()=\'CardProvenance\']/OriginId/Key/String");
        // This function is called to update the QueryResult to contain the DPPA
        // and GLBA values so that further searches can be
        // made using these values. This step is also needed because the DPPA +
        // GLBA values
        // are in the OriginId key and if they don't match duplicates will be
        // shown in the GUI.
        final StreamSource updatedQueryResult = AcxiomUtils.updateQueryResult(
                iapFormatSource, xpaths, dppa, glba);

        final ExternalDataContextResults externalDataContextResults = new ExternalDataContextResults(
                updatedQueryResult, contextItemGroups);

        ITRACER.debugExit(methodName);

        return externalDataContextResults;
    }

    /**
     * Not implemented.
     * 
     * @param provenanceGroups
     *            ProvenanceGroups that contain CardProvenances that identify
     *            the relevant information in the external data source.
     * @return a Source containing platform-compatible XML that describes items
     *         that are associated with the specified ProvenanceGroups.
     */
    @Override
    public Source getLatestItems(final Collection<ProvenanceGroup> provenanceGroups)
    {
        final UnsupportedOperationException ex = new UnsupportedOperationException();
        if (ITRACER.isErrorEnabled())
        {
            ITRACER.error("getLatestItems is not supported");
            ITRACER.error(ex);
        }
        throw ex;
    }

    /**
     * Method to extract Key values from a QueryResult. The values are taken from
     * the OriginId within the QueryResult. These are needed to create the
     * ContextResultMapping object.
     * 
     * @param queryResult
     *            StreamSource containing the queryResult XML
     * @return Collection<String> Collection that contains all the keys in the
     *         queryResult XML
     */
    private Collection<String> getAllItemIds(final StreamSource queryResult)
    {
        final String methodName = "getAllItemIds";
        ITRACER.debugEntry(methodName);
        final String xpathExpression = "//Key/String[1]";

        try
        {
            final Collection<String> keys = new HashSet<String>();
            final DocumentBuilder db = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            final Document doc = db.parse(queryResult.getInputStream());
            final XPathFactory xPathFactory = XPathFactory.newInstance();
            final XPath xpath = xPathFactory.newXPath();
            final XPathExpression expression = xpath.compile(xpathExpression);
            final NodeList keyList = (NodeList) expression.evaluate(doc,
                    XPathConstants.NODESET);

            for (int i = 0; i < keyList.getLength(); i++)
            {
                final Node firstChild = keyList.item(i).getFirstChild();
                if (firstChild != null)
                {
                    keys.add(firstChild.getNodeValue());
                }
            }
            queryResult.getInputStream().reset();

            ITRACER.debugExit(methodName);

            return keys;
        }
        catch (ParserConfigurationException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Exception occurred parsing the query result.");
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
        }
        catch (SAXException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Exception occurred parsing the InputStream.");
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
                final Object[] values = { xpathExpression };
                ITRACER.error("The xpath expression has caused an exception. {0}",  values);
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * @param provenances
     *            Collection<CardProvenance> containing the card provenances to take the
     *            keys from.
     * @return keys Collection<String> containing the keys from the passed card
     *         provenance.
     */
    private Collection<String> getKeysFromProvenance(
            final Collection<CardProvenance> provenances)
    {
        final String methodName = "getKeysFromProvenance";
        ITRACER.debugEntry(methodName);

        final Collection<String> keys = new ArrayList<String>();

        // Use the cardProvenance to find the OriginId key, then add it to the
        // keys list.
        // The value in the key list is then used to perform the
        // ComprehensiveReport search.
        for (CardProvenance cardProvenance : provenances)
        {
            final String key = findKeyInProvenance(cardProvenance);
            if (key != null)
            {
                keys.add(key);
            }
        }

        if (ITRACER.isDebugEnabled())
        {
            ITRACER.debug("Keys from provenance : ");
            for (String key : keys)
            {
                ITRACER.debug(key);
            }
        }

        ITRACER.debugExit(methodName);

        return keys;
    }

    /**
     * Finds the key held within the CardProvenance.
     * 
     * @param cardProvenance
     *            CardProvencance for a given item.
     * @return String String containing the key for the given provenance.
     */
    private String findKeyInProvenance(final CardProvenance cardProvenance)
    {
        final String methodName = "findKeyInProvenance";
        ITRACER.debugEntry(methodName);

        String key = null;
        final Collection<String> cardProvenanceKeys = cardProvenance
                .getOriginId().getKey();
        if (cardProvenanceKeys.size() > 0)
        {
            key = AcxiomUtils.getFirstItem(cardProvenanceKeys);
        }

        ITRACER.debugExit(methodName);

        return key;
    }

    /**
     * In the case the entity sent to the show context is invalid, we will not
     * request a comprehensive report from Acxiom. This function creates and
     * returns a single entity IAP ready QueryResult which is built using the
     * invalid entity that is passed to the getContext function.
     * 
     * @param entityType
     *            The type of entity to create a singleEntityQueryResult for
     * @param originId
     *            The originId Key used in creating the singleEntityQueryResult
     * @return StreamSource StreamSource containing a singleEntityQueryResult
     *         for the entityType specified
     */
    private StreamSource createSingleEntityQueryResult(final String entityType,
            final String originId)
    {
        final String methodName = "createSingleEntityQueryResult";
        ITRACER.debugEntry(methodName);

        try
        {
            final String baseXpath = "/QueryResult/" + entityType + "s/"
                    + entityType + "/Cards/Card";

            // Create a Document from the SingleQueryResult.xml file in the
            // resources folder.
            final DocumentBuilder db = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            final Document doc = db.parse(this.getClass().getResourceAsStream(
                    "/SingleQueryResult.xml"));
            final XPathFactory xPathFactory = XPathFactory.newInstance();
            final XPath xpath = xPathFactory.newXPath();

            // Now set the values to represent the Entity that was passed in.
            // Setting the root element name
            XPathExpression expression = xpath.compile("/QueryResult/Nodes");
            Node node = (Node) expression.evaluate(doc, XPathConstants.NODE);
            doc.renameNode(node, "", entityType + "s");

            // Setting element name
            expression = xpath.compile("/QueryResult/" + entityType + "s"
                    + "/Node");
            node = (Node) expression.evaluate(doc, XPathConstants.NODE);
            doc.renameNode(node, "", entityType);
            
            // Adding a value to the OriginId Key
            expression = xpath.compile(baseXpath
                    + "/*[local-name()=\'CardProvenance\']/OriginId/Key/String");
            node = (Node) expression.evaluate(doc, XPathConstants.NODE);
            node.setTextContent(originId);

            // Changing the "Type" attribute of the OriginId element to
            // represent the type of the current entity.
            expression = xpath.compile(baseXpath + "/*[local-name()=\'CardProvenance\']/OriginId");
            node = (Node) expression.evaluate(doc, XPathConstants.NODE);
            node.getAttributes().getNamedItem("Type").setNodeValue(entityType);
            
            // Adding value to the ItemId.
            expression = xpath.compile("/QueryResult/" + entityType + "s/"
                    + entityType + "/ItemId");
            node = (Node) expression.evaluate(doc, XPathConstants.NODE);
            node.setTextContent(originId);

            final StreamSource source = AcxiomUtils
                    .transformDocumentToStreamSource(doc);

            ITRACER.debugExit(methodName);

            return source;
        }
        catch (SAXException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Error parsing input stream");
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("IOException opening InputStream");
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
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
        catch (XPathExpressionException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("The xpath expression(s) have caused an exception. View xpath(s) at DEBUG in the logs.");
                ITRACER.error(e);
            }
            throw new RuntimeException(e);
        }
    }
}

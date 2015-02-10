/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.i2group.domaintools.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.wink.client.ClientWebException;

import com.i2group.apollo.externaldata.subsetexplorationservice.IExternalDataSubsetExplorationService;
import com.i2group.apollo.externaldata.subsetexplorationservice.sdk.ExternalDataSubsetExplorationServiceFactory;
import com.i2group.apollo.externaldata.transport.ExternalDataSubsetIdentifier;
import com.i2group.apollo.rest.WebServiceContextAdapter;
import com.i2group.domaintools.DomainToolsLoader;
import com.i2group.domaintools.SubsetToken;
import com.i2group.domaintools.SubsetTokenMarshaller;
import com.i2group.domaintools.Methods.IMethod;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;
import com.i2group.utils.exception.WrappedCheckedException;

/**
 * REST Resource for Subsets.
 */
@Path("/daodSubsets")
public class ExternalDataSubsetResource implements IExternalDataSubsetResource
{
    private static final ITracer ITRACER = TracingManager.getTracer(ExternalDataSubsetResource.class);
    @Context
    private ServletContext servletContext;
    @Context
    private HttpServletRequest httpServletRequest;
    @Context
    private HttpServletResponse httpServletResponse;
    @Context
    private UriInfo uriInfo;
    private final ExternalDataSubsetExplorationServiceFactory externalDataSubsetExplorationServiceFactory;
    private final SubsetTokenMarshaller subsetTokenMarshaller;

    /**
     * Constructs a new {@link ExternalDataSubsetResource}.
     */
    public ExternalDataSubsetResource()
    {
        final String methodName = "ExternalDataSubsetResource<init>";
        ITRACER.debugEntry(methodName);
        externalDataSubsetExplorationServiceFactory = new ExternalDataSubsetExplorationServiceFactory();
        subsetTokenMarshaller = new SubsetTokenMarshaller();
        ITRACER.debugExit(methodName);
    }

    /**
     * Handles GET /daodSubsets. Makes a call to domaintools based on the
     * subsetRequest. Constructs a QueryResult from the domaintools response
     * Stores the serialized QueryResult in a file. Initializes a new subset id
     * in IAP. The subset Id is returned in the Response
     * 
     * @param subsetRequest
     *            The {@link SubsetRequest}.
     * @return The {@link Response} for this rest resource.
     */
    @Override
    public final Response createSubset(final SubsetRequest subsetRequest)
    {
        final String methodName = "createSubset";
        ITRACER.debugEntry(methodName);
        if (ITRACER.isInfoEnabled())
        {
            ITRACER.info("Restful Post request to create subset for domaintools connector received.");
        }
        validateRequest(subsetRequest);
        final byte[] loadedData;
        try
        {
            loadedData = getDataFromExternalDataProvider(subsetRequest);
        }
        catch (ClientWebException ex)
        {
            if (ITRACER.isWarnEnabled())
            {
                ITRACER.warn("Exception occurred when getting data from domaintools api.");
                ITRACER.warn(ex);
            }
            return Response.status(ex.getResponse().getStatusCode())
                    .type("text/plain").entity(ex.getResponse().getMessage())
                    .build();
        }
        final String pathToSubset = storeSubset(loadedData, subsetRequest);
        final ExternalDataSubsetIdentifier subsetIdentifier = createSubsetIdentifier(pathToSubset);
        final ExternalDataSubsetIdentifier initializedSubsetIdentifier = initializeSubset(subsetIdentifier);
        final SubsetResponse response = createResponseWithSubsetToken(initializedSubsetIdentifier);
        Response result = Response.ok(response).build();
        ITRACER.debugExit(methodName);
        return result;
    }

    /**
     * Eagerly initializes the subset, enabling further searches against the
     * subset.
     * 
     * @param subsetIdentifier
     *            The {@link ExternalDataSubsetIdentifier}.
     * @return The updated {@link ExternalDataSubsetIdentifier}.
     */
    private ExternalDataSubsetIdentifier initializeSubset(
            final ExternalDataSubsetIdentifier subsetIdentifier)
    {
        final String methodName = "initializeSubset";
        ITRACER.debugEntry(methodName);
        final WebServiceContextAdapter webServiceContextAdapter = new WebServiceContextAdapter(
                servletContext, httpServletRequest, httpServletResponse,
                uriInfo);
        final IExternalDataSubsetExplorationService externalDataService = externalDataSubsetExplorationServiceFactory
                .createExternalDataService(webServiceContextAdapter);

        ExternalDataSubsetIdentifier newIdentifierForInitializedSubset = null;

        try
        {
            newIdentifierForInitializedSubset = externalDataService
                    .initialize(subsetIdentifier);
        }
        catch (Exception ex)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Exception occurred when trying to initialize the subset.");
                ITRACER.error(ex);
            }
            throwWebApplicationExceptionAsInternalServerError(ex);
        }
        ITRACER.debugExit(methodName);
        return newIdentifierForInitializedSubset;
    }

    /**
     * Validates the REST {@link SubsetRequest}.
     * 
     * @param subsetRequest
     *            The {@link SubsetRequest}.
     */
    private void validateRequest(final SubsetRequest subsetRequest)
    {
        final String methodName = "validateRequest";
        ITRACER.debugEntry(methodName);
        if (subsetRequest.getSearchString() == null)
        {
            if (ITRACER.isDebugEnabled())
            {
                ITRACER.debug("subsetRequest.getSearchString() is null");
            }
            throwWebApplicationExceptionAsBadRequest("Invalid parameters: No search string provided");
        }
        ITRACER.debugExit(methodName);
    }

    /**
     * Creates and returns the {@link SubsetResponse} containing the
     * {@link ExternalDataSubsetIdentifier}.
     * 
     * @param subsetIdentifier
     *            The {@link ExternalDataSubsetIdentifier}.
     * @return See above.
     */
    private SubsetResponse createResponseWithSubsetToken(
            final ExternalDataSubsetIdentifier subsetIdentifier)
    {
        final String methodName = "createResponseWithSubsetToken";
        ITRACER.debugEntry(methodName);
        // The datasource identifier is the context root of the we application.
        final String dataSourceId = httpServletRequest.getContextPath()
                .substring(1);
        final SubsetResponse response = new SubsetResponse(subsetIdentifier,
                dataSourceId);
        ITRACER.debugExit(methodName);
        return response;
    }

    /**
     * Creates and returns the {@link ExternalDataSubsetIdentifier}.
     * 
     * @param pathToSubset
     *            The path to the subset XML file.
     * @return See above.
     */
    private ExternalDataSubsetIdentifier createSubsetIdentifier(
            final String pathToSubset)
    {
        final String methodName = "createSubsetIdentifier";
        ITRACER.debugEntry(methodName);
        final String name = "domaintoolssubset";
        final String key = UUID.randomUUID().toString();

        /*
         * Adding a prefix ensures that a new subset is initialised each time
         * this REST resource is called even with the same source file path.
         */
        final String uniquePrefix = key;
        final SubsetToken subsetToken = new SubsetToken(uniquePrefix,
                pathToSubset);

        final String subsetTokenAsString = subsetTokenMarshaller
                .marshall(subsetToken);
        if (ITRACER.isDebugEnabled())
        {
            Object[] values = {key, name, subsetTokenAsString};
            ITRACER.debug("key is : {0}, name is {1}, subsetTokenAsString is : {2}", values);
        }
        ExternalDataSubsetIdentifier result = new ExternalDataSubsetIdentifier(key, name, subsetTokenAsString);
        ITRACER.debugExit(methodName);
        return result;
    }

    /**
     * Store the specified serialized subset data in a file.
     * 
     * @param loadedData the serialized subset data
     *
     * @param subsetRequest
     *            The {@link SubsetRequest}.
     * @return The path to file containing the serialized subset
     */
    private String storeSubset(final byte[] loadedData,
            final SubsetRequest subsetRequest)
    {
        final String methodName = "storeSubset";
        ITRACER.debugEntry(methodName);
        final String dataSourceId = httpServletRequest.getContextPath()
                .substring(1);
        final String apolloDataDir = System.getProperty("APOLLO_DATA");
        final String itemsXmlPath = apolloDataDir
                + "/"
                + dataSourceId
                + "_"
                + subsetRequest.getSearchString().replace(' ', '_')
                        .replace("|", "_") + "_results.xml";
        final File outputFile = new File(itemsXmlPath);

        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(outputFile);
            fileOutputStream.write(loadedData);
        }
        catch (IOException ex)
        {
            if (ITRACER.isErrorEnabled())
            {
                Object[] values = {itemsXmlPath};
                ITRACER.error("Error when trying to write to file {0}", values);
                ITRACER.error(ex);
            }
            throw new WrappedCheckedException(ex);
        }
        finally
        {
            if (fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                }
                catch (IOException ex)
                {
                    if (ITRACER.isErrorEnabled())
                    {
                        Object[] values = {itemsXmlPath};
                        ITRACER.error("Error when trying to close file {0}", values);
                        ITRACER.error(ex);
                    }
                    throw new WrappedCheckedException(ex);
                }
            }
        }
        ITRACER.debugExit(methodName);
        return itemsXmlPath;
    }

    /**
     * Get data from the domaintools web api using the requestType and
     * searchString passed in the subsetRequest parameter. Transform the data
     * into a QueryResult that matches the IAP schema. Serialize the QueryResult
     * into a byte array.
     * 
     * @param subsetRequest
     *            The {@link SubsetRequest}.
     * @return A serialized QueryResult in a byte array.
     */
    private byte[] getDataFromExternalDataProvider(
            final SubsetRequest subsetRequest)
    {
        final String methodName = "getDataFromExternalDataProvider";
        ITRACER.debugEntry(methodName);
        final String searchString = subsetRequest.getSearchString();
        try
        {
            IMethod method = DomainToolsLoader.METHODS
                    .get(subsetRequest.getRequestType());
            if (method == null)
            {
                if (ITRACER.isErrorEnabled())
                {
                    Object[] values = {subsetRequest.getRequestType()};
                    ITRACER.error("No method could be found for request type : {0}", values);
                }
                throw new RuntimeException("Unknown request type.");
            }
            else
            {
                String response = method.execute(searchString);
                ITRACER.debugExit(methodName);
                return response.getBytes("UTF-8");
            }

        }
        catch (UnsupportedEncodingException ex)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Could not convert the domaintools response into a byte encoding.");
                ITRACER.error(ex);
            }
            throw new RuntimeException("A problem occurred: "
                    + ex.getClass().getCanonicalName() + ": " + ex.getMessage());
        }
    }

    /**
     * Throws a {@link WebApplicationException} as a bad request.
     * 
     * @param message
     *            The message to add to the response.
     */
    private void throwWebApplicationExceptionAsBadRequest(final String message)
    {
        final String methodName = "throwWebApplicationExceptionAsBadRequest";
        ITRACER.debugEntry(methodName);
        throw new WebApplicationException(Response.status(Status.BAD_REQUEST)
                .entity(message).build());
    }

    /**
     * Throws a {@link WebApplicationException} as an internal server error.
     * 
     * @param ex
     *            The {@link Throwable} whose message is added to the response.
     */
    private void throwWebApplicationExceptionAsInternalServerError(
            final Throwable ex)
    {
        final String methodName = "throwWebApplicationExceptionAsInternalServerError";
        ITRACER.debugEntry(methodName);
        final String errorText = ex.getClass().getName() + ":"
                + ex.getMessage();
        throw new WebApplicationException(Response
                .status(Status.INTERNAL_SERVER_ERROR).entity(errorText).build());
    }
}

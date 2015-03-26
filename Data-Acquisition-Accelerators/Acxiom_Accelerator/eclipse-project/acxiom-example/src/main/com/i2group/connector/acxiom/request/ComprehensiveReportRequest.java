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
package com.i2group.connector.acxiom.request;

import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingProvider;

import com.acxiom.schemas.v201110.us.idod.comprehensivereport.AcxiomServiceException;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.ComprehensiveReport;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.ComprehensiveReportService;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.InputValidationFault;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.SystemUnavailableFault;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.TooManyRowsException;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.request.InputType;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.request.RequestType;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.response.ResponseType;
import com.i2group.connector.acxiom.AcxiomUtils;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * This class initiates a soap request to Acxiom which completes a ComprehensiveReport.
 */
public final class ComprehensiveReportRequest implements IExecute
{
    private static final ITracer ITRACER = TracingManager
            .getTracer(ComprehensiveReportRequest.class);
    private static final String AXCIOM_URL_ENDPOINT = "https://cert-services.acxiom.com:446/idod-comprehensivereport-v2.7";
    private final String searchString;
    private final String dppa;
    private final String glba;

    /**
     * Initialises ComprehensiveReportRequest with the values needed to complete
     * a search.
     * 
     * 
     * @param searchStringParam
     *            String that contains search terms for a ComprehensiveReport.
     *            The ID of the entity that a ComprehensiveReport will be made
     *            for.
     * @param dppaParam
     *            String representing the DPPA permission value that need to be
     *            updated so that further searches can be made.
     * @param glbaParam
     *            String representing the GLBA permission value that need to be
     *            updated so that further searches can be made.
     */
    public ComprehensiveReportRequest(final String searchStringParam,
            final String dppaParam, final String glbaParam)
    {
        final String methodName = "ComprehensiveReportRequest<init>";
        ITRACER.debugEntry(methodName);
        this.searchString = searchStringParam;
        this.dppa = dppaParam;
        this.glba = glbaParam;
        ITRACER.debugExit(methodName);
    }

    /**
     * Initiates a ComprehensiveReport using the searchString,
     * and returns the result from Acxiom in a StreamSource.
     * 
     * @return StreamSource containing the results from a ComprehensiveReport
     *         request.
     */
    @Override
    public StreamSource execute()
    {
        final String methodName = "execute";
        ITRACER.debugEntry(methodName);
        try
        {
            ComprehensiveReportService service = new ComprehensiveReportService();
            ComprehensiveReport client = service.getComprehensiveReportPort();

            BindingProvider bp = (BindingProvider) client;
            bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                    AcxiomUtils.getUser());
            bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
                    AcxiomUtils.getPass());
            // Add the location of the request
            bp.getRequestContext().put(
                    BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                    AXCIOM_URL_ENDPOINT);
            RequestType request = createCompRequest(searchString);
            ComprehensiveReportHeaderBuilder comprehensiveReportHeaderBuilder = new ComprehensiveReportHeaderBuilder(
                    dppa, glba);

            if (ITRACER.isInfoEnabled())
            {
                final Object[] values = { AcxiomUtils.getUser(),
                         AXCIOM_URL_ENDPOINT, dppa, glba };
                ITRACER.info("Request properties being used are : Username : {0}, Endpoint : {1}, DPPA Value : {2} , GLBA Value : {3}", values);
            }

            ResponseType response = client.processRequest(request,
                    comprehensiveReportHeaderBuilder.build());
            final StreamSource result = AcxiomUtils
                    .marshallResponse(
                            "com.acxiom.schemas.v201110.us.idod.comprehensivereport.response",
                            response);
            ITRACER.debugExit(methodName);
            return result;
        }
        catch (AcxiomServiceException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("An error occurred performing a Comprehensive Request. See DEBUG logs for more information.");
                ITRACER.error(e);
            }
            if (ITRACER.isDebugEnabled())
            {
                final Object[] values = { e.getFaultInfo()};
                ITRACER.debug(
                        "Comprehensive report request error, Fault information from Acxiom : {0}",
                        values);
            }
            throw new RuntimeException(e);
        }
        catch (InputValidationFault e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Input Validation Error while making Comprehensive report request.");
                ITRACER.error(e);
            }
            if (ITRACER.isDebugEnabled())
            {
                final Object[] values = { e.getFaultInfo() };
                ITRACER.debug(
                        "Acxiom inputValidation error,  Fault information from Acxiom : {0}",
                        values);
            }
            throw new RuntimeException(e);
        }
        catch (SystemUnavailableFault e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("The Acxiom service is unavailable.");
                ITRACER.error(e);
            }
            if (ITRACER.isDebugEnabled())
            {
                final Object[] values = { AXCIOM_URL_ENDPOINT,
                        AcxiomUtils.getUser(), e.getFaultInfo() };
                ITRACER.debug(
                        "Acxiom system unavailable,  Endpoint is currently : {0}, Username is currently : {1}, Fault information from Acxiom : {2}",
                        values);
            }
            throw new RuntimeException(e);
        }
        catch (TooManyRowsException e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Too Many rows requested.");
                ITRACER.error(e);
            }
            if (ITRACER.isDebugEnabled())
            {
                final Object[] values = { e.getFaultInfo() };
                ITRACER.debug("Too Many rows requested, Fault information from Acxiom : {0}",
                        values);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a Comprehensive Report RequestType object. Defines what
     * information will be returned from an Acxiom ComprehensiveReport search.
     * e.g. Relative information, vehicle, SSN.....
     * 
     * @param searchString
     *            String that contains search terms for a ComprehensiveReport.
     * @return RequestType RequestType containing the requestOptions for a
     *         ComprehensiveReport. This object defines what type of
     *         ComprehensiveReport request is performed.
     */
    private static RequestType createCompRequest(final String searchString)
    {
        final String methodName = "createCompRequest";
        ITRACER.debugEntry(methodName);
        InputType inputType = new InputType();
        inputType.setSearchId(searchString);
        inputType.setIndividualId(searchString);
        ComprehensiveReportRequestTypeBuilder comprehensiveReportRequestBuilder = new ComprehensiveReportRequestTypeBuilder(inputType);
        RequestType requestType = comprehensiveReportRequestBuilder.build();
        ITRACER.debugExit(methodName);
        return requestType;
    }

}

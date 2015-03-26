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

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingProvider;

import com.acxiom.schemas.v201110.us.idod.common.AddressType;
import com.acxiom.schemas.v201110.us.idod.common.NameType;
import com.acxiom.schemas.v201110.us.idod.common.PhoneType;
import com.acxiom.schemas.v201110.us.idod.findpeople.FindPeopleService;
import com.acxiom.schemas.v201110.us.idod.findpeople.FindPeopleService_Service;
import com.acxiom.schemas.v201110.us.idod.findpeople.InputValidationFault;
import com.acxiom.schemas.v201110.us.idod.findpeople.SystemUnavailableFault;
import com.acxiom.schemas.v201110.us.idod.findpeople.request.InputType;
import com.acxiom.schemas.v201110.us.idod.findpeople.request.RequestType;
import com.acxiom.schemas.v201110.us.idod.findpeople.response.ResponseType;
import com.i2group.connector.acxiom.AcxiomUtils;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * This class initiates a soap request to Acxiom which completes a FindPeople search.
 */
public final class FindPeopleRequest implements IExecute
{
    private static final ITracer ITRACER = TracingManager
            .getTracer(FindPeopleRequest.class);
    private static final String AXCIOM_URL_ENDPOINT = "https://cert-services.acxiom.com/idod-findpeople-v2.7";
    private static final String FIRST = "first name";
    private static final String LAST = "last name";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String ZIP = "zip";
    private static final String ADDRESS1 = "Address Line 1";
    private static final String ADDRESS2 = "address 2";
    private static final String SSN = "ssn";
    private static final String GLBA = "glba";
    private static final String DPPA = "dppa";
    private static final String PHONE = "phone";
    private static final String AREACODE = "areacode";
    private static String glbaValue, dppaValue;
    //Map containing search terms that are used to create the FindPeople request.
    private final Map<String, String> searchTermsMap = new HashMap<String, String>();

    /**
     * Initialises the searchTermsMap which is used to create the request.
     * Contains the search terms needed to create a request.
     * 
     * @param searchTermsMapParam
     *            Map containing the key value pairs consisting of the Acxiom
     *            search terms to be used in the FindPeople search.
     */
    public FindPeopleRequest(final Map<String, String> searchTermsMapParam)
    {
        final String methodName = "ComprehensiveReportRequestTypeBuilder<init>";
        ITRACER.debugEntry(methodName);
        this.searchTermsMap.putAll(searchTermsMapParam);
        ITRACER.debugExit(methodName);

    }

    /**
     * Initiates a FindPeopleSearch. Creates a request. Then returns the results from Acxiom in a
     * StreamSource.
     * 
     * @return StreamSource containing results from Acxiom FindPeopleSearch request.
     */
    @Override
    public StreamSource execute()
    {
        final String methodName = "execute";
        ITRACER.debugEntry(methodName);
        try
        {
            FindPeopleService_Service service = new FindPeopleService_Service();
            FindPeopleService client = service.getFindPeopleServicePort();
            BindingProvider bp = (BindingProvider) client;
            bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                    AcxiomUtils.getUser());
            bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
                    AcxiomUtils.getPass());
            bp.getRequestContext().put(
                    BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                    AXCIOM_URL_ENDPOINT);
            RequestType request = createRequest(searchTermsMap);
            FindPeopleHeaderBuilder findPeopleHeaderBuilder = new FindPeopleHeaderBuilder(
                    dppaValue, glbaValue);
            if (ITRACER.isInfoEnabled())
            {
                final Object[] values = { AcxiomUtils.getUser(),
                         AXCIOM_URL_ENDPOINT, dppaValue, glbaValue };
                ITRACER.info("Request properties being used are : Username : {0}, Endpoint : {1}, DPPA Value : {2} , GLBA Value : {3}", values);
            }
            ResponseType response = client.processRequest(request,
                    findPeopleHeaderBuilder.build());
            final StreamSource result = AcxiomUtils.marshallResponse(
                    "com.acxiom.schemas.v201110.us.idod.findpeople.response",
                    response);
            ITRACER.debugEntry(methodName);
            return result;
        }
        catch (InputValidationFault e)
        {
            if (ITRACER.isErrorEnabled())
            {
                ITRACER.error("Input Validation Error while making a Find People request.");
                ITRACER.error(e);
            }
            if (ITRACER.isDebugEnabled())
            {
                final Object[] values = { e.getFaultInfo() };
                ITRACER.debug(
                        "Acxiom inputValidation error,  Fault information from acxiom : {0}",
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
                        "Acxiom system unavailable,  Endpoint is currently : {0}, Username is currently : {1}, Fault information from Acxiom is : {2}",
                        values);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a FindPeople RequestType.
     * 
     * @param searchTermsMap
     *            Map<String, String> a map containing Acxiom search terms,
     *            where the key is the 'type' and the value is the 'types'
     *            value.
     * @return RequestType RequestType containing the requestOptions for a
     *         FindPeopleSearch. This object defines what type of
     *         FindPeopleSearch is performed.
     */
    private static RequestType createRequest(
            final Map<String, String> searchTermsMap)
    {
        final String methodName = "createRequest";
        ITRACER.debugEntry(methodName);
        InputType input = createInputType(searchTermsMap);
        FindPeopleRequestTypeBuilder findPeopleRequestTypeBuilder = new FindPeopleRequestTypeBuilder(input);
        RequestType request = findPeopleRequestTypeBuilder.build();
        ITRACER.debugExit(methodName);
        return request;

    }

    /**
     * Parse the searchTermsMap and extract the values, and set the corresponding
     * variables. Returns an InputType.
     * 
     * @param searchTermsMap
     *            Map<String, String> a map containing Acxiom search terms,
     *            where the key is the 'type' and the value is the 'types'
     *            value.
     * @return InputType InputType containing the SearchOptions used to create a
     *         FindPeopleSearch.
     */
    private static InputType createInputType(
            final Map<String, String> searchTermsMap)
    {
        final String methodName = "createInputType";
        ITRACER.debugEntry(methodName);
        InputType input = new InputType();
        input.setSearchId("Client Test One");
        boolean addName = false, addAddress = false, addPhone = false;
        NameType name = new NameType();
        PhoneType phone = new PhoneType();
        AddressType address = new AddressType();

        // Iterate over the map key/value pairs, set the corresponding search
        // attribute
        for (Map.Entry<String, String> entry : searchTermsMap.entrySet())
        {
            String type = entry.getKey(); // Get the type
            String value = entry.getValue(); // Get the value
            // Check does the type match one of the pre set search types.
            if (type.equalsIgnoreCase(LAST))
            {
                name.setLast(value);
                addName = true;
            }
            else if (type.equalsIgnoreCase(FIRST))
            {
                name.setFirst(value);
                addName = true;
            }
            else if (type.equalsIgnoreCase(ZIP))
            {
                address.setZip(value);
                addAddress = true;
            }
            else if (type.equalsIgnoreCase(STATE))
            {
                address.setState(value);
                addAddress = true;
            }
            else if (type.equalsIgnoreCase(CITY))
            {
                address.setCity(value);
                addAddress = true;
            }
            else if (type.equalsIgnoreCase(ADDRESS1))
            {
                address.getAddressLine().add(value);
                addAddress = true;
            }
            else if (type.equalsIgnoreCase(ADDRESS2))
            {
                address.getAddressLine().add(value);
                addAddress = true;
            }
            else if (type.equalsIgnoreCase(SSN)
                    || type.equalsIgnoreCase("first 5#")
                    || type.equalsIgnoreCase("Full SSN #"))
            {
                input.setSSN(value);
            }
            else if (type.equalsIgnoreCase(AREACODE))
            {
                phone.setAreaCode(value);
                addPhone = true;
            }
            else if (type.equalsIgnoreCase(PHONE))
            {
                phone.setPhoneNumber(value);
                addPhone = true;
            }
            else if (type.equalsIgnoreCase(DPPA))
            {
                dppaValue = value;
            }
            else if (type.equalsIgnoreCase(GLBA))
            {
                glbaValue = value;
            }
        }

        // set values on the input return
        if (addName)
        {
            input.setName(name);
        }
        if (addAddress)
        {
            input.setAddress(address);
        }
        if (addPhone)
        {
            input.setPhone(phone);
        }
        ITRACER.debugEntry(methodName);
        return input;
    }
}

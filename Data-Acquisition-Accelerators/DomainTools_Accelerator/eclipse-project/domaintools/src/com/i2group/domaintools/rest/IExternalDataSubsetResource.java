/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.i2group.domaintools.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Resource for Subset creation.
 */
public interface IExternalDataSubsetResource
{
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
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response createSubset(SubsetRequest subsetRequest);
}

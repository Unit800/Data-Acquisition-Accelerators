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

import com.acxiom.schemas.v201110.us.idod.common.IDODHeaderType;
import com.acxiom.schemas.v201110.us.idod.common.PermissibleUseAssertionType;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * The {@link FindPeopleHeaderBuilder}.
 */
public final class FindPeopleHeaderBuilder implements IHeaderBuilder<IDODHeaderType>
{
    private static final ITracer ITRACER = TracingManager
            .getTracer(FindPeopleHeaderBuilder.class);
    private final String dppaValue;
    private final String glbaValue;

    /**
     * Constructs a new FindPeopleHeaderBuilder.
     * 
     * @param dppa
     *            String representing the DPPA permission value to be used in
     *            the header.
     * @param glba
     *            String representing the GBLA permission value to be used in
     *            the header.
     */
    public FindPeopleHeaderBuilder(final String dppa, final String glba)
    {
        final String methodName = "FindPeopleHeaderBuilder<init>";
        ITRACER.debugEntry(methodName);
        dppaValue = dppa;
        glbaValue = glba;
        ITRACER.debugExit(methodName);
    }


    /**
     * Builds a IDODHeaderType.
     * 
     * @return IDODHeaderType header needed for a FindPeople request.
     */
    @Override
    public IDODHeaderType build()
    {
        final String methodName = "build";
        ITRACER.debugEntry(methodName);
        final IDODHeaderType header = new IDODHeaderType();
        header.setJobID("jobID");
        header.setSegmentationCode("segCode");
        header.setResellerTransactionID("transID");
        PermissibleUseAssertionType puaType = new PermissibleUseAssertionType();
        puaType.setDPPA(dppaValue);
        puaType.setGLBA(glbaValue);
        header.setPermissibleUseAssertion(puaType);
        ITRACER.debugExit(methodName);
        return header;
    }

}

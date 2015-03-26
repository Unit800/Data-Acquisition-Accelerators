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
import com.acxiom.schemas.v201110.us.idod.common.SubClientInfoType;
import com.i2group.connector.acxiom.AcxiomUtils;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * The {@link ComprehensiveReportHeaderBuilder}.
 */
public final class ComprehensiveReportHeaderBuilder implements IHeaderBuilder<IDODHeaderType>
{
    private static final ITracer ITRACER = TracingManager
            .getTracer(ComprehensiveReportHeaderBuilder.class);
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
    public ComprehensiveReportHeaderBuilder(final String dppa, final String glba)
    {
        final String methodName = "ComprehensiveReportHeaderBuilder<init>";
        ITRACER.debugEntry(methodName);
        dppaValue = dppa;
        glbaValue = glba;
        ITRACER.debugExit(methodName);
    }


    /**
     * Builds a IDODHeaderType.
     * 
     * @return IDODHeaderType header needed for a ComprehensiveReport request.
     */
    @Override
    public IDODHeaderType build()
    {
        final String methodName = "build";
        ITRACER.debugEntry(methodName);
        final IDODHeaderType header = new IDODHeaderType();
        header.setJobID("1");
        header.setSegmentationCode("1");
        header.setResellerTransactionID("transId");
        final SubClientInfoType subClient = new SubClientInfoType();
        subClient.setId(AcxiomUtils.getUser());
        subClient.setIP("127.0.0.1");
        subClient.setUserName(AcxiomUtils.getUser());
        header.setSubClientInfo(subClient);
        final PermissibleUseAssertionType puaType = new PermissibleUseAssertionType();
        puaType.setDPPA(dppaValue);
        puaType.setGLBA(glbaValue);
        header.setPermissibleUseAssertion(puaType);
        ITRACER.debugExit(methodName);
        return header;
    }

}

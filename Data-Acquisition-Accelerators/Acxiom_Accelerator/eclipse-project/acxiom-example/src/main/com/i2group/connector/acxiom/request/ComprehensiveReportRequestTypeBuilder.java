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

import com.acxiom.schemas.v201110.us.idod.comprehensivereport.request.InputType;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.request.InputsType;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.request.OptionsType;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.request.ProcessOptionsType;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.request.ReportOptionsType;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.request.RequestType;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;


/**
 * The {@link ComprehensiveReportRequestTypeBuilder}.
 */
public final class ComprehensiveReportRequestTypeBuilder implements IRequestTypeBuilder<RequestType>
{
    private static final ITracer ITRACER = TracingManager
            .getTracer(ComprehensiveReportRequestTypeBuilder.class);
    private final InputType inputType;

    /**
     * Constructs a new {@link ComprehensiveReportRequestTypeBuilder}.
     * 
     * @param input
     *            the {@link InputType}.
     */
    public ComprehensiveReportRequestTypeBuilder(final InputType input)
    {
        final String methodName = "ComprehensiveReportRequestTypeBuilder<init>";
        ITRACER.debugEntry(methodName);
        inputType = input;
        ITRACER.debugExit(methodName);
    }

    @Override
    public RequestType build()
    {
        final String methodName = "build";
        ITRACER.debugEntry(methodName);
        final RequestType request = new RequestType();
        request.setInputs(buildInputs(inputType));
        request.setOptions(buildOptionsType());
        ITRACER.debugExit(methodName);
        return request;
    }

    /**
     * Builds an {@link InputsType}.
     * 
     * @param input
     *            the {@link input}.
     * @return See above.
     */
    private static InputsType buildInputs(final InputType input)
    {
        final String methodName = "buildInputs";
        ITRACER.debugEntry(methodName);
        final InputsType inputs = new InputsType();
        inputs.getInput().add(input);
        ITRACER.debugExit(methodName);
        return inputs;
    }

    /**
     * Builds a {@link OptionsType}.
     * 
     * @return See above.
     */
    private static OptionsType buildOptionsType()
    {
        final String methodName = "buildOptionsType";
        ITRACER.debugEntry(methodName);
        final OptionsType options = new OptionsType();
        options.setProcessOptions(buildProcessOptionsType());
        options.setReportOptions(buildReportOptionsType());
        ITRACER.debugExit(methodName);
        return options;
    }

    /**
     * Builds a {@link ProcessOptionsType}.
     * 
     * @return See above.
     */
    private static ProcessOptionsType buildProcessOptionsType()
    {
        final String methodName = "buildProcessOptionsType";
        ITRACER.debugEntry(methodName);
        final ProcessOptionsType processOptions = new ProcessOptionsType();
        processOptions.setPreferredOnly(false);
        processOptions.setTestOnly(false);
        ITRACER.debugExit(methodName);
        return processOptions;
    }
    /**
     * Builds an {@link ReportOptionsType}.
     * 
     * @return See above.
     */
    private static ReportOptionsType buildReportOptionsType()
    {
        final String methodName = "buildReportOptionsType";
        ITRACER.debugEntry(methodName);
        final ReportOptionsType reportOptionsType = new ReportOptionsType();
        reportOptionsType.setIncludeNameAlias(true);
        reportOptionsType.setIncludeAircraft(false);
        reportOptionsType.setIncludeAssociate(true);
        reportOptionsType.setIncludeAssociativeDemographic(true);
        reportOptionsType.setIncludeConcealedWeapon(false);
        reportOptionsType.setIncludeContact(false);
        reportOptionsType.setIncludeDriversLicense(false);
        reportOptionsType.setIncludeDrugEnforcementAgencyRegistration(false);
        reportOptionsType.setIncludeHigherEducation(false);
        reportOptionsType.setIncludeHuntingAndFishing(false);
        reportOptionsType.setIncludeInternetDomain(false);
        reportOptionsType.setIncludeNameAddress(true);
        reportOptionsType.setIncludeNeighbors(false);
        reportOptionsType.setIncludePhone(true);
        reportOptionsType.setIncludePilots(false);
        reportOptionsType.setIncludePremiumBasedVehicle(false);
        reportOptionsType.setIncludeProfessionalLicense(false);
        reportOptionsType.setIncludeProperty(true);
        reportOptionsType.setIncludeRecreationalVehicle(false);
        reportOptionsType.setIncludeRelative(true);
        reportOptionsType.setIncludeSSN(true);
        reportOptionsType.setIncludeVehicle(false);
        reportOptionsType.setIncludeVessel(false);
        reportOptionsType.setIncludeVoter(false);
        ITRACER.debugExit(methodName);
        return reportOptionsType;
    }
}

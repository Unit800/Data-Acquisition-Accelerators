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

import com.acxiom.schemas.v201110.us.idod.findpeople.request.AliasOptionsType;
import com.acxiom.schemas.v201110.us.idod.findpeople.request.AnalysisOptionsType;
import com.acxiom.schemas.v201110.us.idod.findpeople.request.DataRequestOptionsType;
import com.acxiom.schemas.v201110.us.idod.findpeople.request.InputListType;
import com.acxiom.schemas.v201110.us.idod.findpeople.request.InputType;
import com.acxiom.schemas.v201110.us.idod.findpeople.request.NameAddressOptionsType;
import com.acxiom.schemas.v201110.us.idod.findpeople.request.PhoneOptionsType;
import com.acxiom.schemas.v201110.us.idod.findpeople.request.ProcessOptionsType;
import com.acxiom.schemas.v201110.us.idod.findpeople.request.RequestType;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * The {@link FindPeopleRequestTypeBuilder}.
 */
public final class FindPeopleRequestTypeBuilder implements IRequestTypeBuilder<RequestType>
{
    private static final ITracer ITRACER = TracingManager
            .getTracer(FindPeopleRequestTypeBuilder.class);
    private static final int RETURN_COUNT = 25;

    private final InputType inputType;

    /**
     * Constructs a new {@link FindPeopleRequestTypeBuilder}.
     * 
     * @param input
     *            the {@link InputType}.
     */
    public FindPeopleRequestTypeBuilder(final InputType input)
    {
        final String methodName = "FindPeopleRequestTypeBuilder<init>";
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

        request.setInputs(buildInputListType(inputType));
        request.setProcessOptions(buildProcessOptionsType());
        ITRACER.debugExit(methodName);
        return request;
    }

    /**
     * Builds an {@link InputListType}.
     * 
     * @param input
     *            the {@link input}.
     * @return See above.
     */
    private static InputListType buildInputListType(final InputType input)
    {
        final String methodName = "buildInputListType";
        ITRACER.debugEntry(methodName);
        final InputListType inputList = new InputListType();
        inputList.getInput().add(input);
        ITRACER.debugExit(methodName);
        return inputList;
    }

    /**
     * Builds an {@link AnalysisOptionsType}.
     * 
     * @return See above.
     */
    private static AnalysisOptionsType buildAnalysisOptionsType()
    {
        final String methodName = "buildAnalysisOptionsType";
        ITRACER.debugEntry(methodName);
        final AnalysisOptionsType analysisOptions = new AnalysisOptionsType();
        analysisOptions.setAnalyzeDriversLicense(false);
        analysisOptions.setAnalyzePhone(false);
        analysisOptions.setAnalyzeSsn(false);
        analysisOptions.setAnalyzeZip(false);
        ITRACER.debugExit(methodName);
        return analysisOptions;
    }

    /**
     * Builds an {@link AliasOptionsType}.
     * 
     * @return See above.
     */
    private static AliasOptionsType buildAliasOptionsType()
    {
        final String methodName = "buildAliasOptionsType";
        ITRACER.debugEntry(methodName);
        final AliasOptionsType aliasOptions = new AliasOptionsType();
        aliasOptions.setIncludeAliases(false);
        aliasOptions.setReturnBestAliasOnly(false);
        ITRACER.debugExit(methodName);
        return aliasOptions;
    }

    /**
     * Builds a {@link NameAddressOptionsType}.
     * 
     * @return See above.
     */
    private static NameAddressOptionsType buildNameAddressOptionsType()
    {
        final String methodName = "buildNameAddressOptionsType";
        ITRACER.debugEntry(methodName);
        final NameAddressOptionsType nameAddressOptions = new NameAddressOptionsType();
        nameAddressOptions.setMaxAddressReturnCount(RETURN_COUNT);
        nameAddressOptions.setReturnMostRecentAddressOnly(false);
        ITRACER.debugExit(methodName);
        return nameAddressOptions;
    }

    /**
     * Builds a {@link PhoneOptionsType}.
     * 
     * @return See above.
     */
    private static PhoneOptionsType buildPhoneOptionsType()
    {
        final String methodName = "buildPhoneOptionsType";
        ITRACER.debugEntry(methodName);
        final PhoneOptionsType phoneOptions = new PhoneOptionsType();
        phoneOptions.setIncludePhoneList(false);
        phoneOptions.setReturnMostRecentPhoneOnly(false);
        ITRACER.debugExit(methodName);
        return phoneOptions;
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
        processOptions.setDataRequestOptions(buildDataRequestOptionsType());
        processOptions.setAnalysisOptions(buildAnalysisOptionsType());
        processOptions.setReturnCount(RETURN_COUNT);
        ITRACER.debugExit(methodName);
        return processOptions;
    }

    /**
     * Builds an {@link AliasOptionsType}.
     * 
     * @return See above.
     */
    private static DataRequestOptionsType buildDataRequestOptionsType()
    {
        final String methodName = "buildDataRequestOptionsType";
        ITRACER.debugEntry(methodName);
        final DataRequestOptionsType dataRequestOptions = new DataRequestOptionsType();
        dataRequestOptions.setDeterminePreferredIndividual(false);
        dataRequestOptions.setEnableCBSA(false);
        dataRequestOptions.setIncludeExpandedDeceasedInformation(true);
        dataRequestOptions.setIncludeVerificationFlags(false);
        dataRequestOptions.setIncludeWatchListDetails(false);
        dataRequestOptions.setPerformWatchListCheck(false);
        dataRequestOptions.setPerformWatchListCheckOnInput(false);
        dataRequestOptions.setReturnBestSsnOnly(false);
        dataRequestOptions.setAliasOptions(buildAliasOptionsType());
        dataRequestOptions.setNameAddressOptions(buildNameAddressOptionsType());
        dataRequestOptions.setPhoneOptions(buildPhoneOptionsType());
        ITRACER.debugExit(methodName);
        return dataRequestOptions;
    }
}

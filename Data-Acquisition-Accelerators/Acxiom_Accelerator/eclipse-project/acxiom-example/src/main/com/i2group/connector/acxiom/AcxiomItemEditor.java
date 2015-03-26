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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.i2group.apollo.common.jaxb.GregorianCalendarProvider;
import com.i2group.apollo.common.jaxb.IGregorianCalendarProvider;
import com.i2group.apollo.externaldata.connector.IExternalDataItemEditor;
import com.i2group.apollo.externaldata.typeconversion.PropertyBag;
import com.i2group.apollo.model.data.transport.Card;
import com.i2group.apollo.model.data.transport.Item;
import com.i2group.apollo.model.data.transport.SourceInfo;
import com.i2group.apollo.model.schema.transport.CompoundPropertyValue;
import com.i2group.apollo.model.schema.transport.PropertyValue;
import com.i2group.tracing.ITracer;
import com.i2group.tracing.TracingManager;

/**
 * An external data item editor that adds time stamp information to the items
 * that it processes.
 */
public final class AcxiomItemEditor implements IExternalDataItemEditor
{
    private static final ITracer ITRACER = TracingManager
            .getTracer(AcxiomItemEditor.class);
    private static final String UTC = "UTC";
    private final IGregorianCalendarProvider mGregorianCalendarProvider;
    private final PropertyValue mNowTimestamp;

    /**
     * Constructs a new {@link ExampleItemEditor}.
     */
    public AcxiomItemEditor()
    {
        final String methodName = "AcxiomItemEditor<init>";
        ITRACER.debugEntry(methodName);
        mGregorianCalendarProvider = new GregorianCalendarProvider();
        mNowTimestamp = createTimestamp(Calendar.getInstance());
        ITRACER.debugExit(methodName);
    }

    /**
     * Edits the specified {@link Item} to add time stamps from the specified
     * {@link PropertyBag}s to the {@link Item} and its {@link Card}s.
     * 
     * @param item
     *            The {@link Item} to be edited.
     * @param propertyBags
     *            {@link PropertyBag}s that contain the created and modified
     *            time stamps.
     */
    @Override
    public void edit(final Item item, final Collection<PropertyBag> propertyBags)
    {
        final String methodName = "edit";
        ITRACER.debugEntry(methodName);

        final List<Calendar> timeStamps = getTimeStampsFromPropertyBag(propertyBags);
        // Give this timeStamps a value so that they will be set if the
        // propertybags don't contain dates.
        CompoundPropertyValue createdTimestamp = (CompoundPropertyValue) mNowTimestamp;
        CompoundPropertyValue modifiedTimestamp = (CompoundPropertyValue) mNowTimestamp;

        if (timeStamps.size() >= 2)
        {
            createdTimestamp = createTimestamp(Calendar.getInstance());
            modifiedTimestamp = createTimestamp(Calendar.getInstance());
        }

        item.setCreatedTimestamp(createdTimestamp);
        item.setModifiedTimestamp(modifiedTimestamp);

        for (Card card : item.getCards())
        {
            final SourceInfo sourceInfo = card.getSourceInfo();
            sourceInfo.setReference("acxiom-example");
            sourceInfo.setDate(mNowTimestamp);

            card.setCreatedTimestamp(createdTimestamp);
            card.setModifiedTimestamp(modifiedTimestamp);
            addTimestampsToCardsSourceInfo(card, createdTimestamp,
                    modifiedTimestamp);
        }

        ITRACER.debugExit(methodName);
    }

    /**
     * Gets created and modified time stamps from the first of the specified
     * {@link PropertyBag}s.
     * 
     * @param propertyBags
     *            The {@link PropertyBag}s from which the time stamps are to be
     *            extracted.
     * @return {@link Calendar}s that represent the created and modified time
     *         stamps.
     */
    private List<Calendar> getTimeStampsFromPropertyBag(
            final Collection<PropertyBag> propertyBags)
    {
        final String methodName = "geTimeStampsFromPropertyBag";
        ITRACER.debugEntry(methodName);

        final List<Calendar> timeStamps = new ArrayList<Calendar>();

        if (propertyBags != null && !propertyBags.isEmpty())
        {
            final PropertyBag propertyBag = propertyBags.iterator().next();

            if (propertyBag.getDateTimes() != null
                    && !propertyBag.getDateTimes().isEmpty())
            {
                timeStamps.addAll(propertyBag.getDateTimes());
            }
        }

        ITRACER.debugExit(methodName);

        return timeStamps;
    }

    /**
     * Adds the specified created and modified time stamps to the source
     * information of the specified {@link Card}.
     * 
     * @param card
     *            The {@link Card} to which the time stamps will be added.
     * @param dateTimeRecordCreated
     *            A {@link CompoundPropertyValue} that contains the created
     *            time stamp.
     * @param dateTimeRecordModified
     *            A {@link CompoundPropertyValue} that contains the modified
     *            time stamp.
     */
    private void addTimestampsToCardsSourceInfo(final Card card,
            final CompoundPropertyValue dateTimeRecordCreated,
            final CompoundPropertyValue dateTimeRecordModified)
    {
        final String methodName = "addTimestampsToCardsSourceInfo";
        ITRACER.debugEntry(methodName);

        String text = "";

        if (dateTimeRecordCreated != null)
        {
            text = "Record Created:  "
                    + dateTimeRecordCreated.getElements().get(0) + "\n";
        }
        if (dateTimeRecordModified != null)
        {
            text += "Record Modified: "
                    + dateTimeRecordModified.getElements().get(0);
        }

        card.getSourceInfo().setDescription(text);

        ITRACER.debugExit(methodName);
    }

    /**
     * Converts a {@link Calendar} to a {@link CompoundPropertyValue} that
     * contains a time stamp.
     * 
     * @param calendar
     *            The {@link Calendar} that contains the time stamp information.
     * @return A {@link CompoundPropertyValue} that contains a time stamp.
     */
    private CompoundPropertyValue createTimestamp(final Calendar calendar)
    {
        final String methodName = "createTimestamp";
        ITRACER.debugEntry(methodName);

        final XMLGregorianCalendar timestamp = mGregorianCalendarProvider
                .createSpecifiedTimeGregorianCalendar(calendar
                        .getTimeInMillis());

        final String calendarAsXmlString = timestamp.toString();
        final List<String> elements = new ArrayList<String>();

        elements.add(calendarAsXmlString);
        elements.add(UTC);
        elements.add(Boolean.FALSE.toString());
        elements.add(calendarAsXmlString);

        final CompoundPropertyValue compoundPropertyValue = new CompoundPropertyValue(
                elements);

        ITRACER.debugExit(methodName);

        return compoundPropertyValue;
    }
}

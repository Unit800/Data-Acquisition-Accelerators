/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.i2group.domaintools.Methods;

/**
 * Represents a method. DomainToolsLoader stores methods in a Map which can
 * later be retrieved by name.
 */
public interface IMethod
{

    /**
     * Get the name of this method.
     * 
     * @return See above.
     */
    String getName();

    /**
     * Execute this method with the specified query string.
     * 
     * @param query
     *            The query string.
     * @return See above.
     */
    String execute(String query);
}

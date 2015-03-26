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

/**
 * IExecute interface, one function execute().
 */
public abstract interface IExecute
{
    /**
     * Execute method which subclasses use to execute a request.
     * 
     * @return StreamSource The data returned from an external service in a StreamSource.
     */
    StreamSource execute();
}

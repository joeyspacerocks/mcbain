//-----------------------------------------------------------------------
// Copyright Can Factory Limited, UK 
// http://www.canfactory.com - mailto:info@canfactory.com
//
// The copyright to the computer program(s) (source files, compiled 
// files and documentation) herein is the property of Can Factory 
// Limited, UK.
// The program(s) may be used and/or copied only with the written 
// permission of Can Factory Limited or in accordance with the terms 
// and conditions stipulated in the agreement/contract under which
// the program(s) have been supplied.
//-----------------------------------------------------------------------

package org.mcbain.request;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contains a list of validation errors.
 */

public class Errors {

	private Map<String, RequestError> errors;

	public Errors() {
		errors = new LinkedHashMap<String, RequestError>();
	}

	public void add(String field, String message) {
		errors.put(field, new RequestError(field, message));
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public Collection<RequestError> errors() {
		return errors.values();
	}
}

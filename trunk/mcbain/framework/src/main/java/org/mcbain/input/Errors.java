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

package org.mcbain.input;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contains a list of validation errors.
 */

public class Errors {

	private Map<String, InputError> errors;

	public Errors() {
		errors = new LinkedHashMap<String, InputError>();
	}

	public void add(String field, String message) {
		errors.put(field, new InputError(field, message));
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public Collection<InputError> errors() {
		return errors.values();
	}
}

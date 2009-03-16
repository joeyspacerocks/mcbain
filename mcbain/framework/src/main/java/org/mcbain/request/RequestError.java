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

/**
 * Holds details of a validation error for a field value.
 */

public class RequestError {

	private String field;
	private String message;

	public RequestError(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String field() {
		return field;
	}

	public String message() {
		return message;
	}
}

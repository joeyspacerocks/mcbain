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

package org.mcbain.validation;

/**
 *
 */

public class ValidationResult {
    public static final ValidationResult OK = new ValidationResult();

    private boolean ok;
    private String failReason;

    public ValidationResult() {
    }

    public ValidationResult(String failReason) {
        this.failReason = failReason;
        ok = false;
    }

    @Override
    public String toString() {
        return ok ? "OK" : "FAIL: " + failReason;
    }
}

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

public class IntegerRangeValidator implements Validator<Integer> {
    private int min;
    private int max;

    public IntegerRangeValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public ValidationResult validate(Integer value) {
        if (value < min || value > max) {
            return new ValidationResult("Not in range");
        }

        return ValidationResult.OK;
    }
}

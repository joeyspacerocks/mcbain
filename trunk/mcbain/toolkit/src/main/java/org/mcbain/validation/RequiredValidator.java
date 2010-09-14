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

import static org.mcbain.validation.ValidationResult.fail;
import static org.mcbain.validation.ValidationResult.pass;

/**
 * Validator that checks that the value is non-null.
 */

public class RequiredValidator implements Validator {

    @Override
    public ValidationResult validate(String id, Object value) {
        if (value == null) {
            return fail(id, "Required value is missing");
        } else {
            return pass(id);
        }
    }
}

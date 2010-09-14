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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Unit tests for the required string value validator.
 */

@Test
public class RequiredStringValidatorTest {
    private RequiredStringValidator validator;

    @BeforeClass
    public void setup() {
        validator = new RequiredStringValidator();
    }

    public void shouldPassIfValueIsNotNullAndNotEmpty() {
        assertTrue(validator.validate("id", "value").passed());
    }

    public void shouldFailIfValueIsNull() {
        assertTrue(validator.validate("id", null).failed());
    }

    public void shouldFailIfValueIsEmptyString() {
        assertTrue(validator.validate("id", "").failed());
    }
}
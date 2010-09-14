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
 * Unit tests for the integer range validator.
 */

@Test
public class IntegerRangeValidatorTest {
    private IntegerRangeValidator validator;

    @BeforeClass
    public void setup() {
        validator = new IntegerRangeValidator(2, 4);
    }

    public void shouldFailIfValueIsLessThanMinumum() {
        assertTrue(validator.validate("id", 1).failed());
    }

    public void shouldPassIfValueIsEqualToMinumum() {
        assertTrue(validator.validate("id", 2).passed());
    }

    public void shouldFailIfValueIsGreaterThanMaximum() {
        assertTrue(validator.validate("id", 5).failed());
    }

    public void shouldFailIfValueIsEqualToMaximum() {
        assertTrue(validator.validate("id", 4).failed());
    }

    public void shouldPassIfValueIsInsideRange() {
        assertTrue(validator.validate("id", 3).passed());
    }
}

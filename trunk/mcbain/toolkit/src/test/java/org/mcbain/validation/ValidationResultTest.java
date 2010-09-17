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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit tests for the validation result container.
 */

@Test
public class ValidationResultTest {
    private ValidationResult result;

    @BeforeMethod
    public void setup() {
        result = new ValidationResult("result", "value");
    }

    public void shouldReturnNoneIfNoNestedResultForId() {
        assertEquals(result.nestedResult("child"), ValidationResult.NONE);
    }

    public void shouldStoreNestedResultById() {
        ValidationResult child = new ValidationResult("child", "value");
        result.addResult(child);

        assertEquals(result.nestedResult("child"), child);
    }

    public void shouldStorePassedState() {
        assertTrue(result.passed());
        assertFalse(result.failed());
    }

    public void shouldStoreFailedState() {
        ValidationResult result = new ValidationResult("result", "value", "failed");
        assertTrue(result.failed());
        assertFalse(result.passed());
    }

    public void shouldIndicatePassedIfAllNestedResultPass() {
        result.addResult(new ValidationResult("child", "value"));
        assertTrue(result.passed());
    }

    public void shouldIndicateFailedIfAnyNestedResultFails() {
        result.addResult(new ValidationResult("child", "value", "failed"));
        assertTrue(result.failed());
    }
}

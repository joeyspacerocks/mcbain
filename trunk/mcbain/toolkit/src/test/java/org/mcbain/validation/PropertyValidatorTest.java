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

import org.mcbain.binding.PropertyAccessor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Unit tests for the property validator.
 */

@Test
public class PropertyValidatorTest {
    private PropertyAccessor testValue;
    private PropertyValidator validator;

    @BeforeMethod
    public void setup() {
        testValue = new PropertyAccessor() {
            public Object get(String property) { return "field".equals(property) ? true : null; }
            public void set(String property, Object value) {}
        };
        validator = new PropertyValidator();
    }

    public void shouldAlwaysPassIfNoRulesDefined() {
        assertTrue(validator.validate("id", testValue).passed());
    }

    public void shouldPassIfAllRulesPass() {
        validator.addRule("field", new RequiredValidator());
        
        assertTrue(validator.validate("id", testValue).passed());
    }

    public void shouldFailIfARuleFails() {
        validator.addRule("field2", new RequiredValidator());

        assertTrue(validator.validate("id", testValue).failed());
    }

    public void shouldFailIfOneRuleFailsAndAnotherPasses() {
        validator.addRule("field", new RequiredValidator());
        validator.addRule("field2", new RequiredValidator());

        assertTrue(validator.validate("id", testValue).failed());
    }
}

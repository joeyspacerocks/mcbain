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

import java.util.ArrayList;
import java.util.List;

/**
 * A validator that applies nested validators to the properties of the value.
 */

public class PropertyValidator implements Validator<PropertyAccessor> {

    private final List<Rule> rules = new ArrayList<Rule>();

    @Override
    public ValidationResult validate(String id, PropertyAccessor value) {
        ValidationResult result = new ValidationResult(id, value);

        for (Rule rule : rules) {
            result.addResult(rule.validate(value));
        }

        return result;
    }

    public void addPropertyRule(String property, Validator validator) {
        rules.add(new Rule(property, validator));
    }
    
    private class Rule {
        final String property;
        final Validator validator;

        ValidationResult validate(PropertyAccessor value) {
            Object propertyValue = value.get(property);
            return (validator.validate(property, propertyValue));
        }

        private Rule(String property, Validator validator) {
            this.property = property;
            this.validator = validator;
        }
    }
}

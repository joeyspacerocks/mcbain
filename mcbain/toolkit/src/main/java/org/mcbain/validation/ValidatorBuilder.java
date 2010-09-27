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
 * Builder providing a simple DSL for constructing property validators.
 */

public class ValidatorBuilder {
    private PropertyValidator validator = new PropertyValidator();
    private String currentProperty;

    /**
     * Declares the property that subsequent calls will apply to. This property
     * will be used until another call to this method.
     *
     * @param   property        Property name
     * @return  Builder instance for method chaining
     */

    public ValidatorBuilder check(String property) {
        this.currentProperty = property;
        return this;
    }

    /**
     * Adds a RequiredValidator rule to the current property's rules.
     *
     * @return  Builder instance for method chaining
     */

    public ValidatorBuilder isNotNull() {
        addRule(new RequiredValidator());
        return this;
    }

    /**
     * Adds a RequiredStringValidator rule to the current property's rules.
     *
     * @return  Builder instance for method chaining
     */

    public ValidatorBuilder isNotEmpty() {
        addRule(new RequiredStringValidator());
        return this;
    }

    /**
     * Returns the built property validator.
     *
     * @return  Property validator
     */

    public PropertyValidator build() {
        return validator;
    }

    /**
     * Adds a rule (validator) for the current property.
     *
     * @param   propertyValidator   Validator to add
     * @throws  IllegalStateException thrown if no current property specified
     */

    private void addRule(Validator propertyValidator) {
        if (currentProperty == null) {
            throw new IllegalStateException("Cannot add rule when no property has been specified");
        }

        validator.addPropertyRule(currentProperty, propertyValidator);
    }
}

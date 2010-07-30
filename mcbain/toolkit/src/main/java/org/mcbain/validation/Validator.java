package org.mcbain.validation;

/**
 *
 */

public interface Validator<T> {

    public ValidationResult validate(T value);
}

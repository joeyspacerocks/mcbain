/*
 * Copyright 2010 Joe Trewin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mcbain.validation;

/**
 * Validator that asserts that an integer lies between a minumum (inclusive)
 * and maximum value (exclusive).
 */

public class IntegerRangeValidator implements Validator<Integer> {
    private int min;
    private int max;

    public IntegerRangeValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public ValidationResult validate(String id, Integer value) {
        if (value < min || value >= max) {
            return ValidationResult.fail(id, value, "The value does not lie inside the range %d-%d", min, max);
        }

        return ValidationResult.pass(id, value);
    }
}

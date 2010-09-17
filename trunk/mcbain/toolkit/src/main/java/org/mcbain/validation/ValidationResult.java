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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Holds the result of applying a Validator to a value.
 */

public class ValidationResult {
    public static final ValidationResult NONE = new ValidationResult("_NONE_", null);
    
    private String id;
    private Object value;
    private boolean pass;
    private String failReason;
    private Map<String, ValidationResult> nestedResults;

    /**
     * Creates a validation result representing a successful validation check.
     *
     * @param   id      Identifier for value source (e.g. field name)
     * @param   value   Value that has been validated
     * @return  Validation result
     */

    public static ValidationResult pass(String id, Object value) {
        return new ValidationResult(id, value);
    }

    /**
     * Creates a validation result representing a failed validation check. Optional
     * parameters can be passed that are interpolated into the message using
     * String.format.
     *
     * @param   id                  Identifier for value source (e.g. field name)
     * @param   value               Value that has failed validation
     * @param   message             Failure message
     * @param   messageParameters   Additional parameters used to build the message
     * @return  Validation result
     */

    public static ValidationResult fail(String id, Object value, String message, Object... messageParameters) {
        return new ValidationResult(id, value, String.format(message, messageParameters));
    }

    public ValidationResult(String id, Object value) {
        this.id = id;
        this.value = value;
        pass = true;
    }

    public ValidationResult(String id, Object value, String failReason) {
        this.id = id;
        this.value = value;
        this.failReason = failReason;
        pass = false;
    }

    public String id() {
        return id;
    }

    public Object value() {
        return value;
    }

    public boolean failed() {
        return !pass;
    }

    public boolean passed() {
        return pass;
    }

    public void addResult(ValidationResult result) {
        nestedResults().put(result.id(), result);
        pass = !result.failed() && pass;
    }

    /**
     * Gets a nested result by id. If none exists for that id,
     * ValidationResult.NONE is returned.
     *
     * @param   id      Id of nested result
     * @return  Nested result, or ValidationResult.NONE
     */
    
    public ValidationResult nestedResult(String id) {
        ValidationResult result = (nestedResults == null ? null : nestedResults().get(id));
        return result == null ? NONE : result;
    }

    public String failReason() {
        return failReason;
    }

    @Override
    public String toString() {
        return pass ? "PASS" : "FAIL: " + failReason;
    }
    
    private Map<String,ValidationResult> nestedResults() {
        if (nestedResults == null) {
            nestedResults = new LinkedHashMap<String, ValidationResult>();
        }
        return nestedResults;
    }
}

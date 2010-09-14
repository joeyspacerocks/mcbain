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

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the result of applying a Validator to a value.
 */

public class ValidationResult {
    private String id;
    private boolean pass;
    private String failReason;
    private List<ValidationResult> nestedResults;

    /**
     * Creates a validation result representing a successful validation check.
     *
     * @param   id      Identifier for value source (e.g. field name)
     * @return  Validation result
     */

    public static ValidationResult pass(String id) {
        return new ValidationResult(id);
    }

    /**
     * Creates a validation result representing a failed validation check. Optional
     * parameters can be passed that are interpolated into the message using
     * String.format.
     *
     * @param   id                  Identifier for value source (e.g. field name)
     * @param   message             Failure message
     * @param   messageParameters   Additional parameters used to build the message
     * @return  Validation result
     */

    public static ValidationResult fail(String id, String message, Object... messageParameters) {
        return new ValidationResult(id, String.format(message, messageParameters));
    }

    public ValidationResult(String id) {
        this.id = id;
        pass = true;
    }

    public ValidationResult(String id, String failReason) {
        this.id = id;
        this.failReason = failReason;
        pass = false;
    }

    public boolean failed() {
        return !pass;
    }

    public boolean passed() {
        return pass;
    }

    public void addResult(ValidationResult result) {
        nestedResults().add(result);
        pass = !result.failed() && pass;
    }

    @Override
    public String toString() {
        return pass ? "PASS" : "FAIL: " + failReason;
    }
    
    private List<ValidationResult> nestedResults() {
        if (nestedResults == null) {
            nestedResults = new ArrayList<ValidationResult>();
        }
        return nestedResults;
    }
}

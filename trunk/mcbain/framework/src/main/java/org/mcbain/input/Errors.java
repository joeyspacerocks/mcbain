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

package org.mcbain.input;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contains a list of validation errors.
 */

public class Errors {

	private Map<String, InputError> errors;

	public Errors() {
		errors = new LinkedHashMap<String, InputError>();
	}

	public void add(String field, String message) {
		errors.put(field, new InputError(field, message));
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public Collection<InputError> errors() {
		return errors.values();
	}
}

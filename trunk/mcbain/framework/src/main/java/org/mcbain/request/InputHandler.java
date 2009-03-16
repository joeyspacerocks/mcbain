// Copyright 2009 Joe Trewin
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.mcbain.request;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Input handlers parse, validate and process request data.
 */

public class InputHandler {

	private Map<String, Field> fields;
	private Map<String, String> values;
	private Errors errors;
	private Request request;

	public InputHandler(Request request) {
		fields = new LinkedHashMap<String, Field>();
		values = new HashMap<String, String>();
		errors = new Errors();
		this.request = request;
	}

	public void addField(String name, Validator validator) {
		fields.put(name, new Field(name, validator));
	}

	public Errors errors() {
		return errors;
	}

	private boolean validate() {
		for (Field field : fields.values()) {
			String value = value(field.name());
			field.validate(value, errors);
		}

		return !errors.hasErrors();
	}

	public boolean ok() {
		return validate();
	}

	public String value(String field) {
		return request.parameter(field); //values.get(field);
	}

	private class Field {
		private String name;
		private Validator validator;

		private boolean validate(String value, Errors errors) {
			return validator.validates(name, value, errors);
		}

		private String name() {
			return name;
		}

		private Field(String name, Validator validator) {
			this.name = name;
			this.validator = validator;
		}
	}
}

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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Input handlers parse, validate and process request data.
 */

public class InputHandler {

	private Map<String, Field> fields;

	public InputHandler() {
		this.fields = new LinkedHashMap<String, Field>();
	}

	public void addField(String name, Validator validator) {
		fields.put(name, new Field(name, validator));
	}

	private class Field {
		private String name;
		private Validator validator;

		private Field(String name, Validator validator) {
			this.name = name;
			this.validator = validator;
		}
	}
}

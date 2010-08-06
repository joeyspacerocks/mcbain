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

/**
 * Holds details of a validation error for a field value.
 */

public class InputError {

	private String field;
	private String message;

	public InputError(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String field() {
		return field;
	}

	public String message() {
		return message;
	}
}

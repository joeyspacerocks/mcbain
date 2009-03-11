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

package org.mcbain.template;

/**
 * Exception wrapper providing additional context to exceptions thrown during
 * the rendering of a template.
 */

public class TemplateContextException extends RuntimeException {

	private Throwable cause;
	private int line;

	public TemplateContextException(Throwable cause, int line) {
		this.cause = cause;
		this.line = line;
	}

	@Override
	public String getMessage() {
		return "Exception occurred on template line " + line;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}
}

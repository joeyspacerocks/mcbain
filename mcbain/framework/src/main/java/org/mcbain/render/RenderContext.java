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

package org.mcbain.render;

import org.mcbain.request.Request;
import org.mcbain.template.Template;
import org.mcbain.template.TemplateFactory;

/**
 * A render context provides access to resources used when rendering, such as
 * templates, current request, link builders, curent component tree, etc.
 */

public class RenderContext {
	private TemplateFactory templates;
	private Request request;

	public RenderContext(Request request, TemplateFactory templates) {
		this.templates = templates;
		this.request = request;
	}

	public Template template(String name) {
		return templates.instance(name);
	}

	public String link(String path) {
		return path;
	}

	public Request request() {
		return request;
	}
}

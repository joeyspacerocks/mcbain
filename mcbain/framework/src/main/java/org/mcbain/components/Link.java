// Copyright 2007 Joe Trewin
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

package org.mcbain.components;

import org.mcbain.template.ElementAware;
import org.mcbain.render.Renderer;
import org.mcbain.template.Container;
import org.mcbain.render.Writer;
import org.mcbain.request.Request;
import org.mcbain.template.Element;

/**
 * Link component.
 */

public class Link implements Renderer, ElementAware, Container {

	private Element element;
	private String path;
	private String value;
	private Renderer content;

	public Link() {
	}

	public Link(String path) {
		uri(path);
	}

	public void uri(String path) {
		this.path = path;
	}

	public void value(String value) {
		this.value = value;
	}

	public void element(Element element) {
		this.element = element;
	}

	public void contents(Renderer content) {
		this.content = content;
	}

	public void render(Request request, Writer writer) {
		element.attribute("href", request.context().link(path));

		writer.tag("a").attributes(element);

		if (value != null) {
			writer.body(value);
		} else {
			content.render(request, writer);
			writer.close();
		}
	}
}

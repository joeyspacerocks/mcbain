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

import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.template.Element;


/**
 * Input field component.
 */

public class Input implements Renderer {

	private Object value;
	private boolean valueSet;

	public Input() {
	}

	public Input(Object value) {
		value(value);
	}

	public Input value(Object value) {
		this.value = value;
		valueSet = true;
		return this;
	}


	// FIXME: unique id/name when in loop ...

	public void render(RenderContext context, Writer writer) {
        Element element = context.element();
        
		Object renderValue = valueSet ? value : context.request().parameter(element.id());

		if (element.tag().equals("textarea")) {
			writer
				.tag("textarea")
				.attribute("name", element.id())
				.attribute("id", element.id())
				.attributes(element)
				.print(renderValue == null ? "" : renderValue.toString(), false)
				.close();

		} else {
			element.attribute("displayAs", renderValue);

			writer
				.emptyTag("input")
				.attribute("name", element.id())
				.attribute("id", element.id())
				.attributes(element);
		}
	}
}

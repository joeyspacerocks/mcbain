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
import org.mcbain.request.InputHandler;
import org.mcbain.template.Element;

/**
 * Component that renders a form.
 */

public class Form implements Renderer {

	private String path;
	private InputHandler in;

	public Form(String path, InputHandler in) {
		this.path = path;
		this.in = in;
	}


	public Form bind(Object... components) {
		return this;
	}

	public void render(RenderContext context, Writer writer) {
		Element element = context.element();

		writer.tag(element.tag());

		element.attribute("action", context.link(path));
		element.attribute("method", "POST");

		writer.attributes(element);
		context.contents().render(context, writer);

		writer.close();
	}
}

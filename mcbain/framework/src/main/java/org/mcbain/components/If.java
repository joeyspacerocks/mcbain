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

package org.mcbain.components;


import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.template.Element;

/**
 * Component that conditionally renders its content.
 *
 * @author Joe Trewin
 * @version $Revision$
 */

public class If implements Renderer {

	private boolean condition;

	public If() {
	}


	/**
	 * Constructs a new component with a specified condition.
	 *
	 * @param condition True to render the content, false not to
	 */

	public If(boolean condition) {
		this.condition = condition;
	}


	/**
	 * Tests to see if the content should be rendererd.
	 *
	 * @return True to render the content, false not to
	 */

	public boolean condition() {
		return condition;
	}


	public void render(RenderContext context, Writer writer) {
        Element element = context.element();

		if (condition) {
			if (element != null)
				writer.tag(element.tag());

			context.contents().render(context, writer);

			if (element != null)
				writer.close();
		}
	}
}

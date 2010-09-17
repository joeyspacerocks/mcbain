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
import org.mcbain.validation.ValidationResult;


/**
 * Input field component.
 */

public class Input implements Renderer {

	private ValidationResult result = ValidationResult.NONE;

    public Input value(ValidationResult result) {
        this.result = result;
        return this;
    }

	// FIXME: unique id/name when in loop ...

	public void render(RenderContext context, Writer writer) {
		Element element = context.element();

        Object value = result.value();
        if (result.failed()) {
            element.styleClass("error");
        }

		if (element.tag().equals("textarea")) {
			writer
				.tag("textarea")
				.attribute("name", element.id())
				.attribute("id", element.id())
				.attributes(element)
				.print(value == null ? "" : value.toString(), false)
				.close();

		} else {
			element.attribute("value", value);

			writer
				.emptyTag("input")
				.attribute("name", element.id())
				.attribute("id", element.id())
				.attributes(element);
		}

        if (result.failed()) {
            writer.tag("span").attribute("class", "error-message").print(result.failReason(), false).close();
        }
	}
}

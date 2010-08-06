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

package org.mcbain.template;

import org.mcbain.render.RenderContext;
import org.mcbain.render.Writer;

/**
 * Template part used to store and render the portions of text that lie
 * between component specifications in a template.
 */

public class TemplateText implements TemplatePart {

	private String text;


	public TemplateText(String text) {
		this.text = text;
	}


	public void render(RenderContext context, Writer writer, Template templateInstance) {
		writer.print(text, true);
	}


	public String text() {
		return text;
	}


	@Override
	public String toString() {
		return text;
	}
}

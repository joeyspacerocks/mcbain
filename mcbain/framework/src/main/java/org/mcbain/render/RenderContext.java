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

package org.mcbain.render;

import org.mcbain.template.Element;
import org.mcbain.util.ArrayStack;

/**
 * A render context provides access to resources used when rendering, such as
 * templates, current request, link builders, curent component tree, etc.
 */

public class RenderContext {
	private ArrayStack<Element> elements;
	private ArrayStack<Renderer> contents;

	public RenderContext() {
		elements = new ArrayStack<Element>();
		contents = new ArrayStack<Renderer>();
	}

	public Element element() {
		return elements.peek();
	}

	public Renderer contents() {
		return contents.peek();
	}

	public void pushTemplateContext(Element element, Renderer contents) {
		this.elements.push(element);
		this.contents.push(contents);
	}

	public void popTemplateContents() {
		elements.pop();
		contents.pop();
	}
}

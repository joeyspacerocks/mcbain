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
 * Component that renders its content multiple times.
 */

public class Loop<T> implements Renderer {

	private Iterable<T> source;
	private T currentValue;


	/**
	 * Constructs a new loop component.
     * @param source    Collection to iterate over
     */

	public Loop(Iterable<T> source) {
		this.source = source;
	}


	/**
	 * Sets the binding to the variable to use for the loop value.
	 *
	 * @param value Current loop value
	 */

	public void currentValue(T value) {
		currentValue = value;
	}


	/**
	 * Gets the current value in the loop.
	 *
	 * @return Current loop value
	 */

	public T value() {
		return currentValue;
	}

	public void render(RenderContext context, Writer writer) {
		if (source == null) return;

        Element element = context.element();

		if (element != null)
			writer.tag(element.tag());

		for (T value : source) {
			currentValue(value);
			context.contents().render(context, writer);
		}

		if (element != null)
			writer.close();
	}
}

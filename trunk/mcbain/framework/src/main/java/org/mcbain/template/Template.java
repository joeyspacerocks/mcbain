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
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.util.PairIterator;

import java.util.HashMap;
import java.util.Map;

/**
 * Instance of a template class for rendering a template with a specific
 * set of bound components/variables.
 */

public class Template implements Renderer {

	private final TemplateClass templateClass;
	private final Map<String, Renderer> components;


	/**
	 * **
	 * Constructs a template instance for binding components to the specified
	 * template class.
	 *
	 * @param templateClass Template class
	 */

	public Template(TemplateClass templateClass) {
		this.templateClass = templateClass;
		this.components = new HashMap<String, Renderer>();
	}


	/**
	 * **
	 * Binds a component collection from the supplied key/value pairs.
	 *
	 * @param content Array of objects, in String/Object pairs
	 */

	public Template bind(Object... content) {
		PairIterator<String, Object> it = new PairIterator<String, Object>(content);

		while (it.hasNext()) {
			String id = it.nextKey();
			Object component = it.nextValue();

			if (component instanceof Renderer) {
				bind(id, (Renderer) component);
			} else {
				bind(id, component);
			}
		}

		return this;
	}


	/**
	 * Adds a component to the collection.
	 *
	 * @param id		Component id in the context of it's container
	 * @param component Renderable component
	 * @return This object
	 */

	public Template bind(final String id, final Renderer component) {
		components.put(id, component);
		return this;
	}


	/**
	 * Adds an object to the collection. The object will be rendered using
	 * its toString method.
	 *
	 * @param id	Component id in the context of it's container
	 * @param value Object to be rendererd
	 * @return This object
	 */

	public Template bind(final String id, final Object value) {
		components.put(id, new Renderer() {
			public void render(RenderContext context, Writer writer) {
				if (value != null)
					writer.print(value.toString(), false);
			}
		});
		return this;
	}


	public boolean contains(final String id) {
		return components.containsKey(id);
	}

	public Renderer get(final String id) {
		return components.get(id);
	}

	public void render(RenderContext context, Writer writer) {
		templateClass.render(context, writer, this);
	}
}

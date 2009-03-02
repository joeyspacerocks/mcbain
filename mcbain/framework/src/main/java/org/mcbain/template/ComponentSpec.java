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

package org.mcbain.template;

import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;

import java.util.ArrayList;
import java.util.List;

/**
 * Specification of a component, usually defined through by template.
 */

class ComponentSpec implements TemplatePart {

	private final TemplateClass templateClass;
	private String id;
	private Element element;

	private ComponentSpec parent;
	private final List<TemplatePart> children;


	/**
	 * Constructs a new component specification.
	 *
	 * @param template Template the specification belongs to
	 */

	ComponentSpec(TemplateClass template) {
		this.templateClass = template;
		children = new ArrayList<TemplatePart>(0);
	}


	/**
	 * Constructs a new component specification.
	 *
	 * @param id		Component specification id
	 * @param parent	Parent fragment
	 * @param element   Markup element
	 */

	private ComponentSpec(String id, ComponentSpec parent, Element element) {
		this(parent.templateClass);
		this.id = id;
		this.parent = parent;
		this.element = element;
	}


	/**
	 * Adds a new component specification as a child of this one.
	 *
	 * @param id	  Component specification id
	 * @param element Markup element
	 * @return New child specification
	 */

	ComponentSpec add(String id, Element element) {
		ComponentSpec child = new ComponentSpec(id, this, element);

		templateClass.add(id, child);
		children.add(child);

		return child;
	}


	/**
	 * Adds a new child component specification to hold template text.
	 *
	 * @param text Template text
	 */

	void addTemplateText(String text) {
		TemplatePart child = new TemplateText(text);
		children.add(child);
	}


	/**
	 * Gets a list of child templates - returns empty list if none.
	 *
	 * @return List of children
	 */

	List<TemplatePart> children() {
		return children;
	}


	/**
	 * Gets the parent of this template, or null if it's the root template.
	 *
	 * @return Parent template, or null
	 */

	ComponentSpec parent() {
		return parent;
	}


	/**
	 * Gets the element that was used to define the component in the
	 * template.
	 *
	 * @return Template element
	 */

	public Element element() {
		return element;
	}


	public void render(RenderContext context, Writer writer, final Template template) {
		if (isRoot()) {
			renderChildren(context, writer, template);

		} else if (template.contains(id)) {
			Renderer component = template.get(id);

			if (component != null) {
//				if (component instanceof Container) {
//					((Container) component).contents(new Renderer() {
//						public void render(RenderContext context, Writer writer) {
//							renderChildren(context, writer, template);
//						}
//					});
//				}
//
//				if (component instanceof ElementAware) {
//					((ElementAware) component).element(new Element(element));
//				}

                context.pushTemplateContext(new Element(element), new Renderer() {
						public void render(RenderContext context, Writer writer) {
							renderChildren(context, writer, template);
						}
					});

				component.render(context, writer);

                context.popTemplateContents();
			}

		} else if (element != null) {
			writer.tag(element.tag()).attribute("id", id).attributes(element);
			renderChildren(context, writer, template);
			writer.close();
		}
	}


	private void renderChildren(RenderContext context, Writer writer, final Template templateInstance) {
		for (TemplatePart part : children) {
			part.render(context, writer, templateInstance);
		}
	}

	private boolean isRoot() {
		return (parent == null);
	}
}

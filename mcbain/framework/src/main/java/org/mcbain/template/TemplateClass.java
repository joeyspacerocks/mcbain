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

import org.mcbain.render.Writer;
import org.mcbain.request.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of a component template, holding the hierarchy of
 * contained component specifications.
 */

public class TemplateClass implements TemplatePart {

	private ComponentSpec root;
	private List<ComponentSpec> components;
	private Map<String, ComponentSpec> lookup;
	private long timestamp;


	public TemplateClass() {
		root = new ComponentSpec(this);
		components = new ArrayList<ComponentSpec>();
		lookup = new HashMap<String, ComponentSpec>();
		timestamp = System.currentTimeMillis();
	}


	/**
	 * Adds a new component spec.
	 *
	 * @param id		Component id
	 * @param component Component spec
	 */

	public void add(String id, ComponentSpec component) {
		components.add(component);
		lookup.put(id, component);
	}


	public void render(Request context, Writer writer, Template templateInstance) {
		root.render(context, writer, templateInstance);
	}


	/**
	 * Tests to see if the template was created before the specified time.
	 *
	 * @param time Time to test against
	 * @return True if older than time, else false
	 */

	public boolean olderThan(long time) {
		return (timestamp < time);
	}


	/**
	 * Gets the root component specification that contains all the nested
	 * specifications.
	 *
	 * @return Root component specification
	 */

	public ComponentSpec root() {
		return root;
	}


	/**
	 * Creates a new template instance.
	 *
	 * @return Template instance
	 */

	public Template instance() {
		return new Template(this);
	}
}

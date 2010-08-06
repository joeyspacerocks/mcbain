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

package org.mcbain.request;

import org.mcbain.route.Router;
import org.mcbain.template.Template;
import org.mcbain.template.TemplateFactory;

import javax.servlet.ServletContext;

/**
 * The context provides components with access to the framework in the
 * current request context.
 */

public class Context {

	private Router router;
	private TemplateFactory templateFactory;
	private ServletContext context;

	/**
	 * Constructs a new instance.
	 */

	public Context(ServletContext servletContext, Router router) {
		context = servletContext;
		this.router = router;
		templateFactory = new TemplateFactory(context);
	}

	public Router router() {
		return router;
	}

	/**
	 * Gets the named template instance.
	 *
	 * @param name Template name
	 * @return Template instance
	 */

	public Template template(String name) {
		return templateFactory.instance(name);
	}

	public String link(String path) {
		return path;
	}
}

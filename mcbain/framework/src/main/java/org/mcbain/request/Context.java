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

package org.mcbain.request;

import javax.servlet.ServletContext;

import org.mcbain.template.Template;
import org.mcbain.template.TemplateFactory;

/************************************************************************
 * The context provides components with access to the framework in the 
 * current request context.
 */

public class Context {
    
    private Router router;
    private TemplateFactory templateFactory;
    private ServletContext context;
    
    /************************************************************************
     * Constructs a new instance.
     */

    public Context(ServletContext servletContext) {
    	context = servletContext;
        router = new Router();
        templateFactory = new TemplateFactory(context);
    }
    
    public Router router() {
        return router;
    }
    
    /************************************************************************
     * Gets the named template instance.
     * 
     * @param   name    Template name
     * @return          Template instance
     */
    
    public Template template(String name) {
        return templateFactory.instance(name);
    }
    
    public String link(String path) {
    	return path;
    }
    
    public ConfigurationBuilder configure() {
    	return new ConfigurationBuilder(this);
    }
}

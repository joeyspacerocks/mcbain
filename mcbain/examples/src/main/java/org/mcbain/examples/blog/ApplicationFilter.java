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

package org.mcbain.examples.blog;

import org.mcbain.Request;
import org.mcbain.render.RenderContext;
import org.mcbain.response.Response;
import org.mcbain.route.LinkBuilder;
import org.mcbain.routes.RouteHandler;
import org.mcbain.routes.Router;
import org.mcbain.template.TemplateFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Application filter - gateway to framework.
 */

public class ApplicationFilter implements Filter {

	private TemplateFactory templates;
	private LinkBuilder linkBuilder;
    private Router router;

    public void init(FilterConfig config) throws ServletException {
        router = new BlogApplication().buildRouter();
		templates = new TemplateFactory(config.getServletContext());
//		linkBuilder = new LinkBuilder(router);
	}


	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		Request request = new Request((HttpServletRequest) servletRequest);
		RouteHandler handler = router.route(request);

        boolean handled = false;

		if (handler != null) {
            Response response = handler.handle(request);
            
            RenderContext rc = new RenderContext(request, templates, linkBuilder);
            handled = response.commit((HttpServletResponse) servletResponse, rc);
        }

		if (!handled) {
			chain.doFilter(servletRequest, servletResponse);
		}
	}


	public void destroy() {
	}
}

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

package org.mcbain.examples.blog;

import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.request.Context;
import org.mcbain.request.Request;
import org.mcbain.route.Router;
import org.mcbain.template.TemplateFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Application filter - gateway to framework.
 */

public class ApplicationFilter implements Filter {

	private Context context;
	private TemplateFactory templates;


	public void init(FilterConfig config) throws ServletException {
		Router router = new BlogApplication().buildRouter();
		context = new Context(config.getServletContext(), router);
		templates = new TemplateFactory(config.getServletContext());
	}


	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		Request request = new Request(context, (HttpServletRequest) servletRequest);

		RenderContext rc = new RenderContext(request, templates);

		Renderer renderer = context.router().route(request);

		if (renderer != null) {
			Writer writer = new Writer();
			renderer.render(rc, writer);
			servletResponse.getWriter().write(writer.toString());

		} else {
			chain.doFilter(servletRequest, servletResponse);
		}
	}


	public void destroy() {
	}
}

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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.mcbain.Application;
import org.mcbain.Renderer;
import org.mcbain.Writer;
import org.mcbain.rest.Context;

/************************************************************************
 * Application filter - gateway to framework.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class ApplicationFilter implements Filter {

    private Application app;
    private Context context;
    
    
    // @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
    
    public void init(FilterConfig config) throws ServletException {
        context = new Context(config.getServletContext());
        app = new BlogApplication();
        app.initialise(context);
    }

    
    // @see javax.servlet.Filter#destroy()
    
    public void destroy() {
    }

    
    // @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hRequest = (HttpServletRequest) request;
        
        Renderer renderer = context.resources().route(hRequest.getRequestURI());
        
        if (renderer != null) {
            Writer writer = new Writer();
            renderer.render(context, writer);
            response.getWriter().write(writer.toString());
            
        } else {
            chain.doFilter(request, response);
        }
    }
}
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

package org.mcbain.servlet;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mcbain.Renderer;
import org.mcbain.Writer;
import org.mcbain.request.Context;
import org.mcbain.request.Request;

/************************************************************************
 * Application filter - gateway to framework.
 */

public class ApplicationFilter implements Filter {

    private Context context;
    
    
    public void init(FilterConfig config) throws ServletException {
    	ServletContext servletContext = config.getServletContext();
        context = new Context(servletContext);
        
        String appClassname = config.getInitParameter("application");

        if (appClassname == null) {
        	throw new RuntimeException("No application class specified");
        }
        
        try {
        	Class<?> appClass = Class.forName(appClassname);
        	Constructor<?> c = appClass.getConstructor(Context.class);
        	c.newInstance(context);
        	
        } catch (ClassNotFoundException e) {
        	throw new RuntimeException("Application class not found: " + appClassname, e);
        } catch (NoSuchMethodException e) {
        	throw new RuntimeException("Application class does not contain a constructor: " + appClassname + "(Context context)", e);
        } catch (InvocationTargetException e) {
        	throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
        	throw new RuntimeException(e);
        } catch (InstantiationException e) {
        	throw new RuntimeException(e);
        }
    }

    
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
    	Request request = new Request(context, (HttpServletRequest) servletRequest);
    	
        Renderer renderer = context.router().route(request);
        
        if (renderer != null) {
            Writer writer = new Writer();
            renderer.render(request, writer);
            servletResponse.getWriter().write(writer.toString());
            
        } else {
            chain.doFilter(servletRequest, (HttpServletResponse) servletResponse);
        }
    }

    
    public void destroy() {
    }
}

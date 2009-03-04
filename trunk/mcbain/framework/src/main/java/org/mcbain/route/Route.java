// Copyright 2009 Joe Trewin
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

package org.mcbain.route;

import org.mcbain.render.Renderer;
import org.mcbain.request.Controller;
import org.mcbain.request.Interceptor;
import org.mcbain.request.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * A route maps a URL pattern to a controller chain. The route may also have
 * a unique name.
 */

public class Route {

	private final String name;
	private final UriTemplate pattern;
	private final List<Interceptor> interceptors;
	private Controller controller;

	public Route(String name, String pattern, List<Interceptor> interceptors, Controller controller) {
		this.name = name;
		this.pattern = new UriTemplate(pattern);
		this.interceptors = new ArrayList<Interceptor>(interceptors);
		this.controller = controller;
	}

	public Uri matches(String url) {
		return pattern.match(url);
	}

	public Renderer process(Request request) {
		String method = request.servletRequest().getMethod().toUpperCase();

		boolean handled = false;
		for (Interceptor interceptor : interceptors) {
			handled = interceptor.intercept(request);
			if (!handled) {
				break;
			}
		}

		if (handled) {
			if ("GET".equals(method)) {
				return controller.get(request);

			} else if ("POST".equals(method)) {
				return controller.post(request);
			}
		}

		return null;
	}

	public String name() {
		return name;
	}

	public boolean named() {
		return name != null;
	}

    public UriTemplate pattern() {
        return pattern;
    }
}

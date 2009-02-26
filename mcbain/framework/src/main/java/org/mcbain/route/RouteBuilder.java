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

package org.mcbain.route;

import org.mcbain.request.Controller;
import org.mcbain.request.Interceptor;

import java.util.List;
import java.util.ArrayList;

/**
 * The route builder provides a fluent interface for configuring routes in a router.
 */

public class RouteBuilder {

    private String name;
    private String pattern;
    private List<Interceptor> interceptors;
    private Controller controller;

    private Router router;

    public RouteBuilder() {
        this(new Router());
    }

    public RouteBuilder(Router router) {
        this.router = router;
        interceptors = new ArrayList<Interceptor>();
    }

    public RouteBuilder route(String path) {
		pattern = path;
		return this;
	}

	public RouteBuilder to(Controller controller) {
		this.controller = controller;
        saveRoute();
		return this;
	}

	public RouteBuilder via(Interceptor interceptor) {
		interceptors.add(interceptor);
		return this;
	}

    public Router end() {
        return router;
    }

    private void saveRoute() {
        Route route = new Route(name, pattern, interceptors, controller);
        router.add(route);

        name = null;
        pattern = null;
        interceptors.clear();
        controller = null;
    }
}

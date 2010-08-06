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

package org.mcbain.routes;

import org.mcbain.Request;

/**
 * Router that delegates method calls to another router.
 * This is a convenience class that can be used as a base class for
 * routers that wish to decorate other routers with additional behaviour.
 */

abstract public class DelegatingRouter implements Router {
    private Router router;

    protected DelegatingRouter(Router router) {
        this.router = router;
    }

    @Override
    public Router add(String path, RouteHandler handler) {
        router.add(path, handler);
        return this;
    }

    @Override
    public Router defaultHandler(RouteHandler handler) {
        router.defaultHandler(handler);
        return this;
    }

    @Override
    public RouteHandler route(Request request) {
        return router.route(request);
    }

    @Override
    public String buildPath(String path, Object... params) {
        return router.buildPath(path, params);
    }
}

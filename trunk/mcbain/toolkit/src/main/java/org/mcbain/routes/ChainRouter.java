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

import java.util.ArrayList;
import java.util.List;

/**
 * A router that queries a list of internal routers until one returns a route.
 * The internal routers are queried in the order that they are defined in.
 * Any default routes defined by the internal routers are ignored.
 */

public class ChainRouter implements Router {

    private List<Router> chain = new ArrayList<Router>();
    private RouteHandler defaultHandler;

    @Override
    public Router add(String path, RouteHandler handler) {
        return this;
    }

    @Override
    public Router defaultHandler(RouteHandler handler) {
        this.defaultHandler = handler;
        return this;
    }

    @Override
    public RouteHandler route(Request request) {
        for (Router router : chain) {
            RouteHandler handler = router.route(request);
            if (handler != null) {
                return handler;
            }
        }

        return defaultHandler;
    }

    @Override
    public String buildPath(String path, Object... params) {
        return null;
    }

    /**
     * Add a router to the list of internal routers that will be asked to
     * match routers.
     *
     * @param   router      Router to add to the chain
     * @return  This chain router
     */
    
    public ChainRouter addRouter(Router router) {
        chain.add(router);
        return this;
    }
}

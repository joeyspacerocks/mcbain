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
import org.mcbain.test.MockRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Unit tests for the exact path router.
 */

@Test
public class ExactPathRouterTest {
    private Request request;
    private Router router;

    @BeforeMethod
    public void setup() {
        request = new MockRequest("/path");
        router = new ExactPathRouter();
    }

    public void shouldReturnNoneWhenNoRouteFoundAndNoDefault() {
        RouteHandler handler = router.route(request);
        assertEquals(handler, RouteHandler.NONE);
    }

    public void shouldReturnDefaultWhenNoRouteFound() {
        RouteHandler defaultHandler = new MockRouteHandler();
        router.defaultHandler(defaultHandler);

        RouteHandler handler = router.route(request);
        assertEquals(handler, defaultHandler);
    }

    public void shouldReturnHandlerMatchedByPath() {
        RouteHandler pathHandler = new MockRouteHandler();
        router.add("/path", pathHandler);
        router.add("/path2", new MockRouteHandler());

        RouteHandler handler = router.route(request);
        assertEquals(handler, pathHandler);
    }

    public void shouldReturnFirstHandlerIfMultipleMatches() {
        RouteHandler pathHandler = new MockRouteHandler();
        router.add("/path", pathHandler);
        router.add("/path", new MockRouteHandler());

        RouteHandler handler = router.route(request);
        assertEquals(handler, pathHandler);
    }

    public void shouldNotReturnHandlerIfPathDoesNotMatches() {
        RouteHandler pathHandler = new MockRouteHandler();
        router.add("/path2", pathHandler);

        RouteHandler handler = router.route(request);
        assertEquals(handler, RouteHandler.NONE);
    }
}

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

import org.mcbain.test.MockRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Tests the aggregate router that delegates to an internal list of routers.
 */

@Test
public class ChainRouterTest {

    private ChainRouter router;
    private MockRequest request;

    @BeforeMethod
    public void setup() {
        request = new MockRequest("/path");
        router = new ChainRouter();
    }

    public void shouldReturnNullIfNoRoutersAddedAndNoDefault() {
        assertNull(router.route(request));
    }

    public void shouldReturnDefaultIfNoRoutersAdded() {
        RouteHandler expected = new MockRouteHandler();
        router.defaultHandler(expected);

        assertEquals(router.route(request), expected);
    }

    public void shouldReturnMatchingRouteFromInnerRouter() {
        RouteHandler expected = new MockRouteHandler();
        router.addRouter(new ExactPathRouter().add("/path", expected));

        assertEquals(router.route(request), expected);
    }

    public void shouldReturnFirstMatchingRouteWhenMultipleInnerRoutersWouldMatch() {
        RouteHandler expected = new MockRouteHandler();
        router
            .addRouter(new ExactPathRouter().add("/path", expected))
            .addRouter(new ExactPathRouter().add("/path", new MockRouteHandler()));

        assertEquals(router.route(request), expected);
    }

    public void shouldReturnMatchingRouteFromSecondRouterIfFirstFailsToMatch() {
        RouteHandler expected = new MockRouteHandler();
        router
            .addRouter(new ExactPathRouter().add("/fail", new MockRouteHandler()))
            .addRouter(new ExactPathRouter().add("/path", expected));

        assertEquals(router.route(request), expected);
    }
}

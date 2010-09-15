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
 * Tests for a router that uses Ant-style wildcard path matching.
 */

@Test
public class WildcardPathRouterTest {

    private Router router;
    private RouteHandler expectedHandler;

    @BeforeMethod
    public void setup() {
        router = new WildcardPathRouter();
        expectedHandler = new MockRouteHandler();
    }

    public void shouldReturnNoneWhenNoDefaultAndNoMatch() {
        Request request = new MockRequest("/path");
        assertEquals(router.route(request), RouteHandler.NONE);
    }

    public void shouldReturnDefaultWhenNoMatch() {
        Request request = new MockRequest("/path");
        router.defaultHandler(expectedHandler);
        assertEquals(router.route(request), expectedHandler);
    }

    public void shouldMatchFixedPathForRootPath() {
        Request request = new MockRequest("/");
        router.add("/", expectedHandler);
        assertEquals(router.route(request), expectedHandler);
    }

    public void shouldMatchFixedPath() {
        Request request = new MockRequest("/path");
        router.add("/path", expectedHandler);
        assertEquals(router.route(request), expectedHandler);
    }

    public void shouldMatchFixedPathWithMultipleSections() {
        Request request = new MockRequest("/path/leaf");
        router
            .add("/path/leaf", expectedHandler)
            .add("/path/nomatch", new MockRouteHandler());
        
        assertEquals(router.route(request), expectedHandler);
    }

    public void shouldMatchSingleSectionWildcard() {
        Request request = new MockRequest("/path");
        router.add("/*", expectedHandler);
        assertEquals(router.route(request), expectedHandler);
    }

    public void shouldMatchSingleSectionNumericWildcard() {
        Request request = new MockRequest("/123");
        router.add("/#", expectedHandler);
        assertEquals(router.route(request), expectedHandler);
    }

    public void shouldNotMatchSingleSectionNumericWildcardAgainstNonNumericPath() {
        Request request = new MockRequest("/12path34");
        router.add("/#", expectedHandler);
        assertEquals(router.route(request), RouteHandler.NONE);
    }

    public void shouldMatchSingleSectionNumericWildcardAfterFixedSection() {
        Request request = new MockRequest("/path/123");
        router.add("/path/#", expectedHandler);
        assertEquals(router.route(request), expectedHandler);
    }

    public void shouldMatchSingleSectionWildcardAfterFixedSection() {
        Request request = new MockRequest("/path/leaf");
        router.add("/path/*", expectedHandler);
        assertEquals(router.route(request), expectedHandler);
    }

    public void shouldNotMatchSingleSectionWildcardWhenPrecedingSectionPresent() {
        Request request = new MockRequest("/path/leaf");
        router.add("/*", expectedHandler);
        assertEquals(router.route(request), RouteHandler.NONE);
    }

    public void shouldMatchMultiSectionWildcard() {
        Request request = new MockRequest("/path");
        router.add("/**", expectedHandler);
        assertEquals(router.route(request), expectedHandler);
    }

    public void shouldMatchMultiSectionWildcardAgainstMultipleSections() {
        Request request = new MockRequest("/path/path/leaf");
        router
            .add("/**/leaf", expectedHandler)
            .add("/*/path/nomatch", new MockRouteHandler())
            .add("/**/nomatch", new MockRouteHandler());
        assertEquals(router.route(request), expectedHandler);
    }

    public void shouldReturnFirstMatchIfMoreThanOneResult() {
        Request request = new MockRequest("/path/path/leaf");
        router
            .add("/*/path/leaf", expectedHandler)
            .add("/**/leaf", new MockRouteHandler())
            .add("/**/nomatch", new MockRouteHandler());
        assertEquals(router.route(request), expectedHandler);
    }

    public void shouldMatchCombintionWildcards() {
        Request request = new MockRequest("/path/path/123/path/leaf");
        router.add("/**/#/*/leaf", expectedHandler);
        assertEquals(router.route(request), expectedHandler);
    }

    public void shouldStoreWildcardSectionAsRequestParameter() {
        Request request = new MockRequest("/path");
        router.add("/(*:value)", expectedHandler);

        router.route(request);

        assertEquals(request.param("value"), "path");
    }

    public void shouldStoreFixedSectionAsRequestParameter() {
        Request request = new MockRequest("/path");
        router.add("/(path:value)", expectedHandler);

        router.route(request);

        assertEquals(request.param("value"), "path");
    }

    public void shouldBuildPathForFixedPath() {
        assertEquals(router.buildPath("/path"), "/path");
    }

    public void shouldReplaceWildcardWithParameterWhenBuildinPath() {
        assertEquals(router.buildPath("/*", "path"), "/path");
    }
}

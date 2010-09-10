//-----------------------------------------------------------------------
// Copyright Can Factory Limited, UK
// http://www.canfactory.com - mailto:info@canfactory.com
//
// The copyright to the computer program(s) (source files, compiled
// files and documentation) herein is the property of Can Factory
// Limited, UK.
// The program(s) may be used and/or copied only with the written
// permission of Can Factory Limited or in accordance with the terms
// and conditions stipulated in the agreement/contract under which
// the program(s) have been supplied.
//-----------------------------------------------------------------------

package org.mcbain.routes;

import org.mcbain.Request;
import org.mcbain.response.Response;
import org.mcbain.test.MockRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for a route handler base class that calls a method based on the HTTP method.
 */

@Test
public class MethodRouteHandlerTest {
    private MockRequest request;
    private Response expectedResponse;

    @BeforeMethod
    public void setup() {
        request = new MockRequest();
        expectedResponse = mock(Response.class);
    }

    public void shouldRouteGetRequestToGetMethod() {
        request.method("GET");

        RouteHandler handler = new MethodRouteHandler() {
            public Response get(Request request) {
                return expectedResponse;
            }
        };

        assertEquals(handler.handle(request), expectedResponse);
    }

    public void shouldRoutePostRequestToPostMethod() {
        request.method("POST");

        RouteHandler handler = new MethodRouteHandler() {
            public Response post(Request request) {
                return expectedResponse;
            }
        };

        assertEquals(handler.handle(request), expectedResponse);
    }

    public void shouldRouteDeleteRequestToDeleteMethod() {
        request.method("DELETE");

        RouteHandler handler = new MethodRouteHandler() {
            public Response delete(Request request) {
                return expectedResponse;
            }
        };

        assertEquals(handler.handle(request), expectedResponse);
    }

    public void shouldRoutePutRequestToPutMethod() {
        request.method("PUT");

        RouteHandler handler = new MethodRouteHandler() {
            public Response put(Request request) {
                return expectedResponse;
            }
        };

        assertEquals(handler.handle(request), expectedResponse);
    }
}

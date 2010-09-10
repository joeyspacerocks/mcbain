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
import org.mcbain.response.ErrorResponse;
import org.mcbain.response.Response;

/**
 * Route handler base class that passes control to a handler method based on the
 * HTTP method of the request.
 *
 * The default implementations of each handler method is to return an error
 * response with HTTP code 405 (method not allowed).
 */

abstract public class MethodRouteHandler implements RouteHandler {

    @Override
    public Response handle(Request request) {
        if (request.isGet()) {
            return get(request);

        } else if (request.isPost()) {
            return post(request);

        } else if (request.isDelete()) {
            return delete(request);

        } else if (request.isPut()) {
            return put(request);
        }

        return null;
    }

    public Response get(Request request) {
        return new ErrorResponse(405);
    }

    public Response post(Request request) {
        return new ErrorResponse(405);
    }

    public Response delete(Request request) {
        return new ErrorResponse(405);
    }

    public Response put(Request request) {
        return new ErrorResponse(405);
    }
}

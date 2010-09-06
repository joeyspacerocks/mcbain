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

package org.mcbain.response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Response that writes out an HTTP error code in the status header.
 */

public class ErrorResponse implements Response {
    private int statusCode;
    private String message;
    private Response response;

    /**
     * Constructs an error response that when committed sets the status to the supplied
     * HTTP status code. No other headers or content will be committed to the HTTP response,
     * so it should be used when some other mechanism is supplying the response content.
     *
     * @param   statusCode      HTTP status code
     */

    public ErrorResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Constructs an error response that when committed sets the status to the supplied
     * HTTP status code and outputs the message to the HTTP response.
     *
     * @param   statusCode      HTTP status code
     * @param   message         Text message
     */

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    /**
     * Constructs an error response that when committed sets the status to the supplied
     * HTTP status code and then passes control to the passed Response instance.
     *
     * @param   statusCode      HTTP status code
     * @param   response        Response to pass control to
     */

    public ErrorResponse(int statusCode, Response response) {
        this.statusCode = statusCode;
        this.response = response;
    }

    @Override
    public void commit(HttpServletResponse response) {
        response.setStatus(statusCode);

        if (this.response != null) {
            this.response.commit(response);

        } else if (message != null) {
            try {
                response.getWriter().println(message);
            } catch (IOException e) {
                // FIXME: logging? errors?
                e.printStackTrace();
            }
        }
    }
}

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

package org.mcbain.request;

import org.mcbain.render.RenderContext;
import org.mcbain.render.Writer;
import org.mcbain.render.Renderer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Response handler that writes the output of a renderer to the response
 * output stream.
 */

public class RenderedResponse implements Response {

    private Renderer renderer;

    public RenderedResponse(Renderer renderer) {
        this.renderer = renderer;
    }

    public boolean commit(HttpServletResponse servletResponse, RenderContext rc) {
        Writer writer = new Writer();
        renderer.render(rc, writer);

        try {
            servletResponse.getWriter().write(writer.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error rendering response to output stream", e);
        }

        return true;
    }
}

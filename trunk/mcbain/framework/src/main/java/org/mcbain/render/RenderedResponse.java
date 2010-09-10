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

package org.mcbain.render;

import org.mcbain.response.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Response handler that writes the output of a renderer to the response
 * output stream.
 *
 * FIXME: sort out interface to this response - specifically how/where the render context comes from
 */

public class RenderedResponse implements Response {

    private Renderer renderer;
    private RenderContext renderContext;

    public RenderedResponse(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void commit(HttpServletResponse servletResponse) {
        Writer writer = new Writer();
        renderer.render(renderContext, writer);

        try {
            servletResponse.getWriter().write(writer.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error rendering response to output stream", e);
        }
    }

    public boolean commit(HttpServletResponse servletResponse, RenderContext rc) {
        renderContext = rc;
        commit(servletResponse);
        return true;
    }
}

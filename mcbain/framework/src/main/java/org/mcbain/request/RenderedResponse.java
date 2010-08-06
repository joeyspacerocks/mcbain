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

package org.mcbain.request;

import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;

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

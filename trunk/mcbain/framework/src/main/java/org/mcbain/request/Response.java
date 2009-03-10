package org.mcbain.request;

import org.mcbain.render.RenderContext;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles the response from a controller or interceptor.
 */

public interface Response {
    public boolean commit(HttpServletResponse servletResponse, RenderContext rc);
}

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

package org.mcbain;

import org.mcbain.binding.PropertyAccessor;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Encapsulates an incoming HTTP request.
 */

public class Request implements PropertyAccessor {
    private HttpServletRequest request;
    private Map<String, String> params;
    private Map<String, Object> resources;

    public Request(HttpServletRequest request) {
        params = new HashMap<String, String>();
        resources = new HashMap<String, Object>();
        this.request = request;
    }

    public String uri() {
        return request.getRequestURI();
    }

    /**
     * Gets the underlying HttpServletRequest.
     *
     * @return  Servlet request
     */
    
    public HttpServletRequest httpRequest() {
        return request;
    }

    public String param(String name) {
        return params.containsKey(name) ? params.get(name) : request.getParameter(name);
    }

    public void param(String name, String value) {
        params.put(name, value);
    }

    public boolean isGet() {
        return methodEquals("GET");
    }

    public boolean isPost() {
        return methodEquals("POST");
    }

    public boolean isDelete() {
        return methodEquals("DELETE");
    }

    public boolean isPut() {
        return methodEquals("PUT");
    }

    private boolean methodEquals(String value) {
        String method = request.getMethod();
        return method != null && method.toUpperCase().equals(value);
    }

    @Override
    public Object getProperty(String property) {
        return param(property);
    }

    @Override
    public void setProperty(String property, Object value) {
        param(property, value == null ? null : value.toString());
    }

    @Override
    public boolean hasProperty(String property) {
        return params.containsKey(property) || (request.getParameter(property) != null);
    }

    @SuppressWarnings({"unchecked"})
    public List<String> propertyNames() {
        List<String> properties = new ArrayList<String>();
        Enumeration parameters = request.getParameterNames();

        if (parameters != null) {
            properties.addAll(Collections.<String>list(parameters));
        }

        properties.addAll(params.keySet());

        return properties;
    }

    public <T> T resource(String id, Class<T> type) {
        Object r = resources.get(id);

        if (r != null && (r.getClass().isAssignableFrom(type))) {
            return (T) r;
        }

        return null;
    }

    public void resource(String id, Object resource) {
        resources.put(id, resource);
    }
}

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

import org.mcbain.route.Uri;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * The request provides components with access to the framework in the
 * current request context.
 */

public class Request {

	private HttpServletRequest servletRequest;
	private Uri uri;
	private Context context;
	private Map<Class, Object> resources;
//	private InputHandler inputHandler;

	public Request(Context context, HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
		this.context = context;
		this.resources = new HashMap<Class, Object>();
	}

	public String parameter(String name) {
		String uriParameter = uri.parameter(name);
		return uriParameter == null ? servletRequest.getParameter(name) : uriParameter;
	}

	public void uri(Uri uri) {
		this.uri = uri;
	}

//	public InputHandler input() {
//		return inputHandler;
//	}
//
//	public void input(InputHandler inputHandler) {
//		this.inputHandler = inputHandler;
//	}

	public HttpServletRequest servletRequest() {
		return servletRequest;
	}

	public Context context() {
		return context;
	}

	public <T> void resource(T resource) {
		resources.put(resource.getClass(), resource);
	}

	@SuppressWarnings("unchecked")
	public <T> T resource(Class<T> type) {
		return (T) resources.get(type);
	}
}

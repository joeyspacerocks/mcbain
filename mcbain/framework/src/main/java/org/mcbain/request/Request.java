// Copyright 2007 Joe Trewin
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.mcbain.request;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/************************************************************************
 * The request provides components with access to the framework in the 
 * current request context.
 */

public class Request {

	private HttpServletRequest servletRequest;
	private Uri uri;
	private Map<String,String> errors;
	private Context context;
	private Map<String, Object> resources;
	
	public Request(Context context, HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
		this.errors = new LinkedHashMap<String,String>();
		this.context = context;
		this.resources = new HashMap<String, Object>();
	}
	
	public String parameter(String name) {
		String uriParameter = uri.parameter(name);
		return uriParameter == null ? servletRequest.getParameter(name) : uriParameter;
	}
	
	public Request has(String... names) {
		for (String name : names) {
			String value = parameter(name);
			if (value == null || value.equals("")) {
				errors.put(name, "Please enter a " + name);
			}
		}
		return this;
	}

	protected void uri(Uri uri) {
		this.uri = uri;
	}
	
	public boolean ok() {
		return errors.isEmpty();
	}
	
	public Map<String,String> errors() {
		return errors;
	}
	
	public HttpServletRequest servletRequest() {
		return servletRequest;
	}
	
	public Context context() {
		return context;
	}
	
	public void resource(String name, Object resource) {
		resources.put(name, resource);
	}
	
	public Object resource(String name) {
		return resources.get(name);
	}
}
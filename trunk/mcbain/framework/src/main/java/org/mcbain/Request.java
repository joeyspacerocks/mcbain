package org.mcbain;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mcbain.rest.Uri;

public class Request {

	private HttpServletRequest servletRequest;
	private Uri uri;
	private boolean error;
	private Map<String,String> errors;
	
	
	public Request(HttpServletRequest servletRequest, Uri uri) {
		this.servletRequest = servletRequest;
		this.uri = uri;
		this.errors = new LinkedHashMap<String,String>();
	}
	
	public String get(String name) {
		return servletRequest.getParameter(name);
	}
	
	public Request has(String... names) {
		for (String name : names) {
			String value = get(name);
			if (value == null || value.equals("")) {
				errors.put(name, "Please enter a " + name);
				error = true;
			}
		}
		return this;
	}
	
	public boolean ok() {
		return !error;
	}
	
	public Map<String,String> errors() {
		return errors;
	}
	
	public Uri uri() {
		return uri;
	}
}

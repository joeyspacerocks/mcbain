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

package org.mcbain.route;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a URI string template, containing a mixture of static and
 * parameterized parts. Responsible for matching against URIs and
 * extracting parameters.
 */

public class UriPattern {

	public String pattern;
	public List<Matcher> matchers;


	/**
	 * Constructs a new template from the supplied pattern.
	 *
	 * @param pattern Uri pattern
	 */

	public UriPattern(String pattern) {
		this.pattern = pattern;
		this.matchers = parse(pattern);
	}


    /**
	 * Creates an uri with no parameters, based on the template.
	 *
	 * @return Uri
	 */

	public Uri emptyUri() {
		return new Uri();
	}


	/**
	 * Builds a complete uri by merging the template with the supplied
	 * parameters.
	 *
	 * @param parameters Parameter map
	 * @return String uri
	 */

	public String build(Object... parameters) {
        Uri uri = new Uri();
        uri.parameters(parameters);

		StringBuilder b = new StringBuilder();
		for (Matcher m : matchers) {
			b.append("/").append(m.toString(uri));
		}
        
		return b.toString();
	}


	/**
	 * Compares the supplied URI string with the pattern, returning a Uri
	 * that reflectes the result.
	 *
	 * @return Uri indicating result
	 */

	public Uri match(String actual) {
		Uri result = new Uri();

		String[] parts = split(actual);

		if (matchers.size() != parts.length) {
			result = Uri.NOMATCH;

		} else {
			for (int i = 0; i < parts.length; i++) {
				if (!matchers.get(i).matches(result, parts[i])) {
					return Uri.NOMATCH;
				}
			}
		}

		return result;
	}


	/**
	 * Parses the pattern into a list of matchers.
	 */

	protected List<Matcher> parse(String pattern) {
		List<Matcher> matchers = new ArrayList<Matcher>();

		String[] parts = split(pattern);

		for (String part : parts) {
			if (part.length() > 0 && part.charAt(0) == '$') {
				matchers.add(new ParameterMatcher(part.substring(1)));

			} else {
				matchers.add(new StringMatcher(part));
			}
		}

		return matchers;
	}


	/**
	 * Matchers compare a string with their content.
	 */

	public interface Matcher {
		public boolean matches(Uri result, String value);

		public String toString(Uri uri);
	}


	/**
	 * Matcher that compares its string contents with the supplied string.
	 */

	public class StringMatcher implements Matcher {
		private String actual;

		public StringMatcher(String actual) {
			this.actual = actual;
		}

		public boolean matches(Uri result, String value) {
			return actual.equals(value);
		}

		public String toString(Uri uri) {
			return actual;
		}
	}


	/**
	 * Matcher that records the value as a named parameter.
	 */

	public class ParameterMatcher implements Matcher {
		private String name;

		public ParameterMatcher(String name) {
			this.name = name;
		}

		public boolean matches(Uri result, String value) {
			try {
				value = URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			result.addParameter(name, value);
			return true;
		}

		public String toString(Uri uri) {
			try {
				return URLEncoder.encode(uri.parameter(name), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
	}


	/**
	 * Splits a uri path into parts.
	 *
	 * @param value Uri path
	 * @return Array of parts
	 */

	private String[] split(String value) {
		return value.charAt(0) == '/' ? value.substring(1).split("/") : value.split("/");
	}
}

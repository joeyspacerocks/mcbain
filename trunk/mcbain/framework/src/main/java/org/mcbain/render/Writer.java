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

package org.mcbain.render;

import org.mcbain.template.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Writer for capturing and writing markup.
 */

public class Writer {

	private enum State {
		CONTENT, TAG, EMPTY_TAG
	}

	private StringBuilder buffer;
	private State state;
	private List<String> tags;
	private String sealed;


	/**
	 * Constructs a new writer.
	 */

	public Writer() {
		this.buffer = new StringBuilder();
		this.state = State.CONTENT;
	}


	/**
	 * Opens an empty tag - i.e. an element that doesn't contain a body.
	 *
	 * @return Writer
	 */

	public Writer emptyTag(String tag) {
		state = State.EMPTY_TAG;
		buffer.append("<").append(tag);
		return this;
	}


	/**
	 * Opens a tag - i.e. an element that could contain a body.
	 *
	 * @return Writer
	 */

	public Writer tag(String tag) {
		closeOpenTag();

		state = State.TAG;
		buffer.append("<").append(tag);
		push(tag);
		return this;
	}


	/**
	 * Adds an attribute to an open tag with the specified value.
	 *
	 * @param name  Attribute name
	 * @param value Attribute value
	 * @return Writer
	 */

	public Writer attribute(String name, String value) {
		value = safe(value);
		buffer.append(' ').append(name).append("=\"");
		if (value != null) {
			buffer.append(value);
		}
		buffer.append('"');
		return this;
	}


	/**
	 * Adds a collection of attributes to the open tag.
	 *
	 * @param element Element containing attributes (may be null)
	 * @return Writer
	 */

	public Writer attributes(Element element) {
		if (element != null) {
			Set<String> attributes = element.attributeNames();
			for (String name : attributes) {
				attribute(name, element.attribute(name));
			}
		}

		return this;
	}


	/**
	 * Adds the body value to the current tag.
	 *
	 * @param body Body value
	 * @return Writer
	 */

	public Writer body(String body) {
		body = safe(body);
		buffer.append('>').append(body).append("</").append(pop()).append('>');
		state = State.CONTENT;
		return this;
	}


	/**
	 * Adds content to the writer.
	 *
	 * @param content Content text
	 * @param raw	 True to leave content unescaped
	 * @return Writer
	 */

	public Writer print(String content, boolean raw) {
		closeOpenTag();

		if (content != null) {
			if (!raw) {
				content = safe(content);
			}
			buffer.append(content);
		}

		state = State.CONTENT;
		return this;
	}


	/**
	 * Adds the contents of a nested writer to the writer.
	 *
	 * @param writer Nested writer
	 * @return Writer
	 */

	public Writer print(Writer writer) {
		closeOpenTag();

		buffer.append(writer.toString());
		state = State.CONTENT;
		return this;
	}


	/**
	 * Closes the currently open tag on the stack.
	 *
	 * @return Writer
	 */

	public Writer close() {
		closeOpenTag();

		String tag = pop();
		if (tag != null) close(tag);

		state = State.CONTENT;
		return this;
	}


	/**
	 * Resets the writer by clearing any buffered content.
	 *
	 * @return Writer
	 */

	public Writer reset() {
		state = State.CONTENT;
		buffer.setLength(0);
		if (tags != null) tags.clear();
		return this;
	}


	/**
	 * Seals the writer preventing any additional content from being written
	 * to it.
	 *
	 * @return Writer
	 */

	public Writer seal() {
		sealed = toString();
		return this;
	}


	// @see java.lang.Object#toString()

	public String toString() {
		if (sealed == null) {
			tidy();
			return buffer.toString();
		} else {
			return sealed;
		}
	}


	/**
	 * Closes any partially written open or empty tag.
	 */

	private void closeOpenTag() {
		if (state == State.EMPTY_TAG) {
			buffer.append(" />");
		} else if (state == State.TAG) {
			buffer.append('>');
		}
	}


	/**
	 * Tidies up any open tags.
	 */

	private void tidy() {
		close();

		String tag;

		while ((tag = pop()) != null) {
			close(tag);
		}
	}


	/**
	 * Appends a close tag to the buffer.
	 *
	 * @param tag Tag to close
	 */

	private void close(String tag) {
		buffer.append("</").append(tag).append('>');
	}


	/**
	 * Pops the last tag off the currently open tags stack.
	 *
	 * @return Last tag opened, or null if nothing on stack
	 */

	private String pop() {
		return (tags == null || tags.isEmpty() ? null : tags.remove(tags.size() - 1));
	}


	/**
	 * Pushes a tag onto the currently open tags stack.
	 *
	 * @param tag Tag to push onto the stach
	 */

	private void push(String tag) {
		if (tags == null) tags = new ArrayList<String>();
		tags.add(tag);
	}


	/**
	 * Escapes markup in a value to make it safe for writing to output.
	 *
	 * @param value Value to make safe (may be null)
	 * @return Safe value (null if value is null)
	 */

	private String safe(String value) {
		if (value == null) return value;

		StringBuilder s = new StringBuilder(value.length());

		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == '<') {
				s.append("&lt;");
			} else if (c == '>') {
				s.append("&gt;");
			} else if (c == '&') {
				s.append("&amp;");
			} else {
				s.append(c);
			}
		}

		return s.toString();
	}
}

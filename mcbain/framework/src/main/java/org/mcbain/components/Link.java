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

package org.mcbain.components;

import org.mcbain.UrlBuilder;
import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.template.Element;

/**
 * Link component.
 */

public class Link implements Renderer {

	private String path;
	private String value;

    private Object[] parameters;
    private UrlBuilder urlBuilder;

    public Link(UrlBuilder urlBuilder) {
        this.urlBuilder = urlBuilder;
    }

	public Link(UrlBuilder urlBuilder, String path, Object... parameters) {
        this.urlBuilder = urlBuilder;
        this.path = path;
        this.parameters = parameters;
	}

	public Link path(String path, Object... parameters) {
		this.path = path;
        this.parameters = parameters;
        return this;
    }

    public Link parameters(Object... parameters) {
        this.parameters = parameters;
        return this;
    }

	public Link text(String value) {
		this.value = value;
        return this;
	}

	public void render(RenderContext context, Writer writer) {
        String url = urlBuilder.buildPath(path, parameters);

        Element element = context.element();
		element.attribute("href", url);

		writer.tag("a").attributes(element);

		if (value != null) {
			writer.body(value);
		} else {
			context.contents().render(context, writer);
			writer.close();
		}
	}
}

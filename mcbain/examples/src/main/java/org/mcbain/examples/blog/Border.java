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

package org.mcbain.examples.blog;

import org.mcbain.UrlBuilder;
import org.mcbain.components.Link;
import org.mcbain.components.Loop;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.template.Template;
import org.mcbain.template.TemplateFactory;

import static java.lang.System.currentTimeMillis;


/**
 * Border component used to render the common border around each page in
 * the blog.
 */

public class Border implements Renderer {

	private Blog blog;
    private UrlBuilder urlBuilder;
    private TemplateFactory templateFactory;

    public Border(final Blog blog, UrlBuilder urlBuilder, TemplateFactory templateFactory) {
		this.blog = blog;
        this.urlBuilder = urlBuilder;
        this.templateFactory = templateFactory;
    }

	public void render(RenderContext context, Writer writer) {
		long timestamp = currentTimeMillis();

		final Template template = templateFactory.instance("border");

        final String name = blog.getName();
        final Link archiveLink = new Link(urlBuilder, "archive");

		Loop<String> archiveLoop = new Loop<String>(blog.getArchives()) {
			public void currentValue(String value) {
                archiveLink.parameters(name, value).text(value);
				template.bind("archive", archiveLink);
			}
		};

		template.bind(
			"archives", archiveLoop,
			"title", new Link(urlBuilder, "blog", name).text(name),
			"newpost", new Link(urlBuilder, "newpost", name),
			"time", currentTimeMillis() - timestamp,
			"content", context.contents()
		);

		writer.reset();
		template.render(context, writer);
		writer.seal();
	}
}

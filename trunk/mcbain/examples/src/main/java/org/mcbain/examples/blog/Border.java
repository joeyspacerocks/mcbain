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

import org.mcbain.components.Link;
import org.mcbain.components.Loop;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.template.Template;

import static java.lang.System.currentTimeMillis;


/**
 * Border component used to render the common border around each page in
 * the blog.
 */

public class Border implements Renderer {

	private Blog blog;

	public Border(final Blog blog) {
		this.blog = blog;
	}

	public void render(RenderContext context, Writer writer) {
		long timestamp = currentTimeMillis();

		final Template template = context.template("border");

        String name = blog.getName();
        Link title = new Link().route("blog", "blog", name).displayAs(name);

		Loop<String> archiveLoop = new Loop<String>(blog.getArchives()) {
			public void currentValue(String value) {
				Link archive = new Link();
				archive.displayAs(value);
				archive.uri("/blog/" + blog.getName() + "/" + value.replace('/', '-'));
				template.bind("archive", archive);
			}
		};

		template.bind(
			"archives", archiveLoop,
			"title", title,
			"newpost", new Link("/blog/" + blog.getName() + "/newpost"),
			"time", currentTimeMillis() - timestamp,
			"content", context.contents()
		);

		writer.reset();
		template.render(context, writer);
		writer.seal();
	}
}

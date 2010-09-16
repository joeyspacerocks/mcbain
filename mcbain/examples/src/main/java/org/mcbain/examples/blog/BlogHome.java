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
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.template.Template;
import org.mcbain.template.TemplateFactory;


/**
 * Blog home page. Contains a list of archives, and a list of the last
 * few most recent posts.
 */

public class BlogHome implements Renderer {

	private Blog blog;
	private String archive;
    private UrlBuilder urlBuilder;
    private TemplateFactory templateFactory;


    public BlogHome(final Blog blog, final String archive, UrlBuilder urlBuilder, TemplateFactory templateFactory) {
		this.blog = blog;
		this.archive = archive;
        this.urlBuilder = urlBuilder;
        this.templateFactory = templateFactory;
    }

	public void render(RenderContext context, Writer writer) {
		Template template = templateFactory.instance("blog");

		template.bind(
			"border", new Border(blog, urlBuilder, templateFactory),
			"posts", new Posts(blog, archive == null ? blog.latestPosts() : blog.archivedPosts(archive), urlBuilder, templateFactory),
			"postTitle", archive == null ? "Recent Posts" : "Archive: " + archive
		);

		template.render(context, writer);
	}
}

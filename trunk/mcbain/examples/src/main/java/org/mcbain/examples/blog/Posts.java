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
import org.mcbain.components.If;
import org.mcbain.components.Link;
import org.mcbain.components.Loop;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.examples.blog.model.Post;
import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.template.Template;
import org.mcbain.template.TemplateFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


/**
 * Component used to display a list of posts.
 */

public class Posts implements Renderer {

	private Blog blog;
	private List<Post> posts;
    private TemplateFactory templateFactory;
    private UrlBuilder urlBuilder;

    public Posts(final Blog blog, final List<Post> posts, UrlBuilder urlBuilder, TemplateFactory templateFactory) {
		this.blog = blog;
		this.posts = posts;
        this.templateFactory = templateFactory;
        this.urlBuilder = urlBuilder;
    }

	public void render(RenderContext context, Writer writer) {
		final Template template = templateFactory.instance("posts");

		template.bind(
			"empty", new If(posts.isEmpty()),
			"posts", new Loop<Post>(posts) {
				public void currentValue(Post post) {
					template.bind(
						"title", post.getTitle(),
						"body", post.getContent(),
						"titleLink", new Link(urlBuilder, postLink(post)),
						"moreLink", new Link(urlBuilder, postLink(post))
					);
				}
			}
		);

		template.render(context, writer);
	}

	private String postLink(Post post) {
		try {
			String archive = post.getArchiveDate().replace('/', '-');
			return "/blog/" + blog.getName() + "/" + archive + "/" + URLEncoder.encode(post.getTitle(), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}

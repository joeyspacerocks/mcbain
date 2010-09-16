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
import org.mcbain.components.Form;
import org.mcbain.components.Input;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.template.Template;
import org.mcbain.template.TemplateFactory;


/**
 * Form for creating a new blog post.
 */

public class NewPost implements Renderer {

	private Blog blog;
    private UrlBuilder urlBuilder;
    private TemplateFactory templateFactory;

    public NewPost(final Blog blog, UrlBuilder urlBuilder, TemplateFactory templateFactory) {
		this.blog = blog;
        this.urlBuilder = urlBuilder;
        this.templateFactory = templateFactory;
    }

	public void render(final RenderContext context, Writer writer) {
        Template template = templateFactory.instance("newpost");

		template.bind(
			"border", new Border(blog, urlBuilder, templateFactory),
			"form", new Form("/blog/" + blog.getName() + "/newpost", urlBuilder),
			"title", new Input(),
			"content", new Input()
//			"errors", new Loop<InputError>(in.errors().errors()) {
//				public void currentValue(InputError value) {
//					template.bind("error", value.message());
//				}
//			}
		);

		template.render(context, writer);
	}
}

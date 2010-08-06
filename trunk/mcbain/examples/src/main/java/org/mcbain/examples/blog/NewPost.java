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

import org.mcbain.components.Form;
import org.mcbain.components.Input;
import org.mcbain.components.Loop;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.input.InputError;
import org.mcbain.input.InputHandler;
import org.mcbain.render.RenderContext;
import org.mcbain.render.Renderer;
import org.mcbain.render.Writer;
import org.mcbain.template.Template;


/**
 * Form for creating a new blog post.
 */

public class NewPost implements Renderer {

	private Blog blog;
	private InputHandler in;

	public NewPost(final Blog blog, InputHandler in) {
		this.blog = blog;
		this.in = in;
	}

	public void render(final RenderContext context, Writer writer) {
		final Template template = context.template("newpost");

		template.bind(
			"border", new Border(blog),
			"form", new Form("/blog/" + blog.getName() + "/newpost", in),
			"title", new Input(in),
			"content", new Input(in),
			"errors", new Loop<InputError>(in.errors().errors()) {
				public void currentValue(InputError value) {
					template.bind("error", value.message());
				}
			}
		);

		template.render(context, writer);
	}
}
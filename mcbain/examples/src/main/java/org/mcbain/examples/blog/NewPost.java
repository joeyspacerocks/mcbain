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

package org.mcbain.examples.blog;

import org.mcbain.Renderer;
import org.mcbain.Request;
import org.mcbain.TemplateInstance;
import org.mcbain.Writer;
import org.mcbain.components.Form;
import org.mcbain.components.Input;
import org.mcbain.components.Loop;
import org.mcbain.components.Value;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.rest.Context;


/************************************************************************
 * Form for creating a new blog post.
 */

public class NewPost implements Renderer {

    private Blog blog;
    private String title;
    private String content;
    private Request request;
    
    public NewPost(final Request request, final Blog blog) {
        this.blog = blog;
        this.request = request;
        title = request.get("title");
        content = request.get("content");
    }

    
    public void render(Context context, Writer writer) {
        TemplateInstance template = context.template("newpost");
        
        final Value error = new Value();
        
        template.bind(
            "border", new Border(blog),
            "form", new Form("newpost", "name", blog.getName()),
            "title", new Input(title),
            "content", new Input(content),
            "error", error,
            "errors", new Loop<String>(request.errors().keySet()) {
            	public void currentValue(String value) {
            		error.value(request.errors().get(value));
            	}
            }
        );
        
        template.render(context, writer);
    }
}

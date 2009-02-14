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

import org.mcbain.Container;
import org.mcbain.Renderer;
import org.mcbain.Writer;
import org.mcbain.components.Link;
import org.mcbain.components.Loop;
import org.mcbain.examples.blog.model.Blog;
import org.mcbain.request.Request;
import org.mcbain.template.Template;

import static java.lang.System.currentTimeMillis;


/************************************************************************
 * Border component used to render the common border around each page in
 * the blog.
 */

public class Border implements Renderer, Container {

    private Renderer content;
    private Blog blog;

    public Border(final Blog blog) {
        this.blog = blog;
    }

    public void contents(Renderer content) {
        this.content = content;
    }
    
    public void render(Request request, Writer writer) {
        long timestamp = currentTimeMillis();

        final Template template = request.template("border");

        Link title = new Link("/blog/" + blog.getName());
        title.value(blog.getName());

        Loop<String> archiveLoop = new Loop<String>(blog.getArchives()) {
            public void currentValue(String value) {
                Link archive = new Link();
                archive.value(value);
                archive.uri("/blog/" + blog.getName() + "/" + value.replace('/', '-'));
                template.bind("archive", archive);
            }
        };

        template.bind(
            "archives", archiveLoop,
            "title", title,
            "newpost", new Link("/blog/" + blog.getName() + "/newpost"),
            "time", currentTimeMillis() - timestamp,
            "content", content
        );

        writer.reset();
        template.render(request, writer);
        writer.seal();
    }
}

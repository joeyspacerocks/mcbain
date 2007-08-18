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

import java.util.List;

import org.mcbain.Components;
import org.mcbain.Renderer;
import org.mcbain.Templated;
import org.mcbain.Writer;
import org.mcbain.components.Loop;
import org.mcbain.components.Value;
import org.mcbain.examples.blog.model.Post;
import org.mcbain.template.Template;


/************************************************************************
 * Component used to display a list of posts.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class Posts implements Renderer, Templated {

    private Template template;
    private Components components;
    
    
    /************************************************************************
     * Constructs a new instance.
     */

    public Posts() {
        
        final Loop<Post> loop = new Loop<Post>() {
            public Iterable<Post> source() {
                return posts();
            }
        };
        
        Value title = new Value() {
            public Object value() {
                return loop.currentValue().getTitle();
            }
        };

        Value body = new Value() {
            public Object value() {
                return loop.currentValue().getContent();
            }
        };
        
        components = new Components()
            .add("posts", loop)
            .add("title", title)
            .add("body", body);
    }

    
    public List<Post> posts() {
        return null;
    }
    
    
    // @see org.redneck.Renderer#render(org.redneck.Writer)
    
    public void render(Writer writer) {
        template.render(writer, components);
    }


    // @see org.redneck.Templated#attachTemplate(org.redneck.template.Template)
    
    public void attachTemplate(Template template) {
        this.template = template;
    }


    // @see org.redneck.Templated#templateName()
    
    public String templateName() {
        return "posts";
    }
}

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

package org.mcbain.template;

import org.mcbain.TemplateInstance;
import org.mcbain.Writer;
import org.mcbain.rest.Context;

/************************************************************************
 * Renderable element representing a portion of a template.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public interface TemplatePart {

    /************************************************************************
     * Renders a template element, using the supplied collection of 
     * components to bind component references to instantiated components.
     * 
     * @param   context             Render context
     * @param   writer              Markup writer
     * @param   templateInstance    Template instance
     */
    
    public void render(Context context, Writer writer, TemplateInstance templateInstance);
}

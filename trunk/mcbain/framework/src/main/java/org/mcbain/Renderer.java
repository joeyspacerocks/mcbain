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

package org.mcbain;

import org.mcbain.request.Request;

/************************************************************************
 * Renderers provide a method for rendering themselves using the supplied
 * writer.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public interface Renderer {

    /************************************************************************
     * Renders using the supplied writer.
     * 
     * @param   context     Render context
     * @param   writer      Writer to render to
     */
    
    public void render(Request context, Writer writer);
}

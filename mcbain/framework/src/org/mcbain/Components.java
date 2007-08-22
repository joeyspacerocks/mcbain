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

import java.util.HashMap;

import org.mcbain.components.Value;

/************************************************************************
 * Collection of components keyed by id.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class Components extends HashMap<String, Renderer> {

    public static final Components NONE = new Components();
    
    
    /************************************************************************
     * Adds a component to the collection.
     * 
     * @param   id              Component id in the context of it's container
     * @param   component       Renderable component
     * @return                  This object
     */
    
    public Components bind(final String id, final Renderer component) {
        put(id, component);
        return this;
    }
    
    
    /************************************************************************
     * Adds an object to the collection. The object will be rendered using
     * its toString method.
     * 
     * @param   id              Component id in the context of it's container
     * @param   value           Object to be rendererd
     * @return                  This object
     */
    
    public Components bind(final String id, final Object value) {
        put(id, new Value() {
            public Object value() { return value; }
        });

        return this;
    }
}

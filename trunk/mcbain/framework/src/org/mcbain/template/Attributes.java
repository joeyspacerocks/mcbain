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

import java.util.HashMap;

/************************************************************************
 * Collection of attributes from a markup element.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class Attributes extends HashMap<String, String> {
    
    /************************************************************************
     * Constructs a new instance with an initial capacity.
     * 
     * @param   size        Initial capacity
     */

    public Attributes(int size) {
        super(size);
    }
}

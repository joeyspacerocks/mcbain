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

package org.mcbain.util;

import java.util.ArrayList;
import java.util.List;

/************************************************************************
 * Collection utility methods.
 *
 * @version $Revision$
 * @author  Joe Trewin
 */

public class Lists {

    /************************************************************************
     * Creates an ArrayList initialised with a single element.
     * 
     * @param   element     Element to add to array
     * @return              ArrayList containing the element
     */
    
    public static <T> List<T> list(T element) {
        List<T> list = new ArrayList<T>(1);
        list.add(element);
        return list;
    }
    
    
    /************************************************************************
     * Creates an ArrayList from an array of elements.
     * 
     * @param   args    Array of elements
     * @return          ArrayList containing elements
     */
    
    public static <T> List<T> list(T... args) {
        List<T> list = new ArrayList<T>(args.length);
        
        for (T a : args) {
            list.add(a);
        }
        
        return list;
    }
}

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

import org.mcbain.rest.Resources;

/************************************************************************
 * Declares configuration of an application.
 */

public interface Application {

    /************************************************************************
     * Gets a collection of declared resources that the application handles.
     * 
     * @return      Resources collection
     */
    
    public Resources resources();
}

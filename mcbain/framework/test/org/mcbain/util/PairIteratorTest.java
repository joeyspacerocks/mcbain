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

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/************************************************************************
 * Unit tests for pair iterator.
 */

public class PairIteratorTest {

    /************************************************************************
     * Tests the iteration of a correctly paired source.
     */
    
    @Test
    public void testCorrectSource() {
        String[] data = new String[] { "key1", "value1", "key2", "value2", "key3", "value3" };
        PairIterator<String, String> it = new PairIterator<String, String>(data);
        
        int i = 0;
        while (it.hasNext()) {
            assertEquals(data[i++], it.nextKey());
            assertEquals(data[i++], it.nextValue());
        }
    }
}

//-----------------------------------------------------------------------
// Copyright Can Factory Limited, UK 
// http://www.canfactory.com - mailto:info@canfactory.com
//
// The copyright to the computer program(s) (source files, compiled 
// files and documentation) herein is the property of Can Factory 
// Limited, UK.
// The program(s) may be used and/or copied only with the written 
// permission of Can Factory Limited or in accordance with the terms 
// and conditions stipulated in the agreement/contract under which
// the program(s) have been supplied.
//-----------------------------------------------------------------------

package org.mcbain.binding;

import java.util.Map;

/**
 * Property accessor implementation that wraps a map keyed by strings with
 * object values.
 */

public class MapPropertyAccessor implements PropertyAccessor {
    private Map<String, Object> map;

    public MapPropertyAccessor(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public Object get(String property) {
        return map.get(property);
    }

    @Override
    public void set(String property, Object value) {
        map.put(property, value);
    }
}

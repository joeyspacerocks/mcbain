package org.mcbain.binding;

/**
 * Property accessors provide simple, uniform access to an object's properties.
 */

public interface PropertyAccessor {

    public Object get(String property);

    public void set(String property, Object value);
}

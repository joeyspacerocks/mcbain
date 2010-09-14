package org.mcbain.binding;

import org.mcbain.Request;

/**
 * Request parsers are factories that use incoming request parameters to
 * construct instances of some representative class.
 */

public interface RequestParser<T> {

    public T parse(Request request);
}

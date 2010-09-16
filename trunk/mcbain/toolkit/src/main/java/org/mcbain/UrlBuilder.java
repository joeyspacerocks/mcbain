package org.mcbain;

/**
 * Builds a url from a path and a list of parameters.
 */

public interface UrlBuilder {

    /**
     * Builds a path using the supplied path as a template and inserting
     * any supplied parameters. This is typically used to build a url to
     * a route that uses wildcards or pattern matching to identify portions
     * of the url.
     *
     * @param   path        Path to merge parameters with
     * @param   params      Array of zero or more parameters
     * @return  Compiled path
     */

    public String buildPath(String path, Object... params);
}

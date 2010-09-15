/*
 * Copyright 2010 Joe Trewin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mcbain.routes;

import org.mcbain.Request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Router that matches request URIs to paths containing wildcards.
 *
 * Matched portions of the path can be stored as named request
 * parameters.
 *
 * The path is split into sections using the forward-slash character
 * as a delimiter.
 *
 * Each section can be one of the following:
 *
 *  *       Zero or more non-forward-slash characters
 *  #       Zero or more 0-9 digit characters
 *  **      Zero or more sections
 *  other   Characters to be matched exactly
 *
 * Examples:
 *
 *  /path       /path only
 *  /path/*     Paths starting with '/path/' with one other section
 *  /path/**    Paths starting with '/path/' with any number of sections
 * 
 * A section can be pushed into a named request parameter by wrapping
 * it in parenthesis and including the name after a semi-colon:
 *
 *  /path/(*:id)
 *  /path/(leaf:id)
 *  /path/(**:path)/(*:id)
 */

public class WildcardPathRouter implements Router {

    private static final Pattern numeric = Pattern.compile("\\d*");

    private PathNode pathsRoot = new PathNode();
    private RouteHandler defaultHandler = RouteHandler.NONE;

    @Override
    public Router add(String path, RouteHandler handler) {
        PathNode node = pathsRoot.create(path.split("/"), 0);
        node.handler = handler;
        return this;
    }

    @Override
    public Router defaultHandler(RouteHandler handler) {
        defaultHandler = handler;
        return this;
    }

    @Override
    public RouteHandler route(Request request) {
        String path = request.uri();
        final Map<String, String> params = new HashMap<String, String>();

        PathNode node = pathsRoot.traverse(path.split("/"), 0, params);

        if (node != null) {
            try {
                for (String key : params.keySet()) {
                    request.param(key, URLDecoder.decode(params.get(key), "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Encoding 'UTF-8' not available", e);
            }

            return node.handler;
        }

        return defaultHandler;
    }

    @Override
    public String buildPath(String path, Object... params) {
        if (!path.contains("*") && !path.contains("#")) {
            return path;
        }

        StringBuilder result = new StringBuilder();
        int parami = 0;
        
        for (String section : path.substring(1).split("/")) {
            result.append("/");
            if (section.equals("*") || section.equals("**")) {
                result.append(params[parami++]);
            } else {
                result.append(section);
            }
        }

        return result.toString();
    }

    /**
     * Paths are parsed into an internal tree structure to improve matching speed.
     * This inner class represents a single node in the tree, each of which corresponds
     * with a path section.
     */

    private class PathNode {
        private String value = "";
        private String rawValue = "";
        private RouteHandler handler;
        private boolean wildcard;
        private boolean multiWildcard;
        private boolean numericWildcard;
        private String captureName;
        private List<PathNode> children = new ArrayList<PathNode>();
        
        public PathNode create(String[] sections, int offset) {
            if (sections.length == 0) {
                return this;
            }
            
            if (rawValue.equals(sections[offset])) {
                if (offset >= sections.length - 1) {
                    return this;
                }

                for (PathNode node : children) {
                    PathNode found = node.create(sections, offset + 1);
                    if (found != null) {
                        return found;
                    }
                }

                PathNode created = new PathNode();
                created.value(sections[offset + 1]);
                children.add(created);

                return created.create(sections, offset + 1);
            }

            return null;
        }

        public PathNode traverse(String[] sections, int offset, Map<String, String> params) {
            if (sections.length == 0) {
                return this;
            }

            if (matches(this, sections[offset])) {
                if (captureName != null) {
                    params.put(captureName, sections[offset]);
                }

                if (offset >= sections.length - 1) {
                    return this;
                }

                for (PathNode node : children) {
                    PathNode found = node.traverse(sections, offset + 1, params);
                    if (found != null) {
                        return found;
                    }
                }

               if (multiWildcard) {
                    return traverse(sections, offset + 1, params);
                }

                return null;
             }

            return null;
        }

        private boolean matches(PathNode node, String value) {
            return node.wildcard ||
                    (node.numericWildcard && numeric.matcher(value).matches()) ||
                    value.equals(node.value);
        }

        private void value(String rawValue) {
            this.rawValue = rawValue;
            if (rawValue.startsWith("(") && rawValue.endsWith(")")) {
                String[] parts = rawValue.substring(1, rawValue.length() - 1).split(":");
                value = parts[0];
                if (parts.length > 1) captureName = parts[1];

            } else {
                value = rawValue;
            }

            multiWildcard = "**".equals(value);
            wildcard = multiWildcard || "*".equals(value);
            numericWildcard = "#".equals(value);
        }
    }
}

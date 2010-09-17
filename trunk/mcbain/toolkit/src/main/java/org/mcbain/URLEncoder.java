package org.mcbain;//-----------------------------------------------------------------------
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Wrapper for JDK URLEncoder and URLDecoder classes that defaults to UTF-8 and
 * throws unchecked runtime exceptions instead of UnsupportedEncodingException.
 */

public class URLEncoder {

    public static String encode(String value) {
        return encode(value, "UTF-8");
    }

    public static String encode(String value, String charset) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Character set " + charset + " not supported", e);
        }
    }

    public static String decode(String value) {
        return decode(value, "UTF-8");
    }

    public static String decode(String value, String charset) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Character set " + charset + " not supported", e);
        }
    }
}

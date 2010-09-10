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

package org.mcbain.test;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Helper implementation of the HttpServletResponse interface for use in low-level
 * tests.
 */

public class MockHttpServletResponse implements HttpServletResponse {
    private int status;
    private ByteArrayOutputStream outputStream;

    public MockHttpServletResponse() {
        outputStream = new ByteArrayOutputStream();
    }

    @Override
    public void addCookie(Cookie cookie) {
    }

    @Override
    public boolean containsHeader(String s) {
        return false;
    }

    @Override
    public String encodeURL(String s) {
        return null;
    }

    @Override
    public String encodeRedirectURL(String s) {
        return null;
    }

    @Override
    public String encodeUrl(String s) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(String s) {
        return null;
    }

    @Override
    public void sendError(int i, String s) throws IOException {
    }

    @Override
    public void sendError(int i) throws IOException {
        this.status = i;
    }

    @Override
    public void sendRedirect(String s) throws IOException {
    }

    @Override
    public void setDateHeader(String s, long l) {
    }

    @Override
    public void addDateHeader(String s, long l) {
    }

    @Override
    public void setHeader(String s, String s1) {
    }

    @Override
    public void addHeader(String s, String s1) {
    }

    @Override
    public void setIntHeader(String s, int i) {
    }

    @Override
    public void addIntHeader(String s, int i) {
    }

    @Override
    public void setStatus(int code) {
        this.status = code;
    }

    @Override
    public void setStatus(int code, String s) {
        this.status = code;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(outputStream);
    }

    @Override
    public void setCharacterEncoding(String s) {
    }

    @Override
    public void setContentLength(int i) {
    }

    @Override
    public void setContentType(String s) {
    }

    @Override
    public void setBufferSize(int i) {
    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {
    }

    @Override
    public void resetBuffer() {
    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {
    }

    @Override
    public void setLocale(Locale locale) {
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    /**
     * Checks to see if the response has the specified status.
     *
     * @param   code    Expected status code
     * @return  True if the response code is the same
     */

    public boolean hasStatus(int code) {
        return code == status;
    }

    /**
     * Checks to see if the response has the specified content.
     *
     * @param   content     Expected content
     * @return  True if the content is the same
     */
    
    public boolean hasContent(String content) {
        return content.equals(outputStream.toString());
    }
}

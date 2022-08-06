package com.idefav.context.request;

import com.idefav.context.Constants;
import com.idefav.context.Context;
import com.idefav.context.Scope;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * the RequestHolder description.
 *
 * @author wuzishu
 */
public class RequestHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHolder.class);

    public static Request get() {
        return Request.INSTANCE;
    }

    private RequestHolder() {
    }

    public static Scope set(HttpServletRequest request) {
        try {
            Context.current().put(Constants.CONTEXT_KEY_REQUEST, new CachedHttpServletRequest(request));
        }
        catch (Exception e) {
            LOGGER.warn("[RequestContext] Failed to cache request context!", e);
        }
        return RequestHolder::reset;
    }

    public static void reset() {
        Context.current().remove(Constants.CONTEXT_KEY_REQUEST);
    }

    private static Optional<HttpServletRequest> request() {
        Object reqObj = Context.current().get(Constants.CONTEXT_KEY_REQUEST);
        if (reqObj == null) return Optional.empty();
        if (CachedHttpServletRequest.class.isAssignableFrom(reqObj.getClass())) {
            return Optional.of((CachedHttpServletRequest) reqObj);
        }
        return Optional.empty();
    }

    public static class Request implements HttpServletRequest {
        private static final Enumeration<String> EMPTY_ENUMERATION = Collections.enumeration(new ArrayList<>());
        private static final Request INSTANCE = new Request();

        private Request() {
        }

        public String getAuthType() {
            return request().map(HttpServletRequest::getAuthType).orElse(StringUtils.EMPTY);
        }

        @Override
        public Cookie[] getCookies() {
            return request().map(HttpServletRequest::getCookies).orElse(new Cookie[0]);
        }

        @Override
        public long getDateHeader(String name) {
            return request().map(c -> c.getDateHeader(name)).orElse(-1L);
        }

        @Override
        public String getHeader(String name) {
            return request().map(c -> c.getHeader(name)).orElse(StringUtils.EMPTY);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            return request().map(c -> c.getHeaders(name)).orElse(EMPTY_ENUMERATION);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            return request().map(HttpServletRequest::getHeaderNames).orElse(EMPTY_ENUMERATION);
        }

        @Override
        public int getIntHeader(String name) {
            return request().map(c -> c.getIntHeader(name)).orElse(-1);
        }

        @Override
        public String getMethod() {
            return request().map(HttpServletRequest::getMethod).orElse(StringUtils.EMPTY);
        }

        @Override
        public String getPathInfo() {
            return request().map(HttpServletRequest::getPathInfo).orElse(StringUtils.EMPTY);
        }

        @Override
        public String getPathTranslated() {
            return request().map(HttpServletRequest::getPathTranslated).orElse(StringUtils.EMPTY);
        }

        @Override
        public String getContextPath() {
            return request().map(HttpServletRequest::getContextPath).orElse(StringUtils.EMPTY);
        }

        @Override
        public String getQueryString() {
            return request().map(HttpServletRequest::getQueryString).orElse(StringUtils.EMPTY);
        }

        @Override
        public String getRemoteUser() {
            return request().map(HttpServletRequest::getRemoteUser).orElse(StringUtils.EMPTY);
        }

        @Override
        public boolean isUserInRole(String role) {
            return request().map(c -> c.isUserInRole(role)).orElse(false);
        }

        @Override
        public Principal getUserPrincipal() {
            return request().map(HttpServletRequest::getUserPrincipal).orElse(null);
        }

        @Override
        public String getRequestedSessionId() {
            return request().map(HttpServletRequest::getRequestedSessionId).orElse(StringUtils.EMPTY);
        }

        @Override
        public String getRequestURI() {
            return request().map(HttpServletRequest::getRequestURI).orElse(StringUtils.EMPTY);
        }

        @Override
        public StringBuffer getRequestURL() {
            return request().map(HttpServletRequest::getRequestURL).orElse(new StringBuffer());
        }

        @Override
        public String getServletPath() {
            return request().map(HttpServletRequest::getServletPath).orElse(StringUtils.EMPTY);
        }

        @Override
        public HttpSession getSession(boolean b) {
            throw new UnsupportedOperationException();
        }

        @Override
        public HttpSession getSession() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String changeSessionId() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isRequestedSessionIdValid() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isRequestedSessionIdFromCookie() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isRequestedSessionIdFromURL() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isRequestedSessionIdFromUrl() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void login(String s, String s1) throws ServletException {

        }

        @Override
        public void logout() throws ServletException {

        }

        @Override
        public Collection<Part> getParts() throws IOException, ServletException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Part getPart(String s) throws IOException, ServletException {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getAttribute(String name) {
            return request().map(c -> c.getAttribute(name)).orElse(null);
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return request().map(HttpServletRequest::getAttributeNames).orElse(EMPTY_ENUMERATION);
        }

        @Override
        public String getCharacterEncoding() {
            return request().map(HttpServletRequest::getCharacterEncoding).orElse(StringUtils.EMPTY);
        }

        @Override
        public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
            Optional<HttpServletRequest> httpServletRequest = request();
            if (httpServletRequest.isPresent()) {
                httpServletRequest.get().setCharacterEncoding(env);
            }
        }

        @Override
        public int getContentLength() {
            return request().map(HttpServletRequest::getContentLength).orElse(-1);
        }

        @Override
        public long getContentLengthLong() {
            return request().map(HttpServletRequest::getContentLengthLong).orElse(-1L);
        }

        @Override
        public String getContentType() {
            return request().map(HttpServletRequest::getContentType).orElse(StringUtils.EMPTY);
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getParameter(String name) {
            return request().map(c -> c.getParameter(name)).orElse(StringUtils.EMPTY);
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return request().map(HttpServletRequest::getParameterNames).orElse(EMPTY_ENUMERATION);
        }

        @Override
        public String[] getParameterValues(String name) {
            return request().map(c -> c.getParameterValues(name)).orElse(new String[0]);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return request().map(HttpServletRequest::getParameterMap).orElse(new HashMap<>());
        }

        @Override
        public String getProtocol() {
            return request().map(HttpServletRequest::getProtocol).orElse(StringUtils.EMPTY);
        }

        @Override
        public String getScheme() {
            return request().map(HttpServletRequest::getScheme).orElse(StringUtils.EMPTY);
        }

        @Override
        public String getServerName() {
            return request().map(HttpServletRequest::getServerName).orElse(StringUtils.EMPTY);
        }

        @Override
        public int getServerPort() {
            return request().map(HttpServletRequest::getServerPort).orElse(-1);
        }

        @Override
        public BufferedReader getReader() throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getRemoteAddr() {
            return request().map(HttpServletRequest::getRemoteAddr).orElse(StringUtils.EMPTY);
        }

        @Override
        public String getRemoteHost() {
            return request().map(HttpServletRequest::getRemoteHost).orElse(StringUtils.EMPTY);
        }

        @Override
        public void setAttribute(String name, Object o) {
            request().ifPresent(c -> c.setAttribute(name, o));
        }

        @Override
        public void removeAttribute(String name) {
            request().ifPresent(c -> c.removeAttribute(name));
        }

        @Override
        public Locale getLocale() {
            return request().map(HttpServletRequest::getLocale).orElse(null);
        }

        @Override
        public Enumeration<Locale> getLocales() {
            return request().map(HttpServletRequest::getLocales).orElse(Collections.enumeration(new ArrayList<>()));
        }

        @Override
        public boolean isSecure() {
            return request().map(HttpServletRequest::isSecure).orElse(false);
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getRealPath(String s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getRemotePort() {
            return request().map(HttpServletRequest::getRemotePort).orElse(-1);
        }

        @Override
        public String getLocalName() {
            return request().map(HttpServletRequest::getLocalName).orElse(StringUtils.EMPTY);
        }

        @Override
        public String getLocalAddr() {
            return request().map(HttpServletRequest::getLocalAddr).orElse(StringUtils.EMPTY);
        }

        @Override
        public int getLocalPort() {
            return request().map(HttpServletRequest::getLocalPort).orElse(-1);
        }

        @Override
        public ServletContext getServletContext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public AsyncContext startAsync() throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isAsyncStarted() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isAsyncSupported() {
            throw new UnsupportedOperationException();
        }

        @Override
        public AsyncContext getAsyncContext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public DispatcherType getDispatcherType() {
            return request().map(HttpServletRequest::getDispatcherType).orElse(null);
        }
    }
}

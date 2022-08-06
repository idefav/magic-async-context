package com.idefav.context.request;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * the CachedHttpServletRequest description.
 *
 * @author wuzishu
 */
public class CachedHttpServletRequest implements HttpServletRequest {

    private final String authType;
    private final Cookie[] cookies;
    private final Map<String, List<String>> headers;
    private final String method;
    private final String pathInfo;
    private final String pathTranslated;
    private final String contextPath;
    private final String queryString;
    private final String remoteUser;
    private final Principal userPrincipal;
    private final String requestedSessionId;
    private final String requestUri;
    private final StringBuffer requestUrl;
    private final String servletPath;
    private final Map<String, Object> attributes;
    private final String characterEncoding;
    private final int contentLength;
    private final long contentLengthLong;
    private final String contentType;
    private final Map<String, List<String>> paramHashValues = new LinkedHashMap<>();
    private final String protocol;
    private final String scheme;
    private final String serverName;
    private final int serverPort;
    private final String remoteAddr;
    private final String remoteHost;
    private final Locale locale;
    private final Enumeration<Locale> locales;
    private final boolean secure;
    private final int remotePort;
    private final String localName;
    private final String localAddr;
    private final int localPort;
    private final ServletContext servletContext;
    private final DispatcherType dispatcherType;
    private final boolean asyncSupported;
    private final boolean asyncStarted;

    public CachedHttpServletRequest(HttpServletRequest request) {
        this.authType = request.getAuthType();
        this.cookies = request.getCookies();
        this.headers = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                if (StringUtils.isEmpty(headerName)) continue;
                Enumeration<String> values = request.getHeaders(headerName);
                if (values != null) {
                    this.headers.put(StringUtils.lowerCase(headerName), Collections.list(values));
                }
            }
        }

        this.method = request.getMethod();
        this.pathInfo = request.getPathInfo();
        this.pathTranslated = request.getPathTranslated();
        this.contextPath = request.getContextPath();
        this.queryString = request.getQueryString();
        this.remoteUser = request.getRemoteUser();
        this.userPrincipal = request.getUserPrincipal();
        this.requestedSessionId = request.getRequestedSessionId();
        this.requestUri = request.getRequestURI();
        this.requestUrl = request.getRequestURL();
        this.servletPath = request.getServletPath();

        this.attributes = new ConcurrentHashMap<>();
        Enumeration<String> attributeNames = request.getAttributeNames();
        if (attributeNames != null) {
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                Object attributeValue = request.getAttribute(attributeName);
                if (attributeName == null || attributeValue == null) continue;
                this.attributes.put(attributeName, attributeValue);
            }
        }

        this.characterEncoding = request.getCharacterEncoding();
        this.contentLength = request.getContentLength();
        this.contentLengthLong = request.getContentLengthLong();
        this.contentType = request.getContentType();

        Enumeration<String> parameterNames = request.getParameterNames();
        if (parameterNames != null) {
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                if (StringUtils.isEmpty(parameterName)) continue;
                this.paramHashValues.put(parameterName, Arrays.asList(request.getParameterValues(parameterName)));
            }
        }

        this.protocol = request.getProtocol();
        this.scheme = request.getScheme();
        this.serverName = request.getServerName();
        this.serverPort = request.getServerPort();
        this.remoteAddr = request.getRemoteAddr();
        this.remoteHost = request.getRemoteHost();
        this.locale = request.getLocale();
        this.locales = request.getLocales();
        this.secure = request.isSecure();
        this.remotePort = request.getRemotePort();
        this.localName = request.getLocalName();
        this.localAddr = request.getLocalAddr();
        this.localPort = request.getLocalPort();
        this.servletContext = request.getServletContext();

        this.dispatcherType = request.getDispatcherType();
        this.asyncSupported = request.isAsyncSupported();
        this.asyncStarted = request.isAsyncStarted();
    }

    @Override
    public String getAuthType() {
        return this.authType;
    }

    @Override
    public Cookie[] getCookies() {
        return this.cookies;
    }

    @Override
    public long getDateHeader(String name) {
        String value = getHeader(name);
        if (StringUtils.isEmpty(value)) {
            return -1L;
        }
        long result = FastHttpDateFormat.parseDate(value);
        if (result != (-1L)) {
            return result;
        }
        throw new IllegalArgumentException(value);
    }

    @Override
    public String getHeader(String name) {
        List<String> values = headers.get(StringUtils.lowerCase(name));
        if (values == null) return null;
        if (values.size() <= 0) {
            return StringUtils.EMPTY;
        }
        return values.get(0);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> valves = headers.get(StringUtils.lowerCase(name));
        if (CollectionUtils.isEmpty(valves)) {
            valves = new ArrayList<>();
        }
        return Collections.enumeration(valves);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(headers.keySet());
    }

    @Override
    public int getIntHeader(String name) {
        String value = getHeader(name);
        if (StringUtils.isEmpty(value)) {
            return -1;
        }
        return Integer.parseInt(value);
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getPathInfo() {
        return this.pathInfo;
    }

    @Override
    public String getPathTranslated() {
        return this.pathTranslated;
    }

    @Override
    public String getContextPath() {
        return this.contextPath;
    }

    @Override
    public String getQueryString() {
        return this.queryString;
    }

    @Override
    public String getRemoteUser() {
        return this.remoteUser;
    }

    @Override
    public boolean isUserInRole(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Principal getUserPrincipal() {
        return this.userPrincipal;
    }

    @Override
    public String getRequestedSessionId() {
        return this.requestedSessionId;
    }

    @Override
    public String getRequestURI() {
        return this.requestUri;
    }

    @Override
    public StringBuffer getRequestURL() {
        return this.requestUrl;
    }

    @Override
    public String getServletPath() {
        return this.servletPath;
    }

    @Override
    public HttpSession getSession(boolean create) {
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
    public void login(String uesrname, String password) throws ServletException {
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
        return this.attributes.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    @Override
    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    @Override
    public void setCharacterEncoding(String encoding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getContentLength() {
        return this.contentLength;
    }

    @Override
    public long getContentLengthLong() {
        return this.contentLengthLong;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getParameter(String name) {
        List<String> values = paramHashValues.get(name);
        if (values == null) return null;
        if (values.size() <= 0) return StringUtils.EMPTY;
        return values.get(0);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(paramHashValues.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        List<String> values = paramHashValues.get(name);
        if (CollectionUtils.isEmpty(values)) return new String[0];
        return paramHashValues.get(name).toArray(new String[values.size()]);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> result = new HashMap<>();
        Enumeration<String> parameterNames = getParameterNames();
        if (parameterNames != null) {
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                if (StringUtils.isEmpty(parameterName)) continue;
                String[] parameterValues = getParameterValues(parameterName);
                result.put(parameterName, parameterValues);
            }
        }
        return result;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    @Override
    public String getServerName() {
        return this.serverName;
    }

    @Override
    public int getServerPort() {
        return this.serverPort;
    }

    @Override
    public BufferedReader getReader() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    @Override
    public String getRemoteHost() {
        return this.remoteHost;
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return this.locales;
    }

    @Override
    public boolean isSecure() {
        return this.secure;
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
        return this.remotePort;
    }

    @Override
    public String getLocalName() {
        return this.localName;
    }

    @Override
    public String getLocalAddr() {
        return this.localAddr;
    }

    @Override
    public int getLocalPort() {
        return this.localPort;
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        throw new IllegalStateException();
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        throw new IllegalStateException();
    }

    @Override
    public boolean isAsyncStarted() {
        return this.asyncStarted;
    }

    @Override
    public boolean isAsyncSupported() {
        return this.asyncSupported;
    }

    @Override
    public AsyncContext getAsyncContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DispatcherType getDispatcherType() {
        return this.dispatcherType;
    }
}

final class FastHttpDateFormat {
    private static final int CACHE_SIZE = Integer.parseInt(System.getProperty("org.apache.tomcat.util.http.FastHttpDateFormat.CACHE_SIZE", "1000"));
    /**
     * @deprecated
     */
    @Deprecated
    public static final String RFC1123_DATE = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private static final String DATE_RFC5322 = "EEE, dd MMM yyyy HH:mm:ss z";
    private static final String DATE_OBSOLETE_RFC850 = "EEEEEE, dd-MMM-yy HH:mm:ss zzz";
    private static final String DATE_OBSOLETE_ASCTIME = "EEE MMMM d HH:mm:ss yyyy";
    private static final ConcurrentDateFormat FORMAT_RFC5322;
    private static final ConcurrentDateFormat FORMAT_OBSOLETE_RFC850;
    private static final ConcurrentDateFormat FORMAT_OBSOLETE_ASCTIME;
    private static final ConcurrentDateFormat[] httpParseFormats;
    private static volatile long currentDateGenerated;
    private static String currentDate;
    private static final Map<Long, String> formatCache;
    private static final Map<String, Long> parseCache;

    public FastHttpDateFormat() {
    }

    public static final String getCurrentDate() {
        long now = System.currentTimeMillis();
        if (Math.abs(now - currentDateGenerated) > 1000L) {
            currentDate = FORMAT_RFC5322.format(new Date(now));
            currentDateGenerated = now;
        }

        return currentDate;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static final String formatDate(long value, DateFormat threadLocalformat) {
        return formatDate(value);
    }

    public static final String formatDate(long value) {
        Long longValue = value;
        String cachedDate = (String) formatCache.get(longValue);
        if (cachedDate != null) {
            return cachedDate;
        }
        else {
            String newDate = FORMAT_RFC5322.format(new Date(value));
            updateFormatCache(longValue, newDate);
            return newDate;
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static final long parseDate(String value, DateFormat[] threadLocalformats) {
        return parseDate(value);
    }

    public static final long parseDate(String value) {
        Long cachedDate = (Long) parseCache.get(value);
        if (cachedDate != null) {
            return cachedDate;
        }
        else {
            long date = -1L;

            for (int i = 0; date == -1L && i < httpParseFormats.length; ++i) {
                try {
                    date = httpParseFormats[i].parse(value).getTime();
                    updateParseCache(value, date);
                }
                catch (ParseException var6) {
                }
            }

            return date;
        }
    }

    private static void updateFormatCache(Long key, String value) {
        if (value != null) {
            if (formatCache.size() > CACHE_SIZE) {
                formatCache.clear();
            }

            formatCache.put(key, value);
        }
    }

    private static void updateParseCache(String key, Long value) {
        if (value != null) {
            if (parseCache.size() > CACHE_SIZE) {
                parseCache.clear();
            }

            parseCache.put(key, value);
        }
    }

    static {
        TimeZone tz = TimeZone.getTimeZone("GMT");
        FORMAT_RFC5322 = new ConcurrentDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US, tz);
        FORMAT_OBSOLETE_RFC850 = new ConcurrentDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US, tz);
        FORMAT_OBSOLETE_ASCTIME = new ConcurrentDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US, tz);
        httpParseFormats = new ConcurrentDateFormat[] {FORMAT_RFC5322, FORMAT_OBSOLETE_RFC850, FORMAT_OBSOLETE_ASCTIME};
        currentDateGenerated = 0L;
        currentDate = null;
        formatCache = new ConcurrentHashMap(CACHE_SIZE);
        parseCache = new ConcurrentHashMap(CACHE_SIZE);
    }
}

class ConcurrentDateFormat {
    private final String format;
    private final Locale locale;
    private final TimeZone timezone;
    private final Queue<SimpleDateFormat> queue = new ConcurrentLinkedQueue();

    public ConcurrentDateFormat(String format, Locale locale, TimeZone timezone) {
        this.format = format;
        this.locale = locale;
        this.timezone = timezone;
        SimpleDateFormat initial = this.createInstance();
        this.queue.add(initial);
    }

    public String format(Date date) {
        SimpleDateFormat sdf = (SimpleDateFormat) this.queue.poll();
        if (sdf == null) {
            sdf = this.createInstance();
        }

        String result = sdf.format(date);
        this.queue.add(sdf);
        return result;
    }

    public Date parse(String source) throws ParseException {
        SimpleDateFormat sdf = (SimpleDateFormat) this.queue.poll();
        if (sdf == null) {
            sdf = this.createInstance();
        }

        Date result = sdf.parse(source);
        sdf.setTimeZone(this.timezone);
        this.queue.add(sdf);
        return result;
    }

    private SimpleDateFormat createInstance() {
        SimpleDateFormat sdf = new SimpleDateFormat(this.format, this.locale);
        sdf.setTimeZone(this.timezone);
        return sdf;
    }
}

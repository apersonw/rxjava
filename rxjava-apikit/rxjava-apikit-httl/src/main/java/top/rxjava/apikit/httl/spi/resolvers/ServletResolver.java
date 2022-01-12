/*
 * Copyright 2011-2013 HTTL Team.
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
package top.rxjava.apikit.httl.spi.resolvers;

import top.rxjava.apikit.httl.Context;
import top.rxjava.apikit.httl.spi.Resolver;
import top.rxjava.apikit.httl.spi.loaders.ServletLoader;
import top.rxjava.apikit.httl.util.ClassUtils;
import top.rxjava.apikit.httl.util.MapSupport;
import top.rxjava.apikit.httl.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ServletResolver. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ServletResolver implements Resolver, Filter {

    private static final String REQUEST_KEY = "request";

    private static final String RESPONSE_KEY = "response";

    private static final String SESSION_KEY = "session";

    private static final String APPLICATION_KEY = "application";

    private static final String COOKIE_KEY = "cookie";

    private static final String PARAMETER_KEY = "parameter";

    private static final String HEADER_KEY = "header";

    private static final String DEFAULT_CONTENT_TYPE = "text/html";

    private static final String CHARSET_SEPARATOR = "; ";

    private static final String CHARSET_KEY = "charset=";

    private static final ThreadLocal<HttpServletRequest> REQUEST_LOCAL = new ThreadLocal<HttpServletRequest>();

    private static final ThreadLocal<HttpServletResponse> RESPONSE_LOCAL = new ThreadLocal<HttpServletResponse>();

    private static String RESPONSE_ENCODING;

    private static void _setResponseEncoding(String responseEncoding) {
        RESPONSE_ENCODING = responseEncoding;
    }

    public static void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
        setRequest(request);
        setResponse(response);
    }

    public static void removeRequestAndResponse() {
        removeRequest();
        removeResponse();
    }

    public static void removeRequest() {
        REQUEST_LOCAL.remove();
    }

    public static void removeResponse() {
        RESPONSE_LOCAL.remove();
    }

    public static HttpServletRequest getRequest() {
        return REQUEST_LOCAL.get();
    }

    public static void setRequest(HttpServletRequest request) {
        if (request != null) {
            if (ServletLoader.getServletContext() == null
                    && request.getSession() != null
                    && request.getSession().getServletContext() != null) {
                ServletLoader.setServletContext(request.getSession().getServletContext());
            }
            REQUEST_LOCAL.set(request);
        } else {
            REQUEST_LOCAL.remove();
        }
        Context.getContext().put("request", request);
    }

    public static HttpServletResponse getResponse() {
        return RESPONSE_LOCAL.get();
    }

    public static void setResponse(HttpServletResponse response) {
        if (response != null) {
            checkResponseEncoding(response);
            RESPONSE_LOCAL.set(response);
        } else {
            RESPONSE_LOCAL.remove();
        }
        Context.getContext().put("response", response);
    }

    public static HttpServletRequest getAndCheckRequest() {
        return assertNotNull(getRequest());
    }

    public static HttpServletResponse getAndCheckResponse() {
        return assertNotNull(getResponse());
    }

    private static <T> T assertNotNull(T object) {
        if (object == null) {
            throw new IllegalStateException("servletRequest == null. Please add config in your /WEB-INF/web.xml: \n<filter>\n\t<filter-name>"
                    + ServletResolver.class.getSimpleName() + "</filter-name>\n\t<filter-class>" + ServletResolver.class.getName()
                    + "</filter-class>\n</filter>\n<filter-mapping>\n\t<filter-name>" + ServletResolver.class.getSimpleName()
                    + "</filter-name>\n\t<url-pattern>/*</url-pattern>\n</filter-mapping>\n");
        }
        return object;
    }

    private static void checkResponseEncoding(HttpServletResponse response) {
        if (StringUtils.isNotEmpty(RESPONSE_ENCODING)) {
            response.setCharacterEncoding(RESPONSE_ENCODING);
            String contentType = response.getContentType();
            if (StringUtils.isEmpty(contentType)) {
                response.setContentType(DEFAULT_CONTENT_TYPE + CHARSET_SEPARATOR + CHARSET_KEY + RESPONSE_ENCODING);
            } else {
                int i = contentType.indexOf(CHARSET_KEY);
                if (i > 0) {
                    response.setContentType(contentType.substring(0, i + CHARSET_KEY.length()) + RESPONSE_ENCODING);
                } else {
                    response.setContentType(contentType + CHARSET_SEPARATOR + CHARSET_KEY + RESPONSE_ENCODING);
                }
            }
        }
    }

    /**
     * httl.properties: response.encoding=UTF-8
     */
    public void setResponseEncoding(String responseEncoding) {
        _setResponseEncoding(responseEncoding);
    }

    private Object getHeaderValue(HttpServletRequest request, String key) {
        Object value = request.getHeader(key);
        if (value != null) {
            return value;
        }
        return request.getHeader(StringUtils.splitCamelName(key, "-", true));
    }

    private Object getParameterValue(HttpServletRequest request, String key) {
        String[] values = request.getParameterValues(key);
        if (values == null || values.length == 0) {
            return null;
        } else if (values.length == 1) {
            return values[0];
        } else {
            return values;
        }
    }

    private Object getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public Object get(String key) {
        final HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        if (REQUEST_KEY.equals(key)) {
            return request;
        }
        if (RESPONSE_KEY.equals(key)) {
            return getResponse();
        }
        if (SESSION_KEY.equals(key)) {
            return request.getSession();
        }
        if (APPLICATION_KEY.equals(key)) {
            return request.getSession() == null ? null : request.getSession().getServletContext();
        }
        if (COOKIE_KEY.equals(key)) {
            return new MapSupport<String, Object>() {
                @Override
                public Object get(Object key) {
                    return ServletResolver.this.getCookieValue(request, (String) key);
                }
            };
        }
        if (PARAMETER_KEY.equals(key)) {
            return new MapSupport<String, Object>() {
                @Override
                public Object get(Object key) {
                    return ServletResolver.this.getParameterValue(request, (String) key);
                }
            };
        }
        if (HEADER_KEY.equals(key)) {
            return new MapSupport<String, Object>() {
                @Override
                public Object get(Object key) {
                    return ServletResolver.this.getHeaderValue(request, (String) key);
                }
            };
        }
        Object value = ClassUtils.getProperty(request, key);
        if (value != null) {
            if ("contextPath".equals(key) && "/".equals(value)) {
                return ""; // e.g. ${contextPath}/index.html
            }
            return value;
        }
        value = request.getAttribute(key);
        if (value != null) {
            return value;
        }
        value = getParameterValue(request, key);
        if (value != null) {
            return value;
        }
        value = getHeaderValue(request, key);
        if (value != null) {
            return value;
        }
        HttpSession session = request.getSession();
        if (session != null) {
            value = ClassUtils.getProperty(session, key);
            if (value != null) {
                return value;
            }
            value = session.getAttribute(key);
            if (value != null) {
                return value;
            }
        }
        value = getCookieValue(request, key);
        if (value != null) {
            return value;
        }
        if (session != null) {
            ServletContext servletContext = session.getServletContext();
            if (servletContext != null) {
                value = ClassUtils.getProperty(servletContext, key);
                if (value != null) {
                    return value;
                }
                value = servletContext.getAttribute(key);
                if (value != null) {
                    return value;
                }
            }
        }
        return value;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        setRequestAndResponse((HttpServletRequest) request, (HttpServletResponse) response);
        try {
            chain.doFilter(request, response);
        } finally {
            removeRequestAndResponse();
        }
    }

}
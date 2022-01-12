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
package top.rxjava.apikit.httl.spi.loaders;

import top.rxjava.apikit.httl.Resource;
import top.rxjava.apikit.httl.spi.Loader;
import top.rxjava.apikit.httl.spi.loaders.resources.ServletResource;
import top.rxjava.apikit.httl.util.UrlUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * ServletLoader. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see top.rxjava.apikit.httl.spi.engines.DefaultEngine#setLoader(Loader)
 */
public class ServletLoader extends AbstractLoader implements ServletContextListener {

    private static ServletContext SERVLET_CONTEXT;

    public static ServletContext getAndCheckServletContext() {
        if (SERVLET_CONTEXT == null) {
            throw new IllegalStateException("servletContext == null. Please add config in your /WEB-INF/web.xml: " +
                    "\n<listener>\n\t<listener-class>" + ServletLoader.class.getName() + "</listener-class>\n</listener>\n");
        }
        return SERVLET_CONTEXT;
    }

    public static ServletContext getServletContext() {
        return SERVLET_CONTEXT;
    }

    public static void setServletContext(ServletContext servletContext) {
        SERVLET_CONTEXT = servletContext;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        setServletContext(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        setServletContext(null);
    }

    @Override
    public List<String> doList(String directory, String suffix) throws IOException {
        String path = getAndCheckServletContext().getRealPath(directory);
        if (path == null || path.length() == 0) {
            return null;
        }
        return UrlUtils.listFile(new File(path), suffix);
    }

    @Override
    protected Resource doLoad(String name, Locale locale, String encoding, String path) throws IOException {
        return new ServletResource(getEngine(), name, locale, encoding, path, getAndCheckServletContext());
    }

    @Override
    public boolean doExists(String name, Locale locale, String path) throws IOException {
        return SERVLET_CONTEXT != null && SERVLET_CONTEXT.getResource(path) != null;
    }

}
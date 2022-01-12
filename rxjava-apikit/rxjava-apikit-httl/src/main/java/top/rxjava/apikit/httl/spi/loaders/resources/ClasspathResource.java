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
package top.rxjava.apikit.httl.spi.loaders.resources;

import top.rxjava.apikit.httl.Engine;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

/**
 * ClasspathResource. (SPI, Prototype, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see top.rxjava.apikit.httl.spi.loaders.ClasspathLoader#load(String, Locale, String)
 */
public class ClasspathResource extends InputStreamResource {

    private static final long serialVersionUID = 2499229996487593996L;

    public ClasspathResource(Engine engine, String name, Locale locale, String encoding, String path) {
        super(engine, name, locale, encoding, path);
    }

    public InputStream openStream() throws IOException {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(getPath());
    }

    @Override
    protected URL getUrl() {
        return Thread.currentThread().getContextClassLoader().getResource(getPath());
    }

}
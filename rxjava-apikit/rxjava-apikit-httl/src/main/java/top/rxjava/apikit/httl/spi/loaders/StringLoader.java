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

import top.rxjava.apikit.httl.Engine;
import top.rxjava.apikit.httl.Resource;
import top.rxjava.apikit.httl.spi.Loader;
import top.rxjava.apikit.httl.spi.loaders.resources.StringResource;
import top.rxjava.apikit.httl.util.LocaleUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StringLoader. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see top.rxjava.apikit.httl.spi.engines.DefaultEngine#setLoader(Loader)
 * @see top.rxjava.apikit.httl.spi.engines.DefaultEngine#parseTemplate(String)
 */
public class StringLoader implements Loader {

    private static final String STRING_ENCODING = "UTF-8";

    private final Map<String, StringResource> templates = new ConcurrentHashMap<String, StringResource>();

    private Engine engine;

    public StringLoader() {
    }

    public StringLoader(Engine engine) {
        this.engine = engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void add(String name, String source) {
        add(name, null, source);
    }

    public void add(String name, Locale locale, String source) {
        templates.put(getTemplateKey(name, locale), new StringResource(engine, name, locale, STRING_ENCODING, System.currentTimeMillis(), source));
    }

    public void remove(String name) {
        remove(name, null);
    }

    public void remove(String name, Locale locale) {
        templates.remove(getTemplateKey(name, locale));
    }

    public void clear() {
        templates.clear();
    }

    @Override
    public List<String> list(String suffix) throws IOException {
        return new ArrayList<String>(templates.keySet());
    }

    @Override
    public Resource load(String name, Locale locale, String encoding) throws IOException {
        StringResource resource = templates.get(getTemplateKey(name, locale));
        if (resource == null) {
            throw new FileNotFoundException("Not found template " + name);
        }
        return resource;
    }

    @Override
    public boolean exists(String name, Locale locale) {
        return templates.containsKey(getTemplateKey(name, locale));
    }

    private String getTemplateKey(String name, Locale locale) {
        return LocaleUtils.appendLocale(name, locale);
    }

}
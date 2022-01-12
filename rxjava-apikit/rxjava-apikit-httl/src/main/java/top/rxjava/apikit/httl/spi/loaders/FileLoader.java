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
import top.rxjava.apikit.httl.spi.loaders.resources.FileResource;
import top.rxjava.apikit.httl.util.UrlUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * FileLoader. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see top.rxjava.apikit.httl.spi.engines.DefaultEngine#setLoader(Loader)
 */
public class FileLoader extends AbstractLoader {

    @Override
    public List<String> doList(String directory, String suffix) throws IOException {
        File file = new File(directory);
        return UrlUtils.listFile(file, suffix);
    }

    @Override
    protected Resource doLoad(String name, Locale locale, String encoding, String path) throws IOException {
        return new FileResource(getEngine(), name, locale, encoding, path);
    }

    @Override
    public boolean doExists(String name, Locale locale, String path) throws IOException {
        return new File(path).exists();
    }

}
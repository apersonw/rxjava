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
package top.rxjava.apikit.httl.spi.converters;

import top.rxjava.apikit.httl.spi.Converter;
import top.rxjava.apikit.httl.util.UnsafeStringWriter;

import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.util.Map;

/**
 * StringBuilderOutConverter. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see top.rxjava.apikit.httl.spi.translators.CompiledTranslator#setOutConverter(Converter)
 * @see top.rxjava.apikit.httl.spi.translators.InterpretedTranslator#setOutConverter(Converter)
 */
public class StringBuilderOutConverter implements Converter<StringBuilder, Writer> {

    public Writer convert(StringBuilder value, Map<String, Class<?>> types) throws IOException, ParseException {
        return new UnsafeStringWriter(value);
    }

}
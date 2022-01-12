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
package top.rxjava.apikit.httl.spi.codecs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import top.rxjava.apikit.httl.util.UnsafeByteArrayInputStream;
import top.rxjava.apikit.httl.util.UnsafeByteArrayOutputStream;

import java.text.ParseException;

/**
 * Xstream Codec. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class XstreamCodec extends AbstractXmlCodec {

    private static XStream XSTREAM = new XStream(new Xpp3Driver());

    public static XStream getXStream() {
        return XSTREAM;
    }

    public static void setDriver(HierarchicalStreamDriver driver) {
        XSTREAM = new XStream(driver);
    }

    @Override
    public String toString(String key, Object value) {
        return XSTREAM.toXML(value);
    }

    @Override
    public byte[] toBytes(String key, Object value) {
        UnsafeByteArrayOutputStream out = new UnsafeByteArrayOutputStream();
        XSTREAM.toXML(value, out);
        return out.toByteArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T valueOf(String str, Class<T> type) throws ParseException {
        if (str == null) {
            return null;
        }
        if (type == null) {
            return (T) XSTREAM.fromXML(str);
        }
        try {
            return (T) XSTREAM.fromXML(str, type.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T valueOf(byte[] str, Class<T> type) throws ParseException {
        if (str == null) {
            return null;
        }
        if (type == null) {
            return (T) XSTREAM.fromXML(new UnsafeByteArrayInputStream(str));
        }
        try {
            return (T) XSTREAM.fromXML(new UnsafeByteArrayInputStream(str), type.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
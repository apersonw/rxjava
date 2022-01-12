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

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;

/**
 * Xbean Codec. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class XmlCodec extends AbstractXmlCodec {

    public String toString(String key, Object value) {
        return toString(toBytes(key, value));
    }

    public byte[] toBytes(String key, Object value) {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        XMLEncoder xe = new XMLEncoder(bo);
        try {
            xe.writeObject(value);
            xe.flush();
        } finally {
            xe.close();
        }
        return bo.toByteArray();
    }

    @Override
    public <T> T valueOf(String str, Class<T> type) throws ParseException {
        return valueOf(toBytes(str), type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T valueOf(byte[] str, Class<T> type) throws ParseException {
        if (str == null) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(str);
        XMLDecoder xd = new XMLDecoder(bi);
        try {
            return (T) xd.readObject();
        } finally {
            xd.close();
        }
    }

}
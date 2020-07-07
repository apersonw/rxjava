package org.rxjava.third.weixin.common.util.xml;

import com.thoughtworks.xstream.converters.basic.StringConverter;

/**
 * CDATA 内容转换器，加上CDATA标签.
 */
public class XStreamCDataConverter extends StringConverter {

    @Override
    public String toString(Object obj) {
        return "<![CDATA[" + super.toString(obj) + "]]>";
    }

}

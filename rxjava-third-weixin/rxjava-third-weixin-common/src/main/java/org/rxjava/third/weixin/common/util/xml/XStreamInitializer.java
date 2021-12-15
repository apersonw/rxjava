package top.rxjava.third.weixin.common.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.*;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.WildcardTypePermission;

import java.io.Writer;

public class XStreamInitializer {
    private static final XppDriver XPP_DRIVER = new XppDriver() {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out, getNameCoder()) {
                private static final String PREFIX_CDATA = "<![CDATA[";
                private static final String SUFFIX_CDATA = "]]>";
                private static final String PREFIX_MEDIA_ID = "<MediaId>";
                private static final String SUFFIX_MEDIA_ID = "</MediaId>";

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (text.startsWith(PREFIX_CDATA) && text.endsWith(SUFFIX_CDATA)) {
                        writer.write(text);
                    } else if (text.startsWith(PREFIX_MEDIA_ID) && text.endsWith(SUFFIX_MEDIA_ID)) {
                        writer.write(text);
                    } else {
                        super.writeText(writer, text);
                    }

                }

                @Override
                public String encodeNode(String name) {
                    //防止将_转换成__
                    return name;
                }
            };
        }
    };

    public static XStream getInstance() {
        XStream xstream = new XStream(new PureJavaReflectionProvider(), XPP_DRIVER) {
            // only register the converters we need; other converters generate a private access warning in the console on Java9+...
            @Override
            protected void setupConverters() {
                registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);
                registerConverter(new IntConverter(), PRIORITY_NORMAL);
                registerConverter(new FloatConverter(), PRIORITY_NORMAL);
                registerConverter(new DoubleConverter(), PRIORITY_NORMAL);
                registerConverter(new LongConverter(), PRIORITY_NORMAL);
                registerConverter(new ShortConverter(), PRIORITY_NORMAL);
                registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
                registerConverter(new ByteConverter(), PRIORITY_NORMAL);
                registerConverter(new StringConverter(), PRIORITY_NORMAL);
                registerConverter(new DateConverter(), PRIORITY_NORMAL);
                registerConverter(new CollectionConverter(getMapper()), PRIORITY_NORMAL);
                registerConverter(new ReflectionConverter(getMapper(), getReflectionProvider()), PRIORITY_VERY_LOW);
            }
        };
        xstream.ignoreUnknownElements();
        xstream.setMode(XStream.NO_REFERENCES);
        XStream.setupDefaultSecurity(xstream);
        xstream.autodetectAnnotations(true);

        // setup proper security by limiting which classes can be loaded by XStream
        xstream.addPermission(NoTypePermission.NONE);
        xstream.addPermission(new WildcardTypePermission(new String[]{
                "top.rxjava.third.weixin.**", "top.rxjava.third.weixin.**", "top.rxjava.third.weixin.**"
        }));
        xstream.setClassLoader(Thread.currentThread().getContextClassLoader());
        return xstream;
    }

}

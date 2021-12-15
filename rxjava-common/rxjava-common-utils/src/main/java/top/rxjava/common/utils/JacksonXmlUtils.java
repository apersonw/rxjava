package top.rxjava.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public final class JacksonXmlUtils {
    private static final JacksonXmlModule module = new JacksonXmlModule();
    private static final ObjectMapper mapper;

    public JacksonXmlUtils() {
    }


    public static final String baseSerialize(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (IOException var2) {
            throw new RuntimeException(var2.getMessage(), var2);
        }
    }

    public static final <T> T baseDeserialize(String xml, Class<T> valueType) {
        try {
            return mapper.readValue(xml, valueType);
        } catch (IOException var3) {
            throw new RuntimeException(var3.getMessage(), var3);
        }
    }

    public static final <T> T baseDeserialize(String xml, TypeReference<T> valueTypeRef) {
        try {
            return mapper.readValue(xml, valueTypeRef);
        } catch (IOException var3) {
            throw new RuntimeException(var3.getMessage(), var3);
        }
    }

    public static final <T> T baseDeserialize(File file, TypeReference<T> valueTypeRef) {
        try {
            return mapper.readValue(file, valueTypeRef);
        } catch (IOException var3) {
            throw new RuntimeException(var3.getMessage(), var3);
        }
    }

    static {
        mapper = new XmlMapper(module);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}

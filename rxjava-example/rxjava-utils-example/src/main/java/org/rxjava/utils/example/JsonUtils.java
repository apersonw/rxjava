package org.rxjava.utils.example;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * JsonUtils线程安全的单例
 */
public class JsonUtils {
    private static final ConcurrentMap<String, VolatileReference<JsonUtils>> JSONUTILLIST = new ConcurrentHashMap<>();

    public static JsonUtils getJsonutils() throws ClassNotFoundException {
        VolatileReference<JsonUtils> reference = JSONUTILLIST.get("default");

        if (reference == null) {
            // new
            reference = new VolatileReference<>();
            VolatileReference<JsonUtils> old = JSONUTILLIST.putIfAbsent("default", reference);
            // check old
            if (old != null) {
                reference = old;
            }
        }

        JsonUtils jsonUtils = reference.getValue();

        if (jsonUtils == null) {
            synchronized (reference) {
                jsonUtils = reference.getValue();
                // double check
                if (jsonUtils == null) {
                    // get from factory, it't slowly
                    jsonUtils = UtilsBeanFactory.createBean(JsonUtils.class, new Properties());
                    reference.setValue(jsonUtils);
                }
            }
        }
        return jsonUtils;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        JsonUtils jsonutils = JsonUtils.getJsonutils();
        System.out.println(jsonutils);
    }

}

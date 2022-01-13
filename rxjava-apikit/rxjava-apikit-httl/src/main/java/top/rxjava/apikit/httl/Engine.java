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
package top.rxjava.apikit.httl;

import top.rxjava.apikit.httl.util.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Engine. (API, Singleton, Immutable, ThreadSafe)
 * <p/>
 * <pre>
 * Engine engine = Engine.getEngine();
 * </pre>
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see top.rxjava.apikit.httl.Context#getEngine()
 * @see top.rxjava.apikit.httl.Template#getEngine()
 * @see top.rxjava.apikit.httl.Resource#getEngine()
 * @see top.rxjava.apikit.httl.spi.engines.DefaultEngine
 */
public abstract class Engine {

    // Built-in configuration name
    private static final String HTTL_DEFAULT_PROPERTIES = "httl-default.properties";

    // User configuration name
    private static final String HTTL_PROPERTIES = "httl.properties";

    // HTTL configuration prefix
    private static final String HTTL_PREFIX = "httl-";

    // HTTL configuration key prefix
    private static final String HTTL_KEY_PREFIX = "httl.";

    // HTTL configuration suffix
    private static final String PROPERTIES_SUFFIX = ".properties";

    // The modes configuration key
    private static final String MODES_KEY = "modes";

    // The engine name configuration key
    private static final String ENGINE_NAME = "engine.name";

    // The engine singletons cache
    private static final ConcurrentMap<String, VolatileReference<Engine>> ENGINES = new ConcurrentHashMap<String, VolatileReference<Engine>>();

    /**
     * Get template engine singleton.
     *
     * @return template engine
     */
    public static Engine getEngine() {
        return getEngine(null, new Properties());
    }

    /**
     * Get template engine singleton.
     *
     * @param configPath - config path
     * @return template engine
     */
    public static Engine getEngine(String configPath) {
        return getEngine(configPath, null);
    }

    /**
     * Get template engine singleton.
     *
     * @param configProperties - config properties
     * @return template engine
     * @throws FileNotFoundException
     */
    public static Engine getEngine(Properties configProperties) {
        return getEngine("", configProperties);
    }

    /**
     * Get template engine singleton.
     *
     * @param configPath       - config path
     * @param configProperties - config properties
     * @return template engine
     */
    public static Engine getEngine(String configPath, Properties configProperties) {
        if (StringUtils.isEmpty(configPath)) {
            configPath = HTTL_PROPERTIES;
        }
        VolatileReference<Engine> reference = ENGINES.get(configPath);
        if (reference == null) {
            reference = new VolatileReference<Engine>(); // quickly
            VolatileReference<Engine> old = ENGINES.putIfAbsent(configPath, reference);
            if (old != null) { // duplicate
                reference = old;
            }
        }
        Engine engine = reference.get();
        if (engine == null) {
            synchronized (reference) { // reference lock
                engine = reference.get();
                if (engine == null) { // double check
                    engine = BeanFactory.createBean(Engine.class, initProperties(configPath, configProperties)); // slowly
                    reference.set(engine);
                }
            }
        }
        assert (engine != null);
        return engine;
    }

    /*
     * Mode and System Configuration
     *
     * @author Li Ding (oldratlee AT gmail DOT com)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Properties initProperties(String configPath, Properties configProperties) {
        Map<String, String> systemProperties = ConfigUtils.filterWithPrefix(HTTL_KEY_PREFIX, (Map) System.getProperties(), false);
        Map<String, String> systemEnv = ConfigUtils.filterWithPrefix(HTTL_KEY_PREFIX, System.getenv(), true);
        Properties properties = ConfigUtils.mergeProperties(HTTL_DEFAULT_PROPERTIES, configPath, configProperties, systemProperties, systemEnv);
        String[] modes = StringUtils.splitByComma(properties.getProperty(MODES_KEY));
        if (CollectionUtils.isNotEmpty(modes)) {
            Object[] configs = new Object[modes.length + 5];
            configs[0] = HTTL_DEFAULT_PROPERTIES;
            for (int i = 0; i < modes.length; i++) {
                configs[i + 1] = HTTL_PREFIX + modes[i] + PROPERTIES_SUFFIX;
            }
            configs[modes.length + 1] = configPath;
            configs[modes.length + 2] = configProperties;
            configs[modes.length + 3] = systemProperties;
            configs[modes.length + 4] = systemEnv;
            properties = ConfigUtils.mergeProperties(configs);
        }
        properties.setProperty(ENGINE_NAME, configPath);
        return properties;
    }

    /**
     * Get the engine config name.
     *
     * @return config name
     */
    public abstract String getName();

    /**
     * Get the engine version.
     *
     * @return engine version
     */
    public String getVersion() {
        return Version.getVersion(getClass());
    }

    /**
     * Get config value.
     *
     * @param key - config key
     * @return config value
     * @see #getEngine()
     */
    public Object getProperty(String key) {
        return getProperty(key, Object.class);
    }

    /**
     * Get config value.
     *
     * @param key          - config key
     * @param defaultValue - default value
     * @return config value
     * @see #getEngine()
     */
    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key, String.class);
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

    /**
     * Get config value.
     *
     * @param key          - config key
     * @param defaultValue - default value
     * @return config value
     * @see #getEngine()
     */
    public String[] getProperty(String key, String[] defaultValue) {
        String value = getProperty(key, String.class);
        return StringUtils.isEmpty(value) ? defaultValue : StringUtils.splitByComma(value);
    }

    /**
     * Get config int value.
     *
     * @param key          - config key
     * @param defaultValue - default int value
     * @return config int value
     * @see #getEngine()
     */
    public int getProperty(String key, int defaultValue) {
        String value = getProperty(key, String.class);
        return StringUtils.isEmpty(value) ? defaultValue : Integer.parseInt(value);
    }

    /**
     * Get config boolean value.
     *
     * @param key          - config key
     * @param defaultValue - default boolean value
     * @return config boolean value
     * @see #getEngine()
     */
    public boolean getProperty(String key, boolean defaultValue) {
        String value = getProperty(key, String.class);
        return StringUtils.isEmpty(value) ? defaultValue : Boolean.parseBoolean(value);
    }

    /**
     * Get config instantiated value.
     *
     * @param key - config key
     * @param cls - config value type
     * @return config value
     * @see #getEngine()
     */
    public abstract <T> T getProperty(String key, Class<T> cls);

    /**
     * Get template resource.
     *
     * @param name - template name
     * @return template resource
     * @throws IOException - If an I/O error occurs
     * @see #getEngine()
     */
    public Resource getResource(String name) throws IOException {
        return getResource(name, null, null);
    }

    /**
     * Get template resource.
     *
     * @param name     - template name
     * @param encoding - template encoding
     * @return template resource
     * @throws IOException - If an I/O error occurs
     * @see #getEngine()
     */
    public Resource getResource(String name, String encoding) throws IOException {
        return getResource(name, null, encoding);
    }

    /**
     * Get template resource.
     *
     * @param name   - template name
     * @param locale - template locale
     * @return template resource
     * @throws IOException - If an I/O error occurs
     * @see #getEngine()
     */
    public Resource getResource(String name, Locale locale) throws IOException {
        return getResource(name, locale, null);
    }

    /**
     * Get template resource.
     *
     * @param name     - template name
     * @param locale   - template locale
     * @param encoding - template encoding
     * @return template resource
     * @throws IOException - If an I/O error occurs
     * @see #getEngine()
     */
    public abstract Resource getResource(String name, Locale locale, String encoding) throws IOException;

    /**
     * Tests whether the resource denoted by this abstract pathname exists.
     *
     * @param name - template name
     * @return exists
     * @see #getEngine()
     */
    public boolean hasResource(String name) {
        return hasResource(name, null);
    }

    /**
     * Tests whether the resource denoted by this abstract pathname exists.
     *
     * @param name   - template name
     * @param locale - template locale
     * @return exists
     * @see #getEngine()
     */
    public abstract boolean hasResource(String name, Locale locale);

    /**
     * Get template.
     *
     * @param name - template name
     * @return template instance
     * @throws IOException    - If an I/O error occurs
     * @throws ParseException - If the template cannot be parsed
     * @see #getEngine()
     */
    public Template getTemplate(String name) throws IOException, ParseException {
        return getTemplate(name, null, null, null);
    }

    /**
     * Get template.
     *
     * @param name     - template name
     * @param encoding - template encoding
     * @return template instance
     * @throws IOException    - If an I/O error occurs
     * @throws ParseException - If the template cannot be parsed
     * @see #getEngine()
     */
    public Template getTemplate(String name, String encoding) throws IOException, ParseException {
        return getTemplate(name, null, encoding, null);
    }

    /**
     * Get template.
     *
     * @param name   - template name
     * @param locale - template locale
     * @return template instance
     * @throws IOException    - If an I/O error occurs
     * @throws ParseException - If the template cannot be parsed
     * @see #getEngine()
     */
    public Template getTemplate(String name, Locale locale) throws IOException, ParseException {
        return getTemplate(name, locale, null, null);
    }

    /**
     * Get template.
     *
     * @param name - template name
     * @return template instance
     * @throws IOException    - If an I/O error occurs
     * @throws ParseException - If the template cannot be parsed
     * @see #getEngine()
     */
    public Template getTemplate(String name, Object args) throws IOException, ParseException {
        if (args instanceof String)
            return getTemplate(name, (String) args);
        if (args instanceof Locale)
            return getTemplate(name, (Locale) args);
        return getTemplate(name, null, null, args);
    }

    /**
     * Get template.
     *
     * @param name     - template name
     * @param encoding - template encoding
     * @return template instance
     * @throws IOException    - If an I/O error occurs
     * @throws ParseException - If the template cannot be parsed
     * @see #getEngine()
     */
    public Template getTemplate(String name, String encoding, Object args) throws IOException, ParseException {
        return getTemplate(name, null, encoding, args);
    }

    /**
     * Get template.
     *
     * @param name     - template name
     * @param locale   - template locale
     * @param encoding - template encoding
     * @return template instance
     * @throws IOException    - If an I/O error occurs
     * @throws ParseException - If the template cannot be parsed
     * @see #getEngine()
     */
    public Template getTemplate(String name, Locale locale, String encoding) throws IOException, ParseException {
        return getTemplate(name, locale, encoding, null);
    }

    /**
     * Get template.
     *
     * @param name   - template name
     * @param locale - template locale
     * @return template instance
     * @throws IOException    - If an I/O error occurs
     * @throws ParseException - If the template cannot be parsed
     * @see #getEngine()
     */
    public Template getTemplate(String name, Locale locale, Object args) throws IOException, ParseException {
        if (args instanceof String)
            return getTemplate(name, locale, (String) args);
        return getTemplate(name, locale, null, args);
    }

    /**
     * Get template.
     *
     * @param name     - template name
     * @param locale   - template locale
     * @param encoding - template encoding
     * @return template instance
     * @throws IOException    - If an I/O error occurs
     * @throws ParseException - If the template cannot be parsed
     * @see #getEngine()
     */
    public abstract Template getTemplate(String name, Locale locale, String encoding, Object args) throws IOException, ParseException;

    /**
     * Parse string template.
     *
     * @param source - template source
     * @return template instance
     * @throws ParseException - If the template cannot be parsed
     * @see #getEngine()
     */
    public Template parseTemplate(String source) throws ParseException {
        return parseTemplate(source, null);
    }

    /**
     * Parse string template.
     *
     * @param source - template source
     * @param args   - template render args
     * @return template instance
     * @throws ParseException - If the template cannot be parsed
     * @see #getEngine()
     */
    public abstract Template parseTemplate(String source, Object args) throws ParseException;

    /**
     * Create context map.
     *
     * @return context map
     */
    public abstract Map<String, Object> createContext(final Map<String, Object> parent, Map<String, Object> current);

}
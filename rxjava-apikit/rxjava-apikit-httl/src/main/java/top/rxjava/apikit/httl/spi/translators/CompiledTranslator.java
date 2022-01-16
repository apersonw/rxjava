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
package top.rxjava.apikit.httl.spi.translators;

import top.rxjava.apikit.httl.Engine;
import top.rxjava.apikit.httl.Node;
import top.rxjava.apikit.httl.Resource;
import top.rxjava.apikit.httl.Template;
import top.rxjava.apikit.httl.spi.Compiler;
import top.rxjava.apikit.httl.spi.*;
import top.rxjava.apikit.httl.spi.Formatter;
import top.rxjava.apikit.httl.spi.translators.templates.AdaptiveTemplate;
import top.rxjava.apikit.httl.spi.translators.templates.CompiledTemplate;
import top.rxjava.apikit.httl.spi.translators.templates.CompiledVisitor;
import top.rxjava.apikit.httl.util.ClassUtils;
import top.rxjava.apikit.httl.util.StringSequence;
import top.rxjava.apikit.httl.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CompiledTranslator. (SPI, Singleton, ThreadSafe)
 *
 * @author @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see top.rxjava.apikit.httl.spi.engines.DefaultEngine#setTranslator(Translator)
 */
public class CompiledTranslator implements Translator {

    private static final String TEMPLATE_CLASS_PREFIX = CompiledTemplate.class.getPackage().getName() + ".Template_";
    private static final String HTTL_DEFAULT = "httl.properties";
    private static final String HTTL_PREFIX = "httl-";
    private static final String PROPERTIES_SUFFIX = ".properties";
    private final Map<String, Template> importMacroTemplates = new ConcurrentHashMap<String, Template>();
    private final Map<Class<?>, Object> functions = new ConcurrentHashMap<Class<?>, Object>();
    private final List<StringSequence> sequences = new CopyOnWriteArrayList<StringSequence>();
    private String[] forVariable;
    private String filterVariable;
    private String formatterVariable;
    private String defaultFilterVariable;
    private String defaultFormatterVariable;
    private Engine engine;
    private Compiler compiler;
    private Interceptor interceptor;
    private Logger logger;
    private Switcher<Filter> textFilterSwitcher;
    private Switcher<Filter> valueFilterSwitcher;
    private Switcher<Formatter<Object>> formatterSwitcher;
    private Filter textFilter;
    private Filter valueFilter;
    private Converter<Object, Object> mapConverter;
    private Converter<Object, Object> outConverter;
    private Formatter<Object> formatter;
    private String[] importMacros;
    private String[] importPackages;
    private Set<String> importPackageSet;
    private String[] importVariables;
    private Map<String, Class<?>> importTypes;
    private boolean isOutputStream;
    private boolean isOutputWriter;
    private boolean sourceInClass;
    private boolean textInClass;
    private String outputEncoding;
    private Class<?> defaultVariableType;
    private String engineName;
    private String[] importSizers;
    private String[] importGetters;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * httl.properties: import.sizers=size,length,getSize,getLength
     */
    public void setImportSizers(String[] importSizers) {
        this.importSizers = importSizers;
    }

    /**
     * httl.properties: import.getters=get,getProperty,getAttribute
     */
    public void setImportGetters(String[] importGetters) {
        this.importGetters = importGetters;
    }

    /**
     * httl.properties: default.variable.type=java.lang.Object
     */
    public void setDefaultVariableType(String defaultVariableType) {
        this.defaultVariableType = ClassUtils.forName(defaultVariableType);
    }

    /**
     * httl.properties: import.macros=common.httl
     */
    public void setImportMacros(String[] importMacros) {
        this.importMacros = importMacros;
    }

    /**
     * httl.properties: output.encoding=UTF-8
     */
    public void setOutputEncoding(String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }

    /**
     * httl.properties: engine=httl.spi.engines.DefaultEngine
     */
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    /**
     * httl.properties: engine.name=httl.properties
     */
    public void setEngineName(String name) {
        if (HTTL_DEFAULT.equals(name)) {
            name = "";
        } else {
            if (name.startsWith(HTTL_PREFIX)) {
                name = name.substring(HTTL_PREFIX.length());
            }
            if (name.endsWith(PROPERTIES_SUFFIX)) {
                name = name.substring(0, name.length() - PROPERTIES_SUFFIX.length());
            }
        }
        this.engineName = name;
    }

    /**
     * httl.properties: compiler=httl.spi.compilers.JdkCompiler
     */
    public void setCompiler(Compiler compiler) {
        this.compiler = compiler;
    }

    /**
     * httl.properties: interceptors=httl.spi.interceptors.ExtendsInterceptor
     */
    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    /**
     * httl.properties: text.filter.switchers=httl.spi.switchers.JavaScriptFilterSwitcher
     */
    public void setTextFilterSwitcher(Switcher<Filter> textFilterSwitcher) {
        this.textFilterSwitcher = textFilterSwitcher;
    }

    /**
     * httl.properties: value.filter.switchers=httl.spi.switchers.JavaScriptFilterSwitcher
     */
    public void setValueFilterSwitcher(Switcher<Filter> valueFilterSwitcher) {
        this.valueFilterSwitcher = valueFilterSwitcher;
    }

    /**
     * httl.properties: formatter.switchers=httl.spi.switchers.NumberFormatterSwitcher
     */
    public void setFormatterSwitcher(Switcher<Formatter<Object>> formatterSwitcher) {
        this.formatterSwitcher = formatterSwitcher;
    }

    /**
     * httl.properties: text.filters=httl.spi.filters.CompressBlankFilter
     */
    public void setTextFilter(Filter filter) {
        this.textFilter = filter;
    }

    /**
     * httl.properties: value.filters=httl.spi.filters.EscapeXmlFilter
     */
    public void setValueFilter(Filter filter) {
        this.valueFilter = filter;
    }

    /**
     * httl.properties: map.converters=httl.spi.converters.BeanMapConverter
     */
    public void setMapConverter(Converter<Object, Object> mapConverter) {
        this.mapConverter = mapConverter;
    }

    /**
     * httl.properties: out.converters=httl.spi.converters.ResponseOutConverter
     */
    public void setOutConverter(Converter<Object, Object> outConverter) {
        this.outConverter = outConverter;
    }

    /**
     * httl.properties: formatters=httl.spi.formatters.DateFormatter
     */
    public void setFormatter(Formatter<Object> formatter) {
        this.formatter = formatter;
    }

    /**
     * httl.properties: output.stream=true
     */
    public void setOutputStream(boolean outputStream) {
        this.isOutputStream = outputStream;
    }

    /**
     * httl.properties: output.writer=true
     */
    public void setOutputWriter(boolean outputWriter) {
        this.isOutputWriter = outputWriter;
    }

    /**
     * httl.properties: source.in.class=false
     */
    public void setSourceInClass(boolean sourceInClass) {
        this.sourceInClass = sourceInClass;
    }

    /**
     * httl.properties: text.in.class=false
     */
    public void setTextInClass(boolean textInClass) {
        this.textInClass = textInClass;
    }

    /**
     * httl.properties: for.variable=for
     */
    public void setForVariable(String[] forVariable) {
        this.forVariable = forVariable;
    }

    /**
     * httl.properties: filter.variable=filter
     */
    public void setFilterVariable(String filterVariable) {
        this.filterVariable = filterVariable;
    }

    /**
     * httl.properties: formatter.variable=formatter
     */
    public void setFormatterVariable(String formatterVariable) {
        this.formatterVariable = formatterVariable;
    }

    /**
     * httl.properties: import.packages=java.util
     */
    public void setImportPackages(String[] importPackages) {
        this.importPackages = importPackages;
        this.importPackageSet = new HashSet<String>(Arrays.asList(importPackages));
    }

    /**
     * httl.properties: import.setVariables=javax.servlet.http.HttpServletRequest request
     */
    public void setImportVariables(String[] importVariables) {
        this.importVariables = importVariables;
    }

    /**
     * httl.properties: import.methods=java.lang.Math
     */
    public void setImportMethods(Object[] importMethods) {
        for (Object function : importMethods) {
            if (function instanceof Class) {
                this.functions.put((Class<?>) function, function);
            } else {
                this.functions.put(function.getClass(), function);
            }
        }
    }

    /**
     * httl.properties: import.sequences=Mon Tue Wed Thu Fri Sat Sun Mon
     */
    public void setImportSequences(String[] sequences) {
        for (String s : sequences) {
            s = s.trim();
            if (s.length() > 0) {
                String[] ts = s.split("\\s+");
                List<String> sequence = new ArrayList<String>();
                for (String t : ts) {
                    t = t.trim();
                    if (t.length() > 0) {
                        sequence.add(t);
                    }
                }
                this.sequences.add(new StringSequence(sequence));
            }
        }
    }

    /**
     * init.
     */
    public void init() {
        defaultFilterVariable = "$" + filterVariable;
        defaultFormatterVariable = "$" + formatterVariable;
        if (importVariables != null && importVariables.length > 0) {
            this.importTypes = new HashMap<String, Class<?>>();
            for (String var : importVariables) {
                int i = var.lastIndexOf(' ');
                if (i < 0) {
                    throw new IllegalArgumentException("Illegal config import.setVariables");
                }
                this.importTypes.put(var.substring(i + 1), ClassUtils.forName(importPackages, var.substring(0, i)));
            }
        }
    }

    /**
     * inited.
     */
    public void inited() {
        if (importMacros != null && importMacros.length > 0) {
            for (String importMacro : importMacros) {
                try {
                    Template importMacroTemplate = engine.getTemplate(importMacro);
                    importMacroTemplates.putAll(importMacroTemplate.getMacros());
                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public Template translate(Resource resource, Node root, Map<String, Class<?>> defVariableTypes) throws IOException, ParseException {
        if (logger != null && logger.isDebugEnabled()) {
            logger.debug("Compile template " + resource.getName());
        }
        try {
            Template writerTemplate = null;
            Template streamTemplate = null;
            if (isOutputWriter || !isOutputStream) {
                Class<?> clazz = parseClass(resource, root, defVariableTypes, false, 0);
                writerTemplate = (Template) clazz.getConstructor(Engine.class, Interceptor.class, Compiler.class, Switcher.class, Switcher.class, Filter.class, Formatter.class, Converter.class, Converter.class, Map.class, Map.class, Resource.class, Template.class, Node.class)
                        .newInstance(engine, interceptor, compiler, valueFilterSwitcher, formatterSwitcher, valueFilter, formatter, mapConverter, outConverter, functions, importMacroTemplates, resource, null, root);
            }
            if (isOutputStream) {
                Class<?> clazz = parseClass(resource, root, defVariableTypes, true, 0);
                streamTemplate = (Template) clazz.getConstructor(Engine.class, Interceptor.class, Compiler.class, Switcher.class, Switcher.class, Filter.class, Formatter.class, Converter.class, Converter.class, Map.class, Map.class, Resource.class, Template.class, Node.class)
                        .newInstance(engine, interceptor, compiler, valueFilterSwitcher, formatterSwitcher, valueFilter, formatter, mapConverter, outConverter, functions, importMacroTemplates, resource, null, root);
            }
            if (writerTemplate != null && streamTemplate != null) {
                return new AdaptiveTemplate(writerTemplate, streamTemplate, outConverter);
            } else if (streamTemplate != null) {
                return streamTemplate;
            } else {
                return writerTemplate;
            }
        } catch (IOException | ParseException e) {
            throw e;
        } catch (Exception e) {
            throw new ParseException("Failed to translate template: " + resource.getName() + ", cause: " + ClassUtils.toString(e), 0);
        }
    }

    private String getTemplateClassName(Resource resource, boolean stream) {
        String name = resource.getName();
        String encoding = resource.getEncoding();
        Locale locale = resource.getLocale();
        long lastModified = resource.getLastModified();
        StringBuilder buf = new StringBuilder(name.length() + 40);
        buf.append(name);
        if (engineName != null) {
            buf.append("_");
            buf.append(engineName);
        }
        if (encoding != null) {
            buf.append("_");
            buf.append(encoding);
        }
        if (locale != null) {
            buf.append("_");
            buf.append(locale);
        }
        if (lastModified > 0) {
            buf.append("_");
            buf.append(lastModified);
        }
        buf.append(stream ? "_stream" : "_writer");
        return TEMPLATE_CLASS_PREFIX + StringUtils.getVaildName(buf.toString());
    }

    private Class<?> parseClass(Resource resource, Node root, Map<String, Class<?>> types, boolean stream, int offset) throws IOException, ParseException {
        String name = getTemplateClassName(resource, stream);
        try {
            return Class.forName(name, true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            if (types == null) {
                types = new HashMap<String, Class<?>>();
            }
            CompiledVisitor visitor = new CompiledVisitor();
            visitor.setResource(resource);
            visitor.setNode(root);
            visitor.setTypes(types);
            visitor.setStream(stream);
            visitor.setOffset(offset);
            visitor.setDefaultFilterVariable(defaultFilterVariable);
            visitor.setDefaultFormatterVariable(defaultFormatterVariable);
            visitor.setDefaultVariableType(defaultVariableType);
            visitor.setEngineName(engineName);
            visitor.setFilterVariable(filterVariable);
            visitor.setFormatterSwitcher(formatterSwitcher);
            visitor.setFormatterVariable(formatterVariable);
            visitor.setForVariable(forVariable);
            visitor.setImportMacroTemplates(importMacroTemplates);
            visitor.setImportPackages(importPackages);
            visitor.setImportPackageSet(importPackageSet);
            visitor.setImportSizers(importSizers);
            visitor.setImportGetters(importGetters);
            visitor.setImportTypes(importTypes);
            visitor.setImportMethods(functions);
            visitor.setOutputEncoding(outputEncoding);
            visitor.setSourceInClass(sourceInClass);
            visitor.setTextFilter(textFilter);
            visitor.setTextFilterSwitcher(textFilterSwitcher);
            visitor.setTextInClass(textInClass);
            visitor.setValueFilterSwitcher(valueFilterSwitcher);
            visitor.setCompiler(compiler);
            visitor.init();
            root.accept(visitor);
            return visitor.compile();
        }
    }

}
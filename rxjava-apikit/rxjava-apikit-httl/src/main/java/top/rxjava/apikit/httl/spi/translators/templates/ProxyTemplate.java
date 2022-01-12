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
package top.rxjava.apikit.httl.spi.translators.templates;

import top.rxjava.apikit.httl.*;
import top.rxjava.apikit.httl.util.UnsafeByteArrayOutputStream;
import top.rxjava.apikit.httl.util.UnsafeStringWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * ProxyTemplate. (SPI, Prototype, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see httl.Engine#getTemplate(String)
 */
public class ProxyTemplate implements Template {

    private final Template template;

    public ProxyTemplate(Template template) {
        this.template = template;
    }

    @Override
    public Object evaluate() throws ParseException {
        return evaluate(null);
    }

    @Override
    public Object evaluate(Object parameters)
            throws ParseException {
        if (Context.getContext().getOut() instanceof OutputStream) {
            UnsafeByteArrayOutputStream output = new UnsafeByteArrayOutputStream();
            try {
                render(parameters, output);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            return output.toByteArray();
        } else {
            UnsafeStringWriter writer = new UnsafeStringWriter();
            try {
                render(parameters, writer);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            return writer.toString();
        }
    }

    @Override
    public void render() throws IOException, ParseException {
        render(null, Context.getContext().getOut());
    }

    @Override
    public void render(Object stream) throws IOException, ParseException {
        render(null, stream);
    }

    @Override
    public void render(Object parameters, Object stream)
            throws IOException, ParseException {
        template.render(parameters, stream);
    }

    @Override
    public String getName() {
        return template.getName();
    }

    @Override
    public String getEncoding() {
        return template.getEncoding();
    }

    @Override
    public Locale getLocale() {
        return template.getLocale();
    }

    @Override
    public long getLastModified() {
        return template.getLastModified();
    }

    @Override
    public long getLength() {
        return template.getLength();
    }

    @Override
    public String getSource() throws IOException {
        return template.getSource();
    }

    @Override
    public Reader openReader() throws IOException {
        return template.openReader();
    }

    @Override
    public Map<String, Class<?>> getVariables() {
        return template.getVariables();
    }

    @Override
    public InputStream openStream() throws IOException {
        return template.openStream();
    }

    @Override
    public int getOffset() {
        return template.getOffset();
    }

    @Override
    public Engine getEngine() {
        return template.getEngine();
    }

    @Override
    public Map<String, Template> getMacros() {
        return template.getMacros();
    }

    @Override
    public boolean isMacro() {
        return template.isMacro();
    }

    @Override
    public void accept(Visitor visitor) throws IOException, ParseException {
        template.accept(visitor);
    }

    @Override
    public Template getParent() {
        return template.getParent();
    }

    @Override
    public List<Node> getChildren() {
        return template.getChildren();
    }

}
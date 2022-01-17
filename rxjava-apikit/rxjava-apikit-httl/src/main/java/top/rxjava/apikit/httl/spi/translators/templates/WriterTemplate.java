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

import top.rxjava.apikit.httl.Context;
import top.rxjava.apikit.httl.Engine;
import top.rxjava.apikit.httl.Node;
import top.rxjava.apikit.httl.Resource;
import top.rxjava.apikit.httl.Template;
import top.rxjava.apikit.httl.spi.Compiler;
import top.rxjava.apikit.httl.spi.Converter;
import top.rxjava.apikit.httl.spi.Filter;
import top.rxjava.apikit.httl.spi.Formatter;
import top.rxjava.apikit.httl.spi.Interceptor;
import top.rxjava.apikit.httl.spi.Switcher;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * Writer Template. (SPI, Prototype, ThreadSafe)
 * 
 * @see Engine#getTemplate(String)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class WriterTemplate extends CompiledTemplate {

	public WriterTemplate(Engine engine, Interceptor interceptor, Compiler compiler, 
			Switcher<Filter> filterSwitcher, Switcher<Formatter<Object>> formatterSwitcher, 
			Filter filter, Formatter<Object> formatter, 
			Converter<Object, Object> mapConverter, Converter<Object, Object> outConverter,
			Map<Class<?>, Object> functions, Map<String, Template> importMacros,
			Resource resource, Template template, Node root){
		super(engine, interceptor, compiler, filterSwitcher, formatterSwitcher, filter, formatter, 
				mapConverter, outConverter, functions, importMacros, resource, template, root);
	}

	@Override
	protected void doRenderStream(Context context, OutputStream stream) throws Exception {
		throw new UnsupportedOperationException("Unsupported out type " + Writer.class.getName() 
				+ " in compiled " + OutputStream.class.getName() + " template. Please config output.stream=true");
	}

}
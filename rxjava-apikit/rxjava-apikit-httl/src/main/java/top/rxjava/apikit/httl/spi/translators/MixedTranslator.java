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

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import top.rxjava.apikit.httl.Node;
import top.rxjava.apikit.httl.Resource;
import top.rxjava.apikit.httl.Template;
import top.rxjava.apikit.httl.spi.Converter;
import top.rxjava.apikit.httl.spi.Logger;
import top.rxjava.apikit.httl.spi.Translator;
import top.rxjava.apikit.httl.spi.engines.DefaultEngine;
import top.rxjava.apikit.httl.spi.translators.templates.MixedTemplate;

/**
 * MixedTranslator. (SPI, Singleton, ThreadSafe)
 * 
 * @see DefaultEngine#setTranslator(Translator)
 * 
 * @author @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class MixedTranslator implements Translator {

	private Translator compiledTranslator;

	private Translator interpretedTranslator;

	private Converter<Object, Object> mapConverter;

	private Logger logger;

	private boolean compiled;

	private boolean interpreted;

	public Template translate(Resource resource, Node root, Map<String, Class<?>> types)
			throws ParseException, IOException {
		if (interpreted && compiled) {
			return new MixedTemplate(interpretedTranslator.translate(resource, root, types),
					resource, root, types, compiledTranslator, mapConverter, logger);
		} else if (interpreted) {
			return interpretedTranslator.translate(resource, root, types);
		} else {
			return compiledTranslator.translate(resource, root, types);
		}
	}

	public void setCompiled(boolean compiled) {
		this.compiled = compiled;
	}

	public void setInterpreted(boolean interpreted) {
		this.interpreted = interpreted;
	}

	public void setCompiledTranslator(Translator compiledTranslator) {
		this.compiledTranslator = compiledTranslator;
	}

	public void setInterpretedTranslator(Translator interpretedTranslator) {
		this.interpretedTranslator = interpretedTranslator;
	}

	public void setMapConverter(Converter<Object, Object> mapConverter) {
		this.mapConverter = mapConverter;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}
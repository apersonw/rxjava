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
package top.rxjava.apikit.httl.spi.converters;

import top.rxjava.apikit.httl.spi.Converter;
import top.rxjava.apikit.httl.spi.translators.CompiledTranslator;
import top.rxjava.apikit.httl.spi.translators.InterpretedTranslator;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * ResponseOutConverter. (SPI, Singleton, ThreadSafe)
 * 
 * @see CompiledTranslator#setOutConverter(Converter)
 * @see InterpretedTranslator#setOutConverter(Converter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ResponseOutConverter implements Converter<HttpServletResponse, Object> {

	private boolean outputStream;

	/**
	 * httl.properties: output.stream=true
	 */
	public void setOutputStream(boolean outputStream) {
		this.outputStream = outputStream;
	}

	public Object convert(HttpServletResponse value, Map<String, Class<?>> types) throws IOException, ParseException {
		if (outputStream) {
			return value.getOutputStream();
		} else {
			return value.getWriter();
		}
	}

}
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
package top.rxjava.apikit.httl.spi.filters;

import java.io.UnsupportedEncodingException;

import top.rxjava.apikit.httl.spi.Filter;
import top.rxjava.apikit.httl.spi.translators.CompiledTranslator;
import top.rxjava.apikit.httl.spi.translators.InterpretedTranslator;

/**
 * AbstractFilter. (SPI, Singleton, ThreadSafe)
 * 
 * @see CompiledTranslator#setTemplateFilter(Filter)
 * @see CompiledTranslator#setTextFilter(Filter)
 * @see CompiledTranslator#setValueFilter(Filter)
 * @see InterpretedTranslator#setTemplateFilter(Filter)
 * @see InterpretedTranslator#setTextFilter(Filter)
 * @see InterpretedTranslator#setValueFilter(Filter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class AbstractFilter implements Filter {

	private String outputEncoding;

	/**
	 * httl.properties: output.encoding=UTF-8
	 */
	public void setOutputEncoding(String outputEncoding) {
		this.outputEncoding = outputEncoding;
	}

	public char[] filter(String key, char[] value) { // slowly
		if (value == null) {
			return new char[0];
		}
		String str = filter(key, String.valueOf(value));
		if (str == null) {
			return new char[0];
		}
		return str.toCharArray();
	}

	public byte[] filter(String key, byte[] value) { // slowly
		if (value == null) {
			return new byte[0];
		}
		String str = filter(key, new String(value));
		if (str == null) {
			return new byte[0];
		}
		if (outputEncoding == null) {
			return str.getBytes();
		}
		try {
			return str.getBytes(outputEncoding);
		} catch (UnsupportedEncodingException e) {
			return str.getBytes();
		}
	}

}
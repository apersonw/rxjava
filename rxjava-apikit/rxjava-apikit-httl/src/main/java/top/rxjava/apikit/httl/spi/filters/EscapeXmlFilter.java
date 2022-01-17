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

import top.rxjava.apikit.httl.spi.Filter;
import top.rxjava.apikit.httl.util.StringUtils;
import top.rxjava.apikit.httl.spi.translators.CompiledTranslator;
import top.rxjava.apikit.httl.spi.translators.InterpretedTranslator;

/**
 * EscapeXmlFilter. (SPI, Singleton, ThreadSafe)
 * 
 * @see CompiledTranslator#setValueFilter(Filter)
 * @see InterpretedTranslator#setValueFilter(Filter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class EscapeXmlFilter implements Filter {

	public String filter(String key, String value) {
		return StringUtils.escapeXml(value);
	}

	public char[] filter(String key, char[] value) {
		return StringUtils.escapeXml(value);
	}

	public byte[] filter(String key, byte[] value) {
		return StringUtils.escapeXml(value);
	}

}
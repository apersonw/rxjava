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
package top.rxjava.apikit.httl.spi;

import top.rxjava.apikit.httl.spi.translators.CompiledTranslator;
import top.rxjava.apikit.httl.spi.translators.InterpretedTranslator;

/**
 * Text Filter. (SPI, Singleton, ThreadSafe)
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
public interface Filter {

	/**
	 * Filter the string value.
	 * 
	 * @param key - source key
	 * @param value - original string value
	 * @return filtered string value
	 */
	String filter(String key, String value);

	/**
	 * Filter the char array value.
	 * 
	 * @param key - source key
	 * @param value - original char array value
	 * @return filtered char array value
	 */
	char[] filter(String key, char[] value);

	/**
	 * Filter the byte array value.
	 * 
	 * @param key - source key
	 * @param value - original byte array value
	 * @return filtered byte array value
	 */
	byte[] filter(String key, byte[] value);

}
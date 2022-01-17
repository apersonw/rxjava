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

import java.util.List;

/**
 * Location Switcher. (SPI, Singleton, ThreadSafe)
 * 
 * @see CompiledTranslator#setTextFilterSwitcher(Switcher)
 * @see CompiledTranslator#setValueFilterSwitcher(Switcher)
 * @see CompiledTranslator#setFormatterSwitcher(Switcher)
 * @see InterpretedTranslator#setTextFilterSwitcher(Switcher)
 * @see InterpretedTranslator#setValueFilterSwitcher(Switcher)
 * @see InterpretedTranslator#setFormatterSwitcher(Switcher)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public interface Switcher<T> {

	/**
	 * Switch's locations.
	 * 
	 * <pre>
	 * locations = ["&lt;script", "&lt;script&gt;"]
	 * </pre>
	 * 
	 * @return locations
	 */
	List<String> locations();

	/**
	 * Enter the location.
	 * 
	 * <pre>
	 * filter = switcher.switchover("&lt;script", defaultFilter); // return EscapeStringFilter
	 * &lt;script type="text/javascript"&gt;
	 * ...
	 * filter = switcher.switchover("&lt;/script&gt;", defaultFilter); // return defaultFilter
	 * &lt;/script&gt;
	 * </pre>
	 * 
	 * @param location - the entered location
	 * @param origin - the origin value
	 * @return the location value
	 */
	T switchover(String location, T origin);

}
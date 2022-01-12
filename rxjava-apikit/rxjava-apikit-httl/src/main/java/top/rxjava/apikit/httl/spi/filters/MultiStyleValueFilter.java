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

/**
 * MultiStyleValueFilter. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see top.rxjava.apikit.httl.spi.translators.CompiledTranslator#setValueFilter(Filter)
 * @see top.rxjava.apikit.httl.spi.translators.InterpretedTranslator#setValueFilter(Filter)
 */
public class MultiStyleValueFilter extends MultiFilter {

    /**
     * httl.properties: style.value.filters=httl.spi.filters.EscapeStringFilter
     */
    public void setStyleValueFilters(Filter[] filters) {
        setFilters(filters);
    }

}
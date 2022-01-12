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
package top.rxjava.apikit.httl.util;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class TypeMap extends MapSupport<String, Class<?>> {

    private final Map<String, Object> valueMap;

    public TypeMap(Map<String, Object> valueMap) {
        this.valueMap = valueMap;
    }

    public Class<?> get(Object key) {
        if (valueMap != null) {
            Object value = valueMap.get(key);
            if (value != null) {
                return value.getClass();
            }
            value = valueMap.get(key + ".class");
            if (value instanceof Class) {
                return (Class<?>) value;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Set<String> keySet() {
        return valueMap == null ? Collections.EMPTY_SET : valueMap.keySet();
    }

}
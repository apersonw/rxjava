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

import java.util.*;

/**
 * MapSupport (Tool, Prototype, NotThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class MapSupport<K, V> implements Map<K, V> {

    private final Set<K> keySet;

    public MapSupport() {
        this(null);
    }

    @SuppressWarnings("unchecked")
    public MapSupport(K[] keys) {
        this.keySet = keys == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(new HashSet<K>(Arrays.asList(keys)));
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public Collection<V> values() {
        return new BeanSet<V>() {
            @Override
            protected V getVaue(K key) {
                return get(key);
            }
        };
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new BeanSet<Entry<K, V>>() {
            @Override
            protected Entry<K, V> getVaue(K key) {
                return new MapEntry<K, V>(key, get(key));
            }
        };
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public boolean isEmpty() {
        return size() > 0;
    }

    @Override
    public int size() {
        return keySet().size();
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        if (map != null) {
            for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        return put((K) key, null);
    }

    @Override
    public void clear() {
        for (K key : keySet()) {
            remove(key);
        }
    }

    private abstract class BeanSet<T> extends AbstractSet<T> {

        @Override
        public Iterator<T> iterator() {
            return new BeanIterator();
        }

        @Override
        public int size() {
            return MapSupport.this.size();
        }

        protected abstract T getVaue(K key);

        private class BeanIterator implements Iterator<T> {

            private final Iterator<K> iterator = MapSupport.this.keySet().iterator();

            private K key;

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                key = iterator.next();
                return getVaue(key);
            }

            @Override
            public void remove() {
                if (key == null)
                    throw new IllegalStateException("No such remove() key, Please call next() first.");
                MapSupport.this.remove(key);
            }

        }

    }

}
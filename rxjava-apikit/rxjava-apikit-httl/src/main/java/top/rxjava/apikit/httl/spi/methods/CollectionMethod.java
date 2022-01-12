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
package top.rxjava.apikit.httl.spi.methods;

import top.rxjava.apikit.httl.spi.methods.cycles.*;
import top.rxjava.apikit.httl.util.CollectionUtils;

import java.util.*;

/**
 * CollectionMethod. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class CollectionMethod {

    private CollectionMethod() {
    }

    public static <T> ListCycle<T> toCycle(Collection<T> values) {
        return new ListCycle<T>(values);
    }

    public static <T> ArrayCycle<T> toCycle(T[] values) {
        return new ArrayCycle<T>(values);
    }

    public static BooleanArrayCycle toCycle(boolean[] values) {
        return new BooleanArrayCycle(values);
    }

    public static CharArrayCycle toCycle(char[] values) {
        return new CharArrayCycle(values);
    }

    public static ByteArrayCycle toCycle(byte[] values) {
        return new ByteArrayCycle(values);
    }

    public static ShortArrayCycle toCycle(short[] values) {
        return new ShortArrayCycle(values);
    }

    public static IntArrayCycle toCycle(int[] values) {
        return new IntArrayCycle(values);
    }

    public static LongArrayCycle toCycle(long[] values) {
        return new LongArrayCycle(values);
    }

    public static FloatArrayCycle toCycle(float[] values) {
        return new FloatArrayCycle(values);
    }

    public static DoubleArrayCycle toCycle(double[] values) {
        return new DoubleArrayCycle(values);
    }

    public static int length(Map<?, ?> values) {
        return values == null ? 0 : values.size();
    }

    public static int length(Collection<?> values) {
        return values == null ? 0 : values.size();
    }

    public static int length(Object[] values) {
        return values == null ? 0 : values.length;
    }

    public static int length(boolean[] values) {
        return values == null ? 0 : values.length;
    }

    public static int length(char[] values) {
        return values == null ? 0 : values.length;
    }

    public static int length(byte[] values) {
        return values == null ? 0 : values.length;
    }

    public static int length(short[] values) {
        return values == null ? 0 : values.length;
    }

    public static int length(int[] values) {
        return values == null ? 0 : values.length;
    }

    public static int length(long[] values) {
        return values == null ? 0 : values.length;
    }

    public static int length(float[] values) {
        return values == null ? 0 : values.length;
    }

    public static int length(double[] values) {
        return values == null ? 0 : values.length;
    }

    public static <K, V> Map<K, V> sort(Map<K, V> map) {
        if (map == null) {
            return null;
        }
        return new TreeMap<K, V>(map);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<T> sort(List<T> list) {
        if (list == null) {
            return null;
        }
        list = new ArrayList<T>(list);
        Collections.sort((List) list);
        return list;
    }

    public static <T> Set<T> sort(Set<T> set) {
        if (set == null) {
            return null;
        }
        return new TreeSet<T>(set);
    }

    public static <T> Collection<T> sort(Collection<T> set) {
        if (set == null) {
            return null;
        }
        return new TreeSet<T>(set);
    }

    public static <T> T[] sort(T[] array) {
        if (array == null) {
            return null;
        }
        array = CollectionUtils.copyOf(array, array.length);
        Arrays.sort(array);
        return array;
    }

    public static char[] sort(char[] array) {
        if (array == null) {
            return null;
        }
        array = CollectionUtils.copyOf(array, array.length);
        Arrays.sort(array);
        return array;
    }

    public static byte[] sort(byte[] array) {
        if (array == null) {
            return null;
        }
        array = CollectionUtils.copyOf(array, array.length);
        Arrays.sort(array);
        return array;
    }

    public static short[] sort(short[] array) {
        if (array == null) {
            return null;
        }
        array = CollectionUtils.copyOf(array, array.length);
        Arrays.sort(array);
        return array;
    }

    public static int[] sort(int[] array) {
        if (array == null) {
            return null;
        }
        array = CollectionUtils.copyOf(array, array.length);
        Arrays.sort(array);
        return array;
    }

    public static long[] sort(long[] array) {
        if (array == null) {
            return null;
        }
        array = CollectionUtils.copyOf(array, array.length);
        Arrays.sort(array);
        return array;
    }

    public static float[] sort(float[] array) {
        if (array == null) {
            return null;
        }
        array = CollectionUtils.copyOf(array, array.length);
        Arrays.sort(array);
        return array;
    }

    public static double[] sort(double[] array) {
        if (array == null) {
            return null;
        }
        array = CollectionUtils.copyOf(array, array.length);
        Arrays.sort(array);
        return array;
    }

    public static <K, V> Map<K, V> recursive(Map<K, V> map) {
        Map<K, V> result = new HashMap<K, V>();
        _recursive(result, map);
        return result;
    }

    @SuppressWarnings("unchecked")
    private static <K, V> void _recursive(Map<K, V> result, Map<K, V> map) {
        if (map != null) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                V value = entry.getValue();
                if (value instanceof Map) {
                    _recursive(result, (Map<K, V>) entry.getValue());
                } else {
                    result.put(entry.getKey(), value);
                }
            }
        }
    }

    public static <T> List<T> recursive(Collection<T> set) {
        List<T> result = new ArrayList<T>();
        _recursive(result, set);
        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T> void _recursive(Collection<T> result, Collection<T> set) {
        if (set != null) {
            for (T item : set) {
                if (item instanceof Collection) {
                    _recursive(result, (Collection<T>) item);
                } else {
                    result.add(item);
                }
            }
        }
    }

    public static <T> List<T> recursive(Collection<T> set, String children) {
        List<T> result = new ArrayList<T>();
        if (set != null) {
            for (T item : set) {
                _recursive(result, item, children);
            }
        }
        return result;
    }

    public static <T> List<T> recursive(T node, String children) {
        List<T> result = new ArrayList<T>();
        _recursive(result, node, children);
        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T> void _recursive(Collection<T> result, T node, String children) {
        if (node != null) {
            result.add(node);
            Collection<T> set;
            try {
                set = (Collection<T>) node.getClass().getMethod(children, new Class<?>[0]).invoke(node, new Object[0]);
            } catch (RuntimeException e) {
                throw (RuntimeException) e;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            if (set != null) {
                for (T item : set) {
                    _recursive(result, item, children);
                }
            }
        }
    }

}
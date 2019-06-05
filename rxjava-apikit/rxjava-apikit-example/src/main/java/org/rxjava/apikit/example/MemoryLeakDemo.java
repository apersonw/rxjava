package org.rxjava.apikit.example;

import java.util.HashMap;
import java.util.Map;

/**
 * @author happy 2019-05-11 22:08
 */
public class MemoryLeakDemo {
    static class Key {
        Integer id;

        Key(Integer id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        public static void main(String[] args) {
            Map m = new HashMap<>();
            new Object();
            while (true) {
                for (int i = 0; i < 10000; i++) {
                    if (!m.containsKey(new Key(i))) {
                        m.put(new Key(i), "Number" + i);
                    }
                }
            }
        }
    }
}

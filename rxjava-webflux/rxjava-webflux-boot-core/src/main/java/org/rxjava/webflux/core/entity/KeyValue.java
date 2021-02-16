package org.rxjava.webflux.core.entity;

import lombok.*;

import java.util.Objects;

/**
 * @author happy 2019-04-22 15:47
 * 键值对对象
 */
@Data
public class KeyValue<K, V> {
    private K key;
    private V value;
}

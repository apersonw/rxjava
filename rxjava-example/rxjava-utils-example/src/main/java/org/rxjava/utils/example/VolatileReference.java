package org.rxjava.utils.example;

import lombok.Data;

@Data
public class VolatileReference<T> {
    private volatile T value;
}
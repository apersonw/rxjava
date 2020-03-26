package org.rxjava.service.example.model;

import lombok.Data;

/**
 * @author happy
 * @date 2020/3/26 11:58 上午
 */
@Data
public class OpResult<T> {
    private int code;
    private String msg;
    private T data;
    private int count;
}

package com.example.rediscache.config;

import lombok.Getter;

@Getter
public enum CacheType {
    GET_HELLO("getHello", 60 * 3, 10000);

    CacheType(String cacheName, int expireAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expireAfterWrite = expireAfterWrite;
        this.maximumSize = maximumSize;
    }

    private final String cacheName;
    private final int expireAfterWrite;
    private final int maximumSize;
}

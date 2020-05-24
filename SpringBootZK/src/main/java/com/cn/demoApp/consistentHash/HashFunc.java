package com.cn.demoApp.consistentHash;

public interface HashFunc {
    public Long hash(Object key);
}

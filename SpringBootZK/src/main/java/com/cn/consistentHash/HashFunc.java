package com.cn.consistentHash;

public interface HashFunc {
    public Long hash(Object key);
}

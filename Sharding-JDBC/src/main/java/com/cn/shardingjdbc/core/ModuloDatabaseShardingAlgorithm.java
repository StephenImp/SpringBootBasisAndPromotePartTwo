package com.cn.shardingjdbc.core;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 自定义分库分表算法
 * <p>
 * 分库算法类需要实现SingleKeyDatabaseShardingAlgorithm<T>接口，
 * 这是一个泛型接口，T代表分库依据的字段的类型，
 * 比如我们根据userId%2来分库，userId是Long型的，这里的T就是Long。
 */
public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Long> {

    @Override
    public String doEqualSharding(Collection<String> availableDatabaseNames, ShardingValue<Long> shardingValue) {
        for (String databaseName : availableDatabaseNames) {
            if (databaseName.endsWith(shardingValue.getValue() % 2 + "")) {
                return databaseName;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doInSharding(Collection<String> availableDatabaseNames, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableDatabaseNames.size());
        for (Long value : shardingValue.getValues()) {
            for (String name : availableDatabaseNames) {
                if (name.endsWith(value % 2 + "")) {
                    result.add(name);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> availableDatabaseNames, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableDatabaseNames.size());
        Range<Long> range = shardingValue.getValueRange();
        for (Long i = range.lowerEndpoint(); i < range.upperEndpoint(); i++) {
            for (String each : availableDatabaseNames) {
                if (each.endsWith(i % 2 + "")) {
                    result.add(each);
                }
            }
        }

        return result;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

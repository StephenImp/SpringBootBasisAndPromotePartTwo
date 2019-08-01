package com.cn.shardingjdbc.dao;

import com.cn.shardingjdbc.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {
}

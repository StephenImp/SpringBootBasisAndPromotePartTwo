package com.cn.demoApp.shardingjdbc.dao;

import com.cn.demoApp.shardingjdbc.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {
}

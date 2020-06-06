package com.cn.demoApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cn.demoApp.entity.Subway;

/**
 *
 */
public interface SubwayRepository extends CrudRepository<Subway, Long>{
    List<Subway> findAllByCityEnName(String cityEnName);
}

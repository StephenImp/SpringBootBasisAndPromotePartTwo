package com.cn.demoApp.repository;

import java.util.List;

import com.cn.demoApp.entity.SubwayStation;
import org.springframework.data.repository.CrudRepository;

/**
 *
 */
public interface SubwayStationRepository extends CrudRepository<SubwayStation, Long> {
    List<SubwayStation> findAllBySubwayId(Long subwayId);
}

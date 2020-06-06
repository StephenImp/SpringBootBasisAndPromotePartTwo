package com.cn.demoApp.repository;

import java.util.List;

import com.cn.demoApp.entity.HouseTag;
import org.springframework.data.repository.CrudRepository;

/**
 *
 */
public interface HouseTagRepository extends CrudRepository<HouseTag, Long> {
    HouseTag findByNameAndHouseId(String name, Long houseId);

    List<HouseTag> findAllByHouseId(Long id);

    List<HouseTag> findAllByHouseIdIn(List<Long> houseIds);
}

package com.cn.demoApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cn.demoApp.entity.HousePicture;

/**
 *
 */
public interface HousePictureRepository extends CrudRepository<HousePicture, Long> {
    List<HousePicture> findAllByHouseId(Long id);
}

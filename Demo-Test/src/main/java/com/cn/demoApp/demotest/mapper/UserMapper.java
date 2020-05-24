package com.cn.demoApp.demotest.mapper;

import com.cn.demoApp.demotest.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    void insert(UserEntity user);

    void deleteByIds(@Param("ids")  List<Integer> ids);

    void deleteByIdsEquals(@Param("ids")  List<Integer> ids);
}

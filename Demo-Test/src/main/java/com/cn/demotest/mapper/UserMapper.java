package com.cn.demotest.mapper;

import com.cn.demotest.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    void insert(UserEntity user);

    void deleteByIds(@Param("ids")  List<Integer> ids);

    void deleteByIdsEquals(@Param("ids")  List<Integer> ids);
}

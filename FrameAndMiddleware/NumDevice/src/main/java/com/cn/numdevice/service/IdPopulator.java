package com.cn.numdevice.service;

import com.cn.numdevice.entity.Id;
import com.cn.numdevice.entity.IdMeta;

public interface IdPopulator {

    void populateId(Id id, IdMeta idMeta);
}

package com.cn.numdevice.service;

import com.cn.numdevice.entity.Id;
import com.cn.numdevice.utils.IdConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public abstract class AbstractIdServiceImpl implements IdService {
    private static Logger log = LoggerFactory.getLogger(AbstractIdServiceImpl.class);

    protected long machineId;
    protected long genMethod;
    protected long type;
    protected long version;

    @Override
    public long genId() {

        Id id = new Id();
        id.setMachineId(machineId);
        id.setGenMethod(genMethod);
        id.setType(type);
        id.setVersion(version);

        populatedId(id);

        IdConverter idConverter = new IdConverter();

        long ret = idConverter.convert(id);

        if (log.isTraceEnabled()){
            log.trace(String.format("Id:%s => %d",id,ret));
        }

        return ret;
    }

    @Override
    public Id expId(Long Id) {
        return null;
    }

    @Override
    public long makeId(long time, long seq) {
        return 0;
    }

    @Override
    public long makeId(long time, long seq, long mechine) {
        return 0;
    }

    @Override
    public long makeId(long genMethod, long time, long seq, long mechine) {
        return 0;
    }

    @Override
    public long makeId(long type, long genMethod, long time, long seq, long mechine) {
        return 0;
    }

    @Override
    public long makeId(long version, long type, long genMethod, long time, long seq, long mechine) {
        return 0;
    }

    @Override
    public Date transTime(long time) {
        return null;
    }

    /**
     * 子类根据不同的场景会有不同的实现，在这里我们只需要在父类中给子类进行处理的一个机会，
     * 子类主要负责根据某一种算法生成唯一ID的时间和序列号属性
     * 父类则对自己管理的属性机器ID，生成方式，类型和版本进行赋值。
     * @param id
     */
    protected abstract void populatedId(Id id);
}

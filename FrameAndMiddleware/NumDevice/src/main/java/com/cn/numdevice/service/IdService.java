package com.cn.numdevice.service;

import com.cn.numdevice.entity.Id;

import java.util.Date;

public interface IdService {

    //分布式发号器的主要API，用来产生唯一ID
    public long genId();

    //这里是产生唯一ID的反向操作，可以对一个ID内包含的信息进行解读，用人可读的形式来表示
    public Id expId(Long Id);

    //用来伪造某一时间的ID
    public long makeId(long time,long seq);
    public long makeId(long time,long seq,long mechine);
    public long makeId(long genMethod,long time,long seq,long mechine);
    public long makeId(long type,long genMethod,long time,long seq,long mechine);
    public long makeId(long version,long type,long genMethod,long time,long seq,long mechine);

    //用该方法用于将整型时间翻译成格式化时间
    public Date transTime(long time);


}

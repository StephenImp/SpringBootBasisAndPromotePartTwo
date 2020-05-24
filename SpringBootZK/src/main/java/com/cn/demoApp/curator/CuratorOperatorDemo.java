package com.cn.demoApp.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 对节点的相关操作
 */
public class CuratorOperatorDemo {

    public static void main(String[] args) throws InterruptedException {
        CuratorFramework curatorFramework=CuratorClientUtils.getInstance();
        System.out.println("连接成功.........");

        //fluent风格

        /**
         * 创建节点
         */

        try {
            String result=curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).
                    forPath("/curator/curator1/curator11","123".getBytes());

            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 删除节点
         */
        try {
            //默认情况下，version为-1,deletingChildrenIfNeeded 删除父节点以及子节点.
            curatorFramework.delete().deletingChildrenIfNeeded().forPath("/node11");

        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * 查询
         */
        Stat stat=new Stat();
        try {
            /**
             * storingStatIn 存入状态信息
             */
            byte[] bytes=curatorFramework.getData().storingStatIn(stat).forPath("/curator");
            System.out.println(new String(bytes)+"-->stat:"+stat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 更新
         */

        try {
            Stat stat1 = curatorFramework.setData().forPath("/curator","123".getBytes());
            System.out.println(stat1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * 异步操作
         *
         * 异步的事件，由线程池来处理的。
         */
        ExecutorService service= Executors.newFixedThreadPool(1);
        CountDownLatch countDownLatch=new CountDownLatch(1);
        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).
                    inBackground(new BackgroundCallback() {
                        @Override
                        public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                            System.out.println(Thread.currentThread().getName()+"->resultCode:"+curatorEvent.getResultCode()+"->"
                            +curatorEvent.getType());
                            countDownLatch.countDown();
                        }
                    },service).forPath("/mic","123".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        countDownLatch.await();
        service.shutdown();



        /**
         * 事务操作（curator独有的）
         */

        try {
            Collection<CuratorTransactionResult> resultCollections=curatorFramework.inTransaction()
                    .create().forPath("/trans","111".getBytes())//新增
                    .and().setData().forPath("/curator","111".getBytes())//修改
                    .and().commit();
            for (CuratorTransactionResult result:resultCollections){
                System.out.println(result.getForPath()+"->"+result.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

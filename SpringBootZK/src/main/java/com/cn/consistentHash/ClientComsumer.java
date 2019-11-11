package com.cn.consistentHash;

import com.alibaba.fastjson.JSONObject;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 猜想一下：
 *
 * 这个就是，根据服务器名称，在hash环上添加节点(真实节点和虚拟节点)
 * 返回真实节点
 *
 * 根据服务名称，找到真实ip，调用？
 *
 */
@Component
public class ClientComsumer  implements Watcher {

    //本地缓存服务列表
    private static Map<String, List<String>> servermap;
    @Autowired
    private ZookeeperServer zkServer ;
    private ZooKeeper zk;
    private ConsistentHash consistentHash;
    @Autowired
    Environment env;

    @PostConstruct
    private void init() throws IOException {
        String address = env.getProperty("zookeeper.address");
        this.zk = zkServer.getConnection(address,this);
    }

    private List<String> getNodeList(String serverName) throws KeeperException, InterruptedException, IOException {
        if (servermap == null) {
            servermap = new HashMap<>();
        }
        Stat exists = null;
        try {
            String s = "/guanjian/" + serverName;
            exists = zk.exists(s,this);
        } catch (Exception e) {
        }

        //判断是否存在该服务
        if (exists == null) return null;
        List<String> serverList = servermap.get(serverName);
        if (serverList != null && serverList.size() > 0) {
            //将已存在的serverList放入一致性Hash环
            this.consistentHash = new ConsistentHash(serverList.size(),serverList);
            return serverList;
        }

        //如果这个节点在servermap中不存在,就创建节点，放入一致性hash环中
        List<String> children = zk.getChildren("/guanjian/" + serverName,this);
        List<String> list = new ArrayList<>();
        for (String s : children) {
            byte[] data = zk.getData("/guanjian/" + serverName + "/" + s, this, null);
            String datas = new String(data);
            NodeStat nodeStat = JSONObject.parseObject(datas, NodeStat.class);
            if (!Status.stop.getCode().equals(nodeStat.getStatus())) {
                list.add(datas);
            }
        }
        //将list放入一致性Hash环
        this.consistentHash = new ConsistentHash(list.size(),list);
        servermap.put(serverName, list);
        return list;
    }

    /**
     * 获取服务信息
     * @param serverName  zk的文件名称
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     * @throws IOException
     */
    public String getServerinfo(String serverName) throws KeeperException, InterruptedException, IOException {
        try {
            //根据服务名称，将该服务放入hash环中。
            //nodeList 返回所有节点的数组   nodeList 这里是真实的服务器名称(ip)
            //circle(hash环上面是包含了虚拟节点的)   根据单个节点的hash值，再用md5方法进行加密，作为circle的key
            List<String> nodeList = getNodeList(serverName);
            if (nodeList == null|| nodeList.size()< 1) {
                return null;
            }
            //这里使用得随机负载策略，如需需要自己可以实现其他得负载策略
            //String snode = nodeList.get((int) (Math.random() * nodeList.size()));
            //从一致性Hash环的第一个服务开始查找
            String snode = (String) this.consistentHash.get(nodeList.get(0));
            NodeStat nodeStat = JSONObject.parseObject(snode, NodeStat.class);
            List<String> children = zk.getChildren("/guanjian/" + serverName,this);
            //随机负载后,将随机取得节点后的状态更新为run
            for (String s : children) {
                byte[] data = zk.getData("/guanjian/" + serverName + "/" + s, this, null);
                String datas = new String(data);
                if (snode.equals(datas)) {
                    nodeStat.setStatus(Status.run.getCode());
                    zk.setData("/guanjian/" + serverName + "/" + s,JSONObject.toJSONString(nodeStat).getBytes(),0);
                    break;
                }
            }
            return JSONObject.toJSONString(nodeStat);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //如果服务节点数据发生变化则清空本地缓存
        if (watchedEvent.getType().equals(Event.EventType.NodeChildrenChanged)) {
            servermap = null;
        }
    }

}

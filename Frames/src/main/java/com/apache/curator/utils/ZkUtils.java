package com.zookeeper.curator.utils;

import com.zookeeper.curator.client.ZkServer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type.CHILD_ADDED;
import static org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type.CHILD_REMOVED;
import static org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type.CHILD_UPDATED;

public class ZkUtils {

    public static void main(String[] args) {

        CuratorFramework client =  ZkServer.build();

        client.start();

        try {
            watch(client);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static void watch(CuratorFramework client) throws Exception {
        /**
         * 在注册监听器的时候，如果传入此参数，当事件触发时，逻辑由线程池处理
         */
        ExecutorService pool = Executors.newFixedThreadPool(2);

        /**
         * 监听数据节点的变化情况
         */
        final NodeCache nodeCache = new NodeCache(client, "/zk-huey/cnode", false);
        nodeCache.start(true);
        nodeCache.getListenable().addListener(
                new NodeCacheListener() {
                    @Override
                    public void nodeChanged() throws Exception {
                        System.out.println("Node data is changed, new data: " +
                                new String(nodeCache.getCurrentData().getData()));
                    }
                },
                pool
        );

        /**
         * 监听子节点的变化情况
         */
        final PathChildrenCache childrenCache = new PathChildrenCache(client, "/zk-huey", true);
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        childrenCache.getListenable().addListener(
                new PathChildrenCacheListener() {
                    @Override
                    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
                            throws Exception {
                        switch (event.getType()) {
                            case CHILD_ADDED:
                                System.out.println("CHILD_ADDED: " + event.getData().getPath());
                                break;
                            case CHILD_REMOVED:
                                System.out.println("CHILD_REMOVED: " + event.getData().getPath());
                                break;
                            case CHILD_UPDATED:
                                System.out.println("CHILD_UPDATED: " + event.getData().getPath());
                                break;
                            default:
                                break;
                        }
                    }
                },
                pool
        );

        client.setData().forPath("/zk-huey/cnode", "world".getBytes());

        Thread.sleep(10 * 1000);
        pool.shutdown();
        client.close();
    }

    /**
     * 事务管理：碰到异常，事务会回滚
     * @throws Exception
     */

    public static void testTransaction(CuratorFramework client) throws Exception{
        //定义几个基本操作
        CuratorOp createOp = client.transactionOp().create()
                .forPath("/curator/one_path","some data".getBytes());

        CuratorOp setDataOp = client.transactionOp().setData()
                .forPath("/curator","other data".getBytes());

        //因为节点“/curator”存在子节点，所以在删除的时候将会报错，事务回滚
        CuratorOp deleteOp = client.transactionOp().delete()
                .forPath("/curator");

        //事务执行结果
        List<CuratorTransactionResult> results = client.transaction()
                .forOperations(createOp,setDataOp,deleteOp);
       // List<CuratorTransactionResult> results = client.transaction().forOperations(createOp, setDataOp);

        //遍历输出结果
        for(CuratorTransactionResult result : results){
            System.out.println("执行结果是： " + result.getForPath() + "--" + result.getType());
        }
    }


    public static void test(CuratorFramework client){
        try {

            System.out.println(ZooKeeper.States.CONNECTED);
            System.out.println(client.getState());

            //创建永久节点
            client.create().forPath("/curator","/curator data".getBytes());

            //创建永久有序节点
            client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/curator_sequential","/curator_sequential data".getBytes());

            //创建临时节点
            client.create().withMode(CreateMode.EPHEMERAL)
                    .forPath("/curator/ephemeral","/curator/ephemeral data".getBytes());

            //创建临时有序节点
            client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL) .forPath("/curator/ephemeral_path1","/curator/ephemeral_path1 data".getBytes());

            client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/curator/ephemeral_path2","/curator/ephemeral_path2 data".getBytes());

            //测试检查某个节点是否存在
            Stat stat1 = client.checkExists().forPath("/curator");
            Stat stat2 = client.checkExists().forPath("/curator2");

            System.out.println("'/curator'是否存在： " + (stat1 != null ? true : false));
            System.out.println("'/curator2'是否存在： " + (stat2 != null ? true : false));

            //获取某个节点的所有子节点
            System.out.println(client.getChildren().forPath("/"));

            //获取某个节点数据
            System.out.println(new String(client.getData().forPath("/curator")));

            //设置某个节点数据
            client.setData().forPath("/curator","/curator modified data".getBytes());

            //创建测试节点
            client.create().orSetData().creatingParentContainersIfNeeded()
                    .forPath("/curator/del_key1","/curator/del_key1 data".getBytes());

            client.create().orSetData().creatingParentContainersIfNeeded()
                    .forPath("/curator/del_key2","/curator/del_key2 data".getBytes());

            client.create().forPath("/curator/del_key2/test_key","test_key data".getBytes());

            //删除该节点
            client.delete().forPath("/curator/del_key1");

            //级联删除子节点
            client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/curator/del_key2");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}

package com.apache.curator.client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 1.Curator框架的介绍
 *    为了更好的实现java操作ZK服务器，后来出现了Curator框架，非常的强大，目前已经是Apache的顶级项目，里面提供了更多丰富的操作，例如session超时重连、主从选举、分布式计数器、分布式锁等等适用于各种复杂的ZK场景的API封装。（具体想要深入了解的可以去官网查看）
 *
 * 2.Curator框架中使用链式编程风格，易读性更强，使用工厂方法创建连接对象。
 *   1）使用CuratorFrameworkFactory的俩个静态工厂方法（参数不同）来实现：
 *
 *      参数1：connectString，连接串
 *      参数2：retryPolicy，重试连接策略。有四种实现分别为：ExponentialBackoffRetry、RetryNTimes、RetryOneTimes、RetryUntilElapsed
 *      参数3：sessionTimeoutMs 会话超时时间 默认60000ms
 *      参数4：connectionTimeoutMs 连接超时时间，默认为15000ms
 *      注意：对于retryPolicy策略通过一个接口来让用户自定义实现。
 *  2）创建节点create方法，可选链式项：
 *      creatingParentsIfNeeded、withMode、forPath、withACL等
 *
 * 3)删除节点 delete方法，可选链式项：
 *      deletingChildrenIfNeeded、guaranteed、withVersion、forPath等
 *
 * 4)读取和修改数据 getData、setData方法
 *
 * 5)异步绑定回调方法。比如创建节点时绑定一个回调函数，该回调函数可以输出服务器的状态码以及服务器事件类型。还可以加入一个线程池进行优化操作
 *
 * 6)读取子节点的方法 getChildren
 *
 * 7)判断节点是否存在方法 checkExists
 *
 * http://curator.apache.org/zk-compatibility.html
 *
 *
 */
public class ZkServer {

    private static final int BASE_SLEEP_TIME_MS = 5000; //定义失败重试间隔时间 单位:毫秒
    private static final int MAX_RETRIES = 3; //定义失败重试次数
    private static final int SESSION_TIME_OUT = 1000000; //定义会话存活时间,根据业务灵活指定 单位:毫秒
    private static final String ZK_URI = "127.0.0.1:2181";//你自己的zkurl和端口号
    private static final String NAMESPACE = "zsj";
    //工作空间,可以不指定,建议指定,功能类似于项目包,之后创建的所有的节点都会在该工作空间下,方便管理

    public static CuratorFramework build() {
        //1 重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME_MS,MAX_RETRIES);//刚开始重试间隔为1秒，之后重试间隔逐渐增加，最多重试不超过三次
        /*
            RetryPolicy retryPolicy1 = new RetryNTimes(3, 1000);//最大重试次数，和两次重试间隔时间
            RetryPolicy retryPolicy2 = new RetryUntilElapsed(5000, 1000);//会一直重试直到达到规定时间，第一个参数整个重试不能超过时间，第二个参数重试间隔

            //第一种方式
            CuratorFramework client = CuratorFrameworkFactory.newClient(zkServerPath, 5000,5000,retryPolicy);//最后一个参数重试策略
        */
        //第二种方式
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(ZK_URI)
                .retryPolicy(retryPolicy)
                .namespace(NAMESPACE)
                .sessionTimeoutMs(SESSION_TIME_OUT)
                .build();
        return client;
    }


}

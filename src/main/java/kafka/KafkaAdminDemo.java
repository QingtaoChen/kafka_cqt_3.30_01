package kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.KafkaFuture;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Package: ccjz_rgzn_kafka
 * Description：
 * Author: Chenqingtao
 * Date: Created in 2022/4/27 0027 10:52
 * Version: 0.0.1
 */
public class KafkaAdminDemo {
    private static final String SERVERS = "node1:9092,node2:9092,node3:9092";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS);
        /*1.构造一个客户端的对象*/
        AdminClient adminClient = KafkaAdminClient.create(props);
        /*2.列出集群中的主题信息*/
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        KafkaFuture<Set<String>> names = listTopicsResult.names();
        Set<String> topicNames = names.get();
        System.out.println(topicNames);

    }
}
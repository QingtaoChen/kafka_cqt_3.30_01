package kafka;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

public class ConsumerSeekOffset {
    private static final String SERVERS = "node1:9092,node2:9092,node3:9092";
    public static void main(String[] args) {
        //1.参数配置
        Properties props = new Properties();
        //key的反序列化器
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //value的反序列化器
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        //服务器地址
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,SERVERS);
        //设置自动读取的起始offset（偏移量），值可以是：earliest，latest，none
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //设置自动提交offset（偏移量）
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
        //设置消费者组
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"b1");

        //创建
        KafkaConsumer<String, String> kafkaconsumer = new KafkaConsumer<>(props);

        //订阅主题
        kafkaconsumer.subscribe(Arrays.asList("tpc_1"));
        kafkaconsumer.subscribe(Arrays.asList("tpc_1"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {

            }
        });

        //先拉取一次消息
        kafkaconsumer.poll(Duration.ofMillis(10000));
        // 先看看被分配了哪些topic中的分区,获取得到现在已经有的分区
        Set<TopicPartition> assignment = kafkaconsumer.assignment();
        //对于被分配的分区，全部统一定位到offset=100的位置成为初始偏移量
        for (TopicPartition topicPartition: assignment){
            kafkaconsumer.seek(topicPartition,100);
        }

        while (true){
            ConsumerRecords<String, String> records = kafkaconsumer.poll(Duration.ofMillis(Long.MAX_VALUE));
            for (ConsumerRecord<String, String> record : records) {
                //do some process做一些处理
                System.out.println(record.key()+","
                        +record.value()+","
                        +record.topic()+","
                        +record.partition()+","
                        +record.offset());
                System.out.println("--------------------------1902_ws-------------------------");

            }
        }
    }
}

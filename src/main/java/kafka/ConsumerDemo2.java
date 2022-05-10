package kafka;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConsumerDemo2 {
    private static final String SERVERS = "node1:9092,node2:9092,node3:9092";

    public static void main(String[] args) {
        //1.参数配置
        Properties props = new Properties();
        //key的反序列化器
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //value的反序列化器
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //服务器地址
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS);
        //设置自动读取的起始offset（偏移量），值可以是：earliest，latest，none
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //设置自动提交offset（偏移量）
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        //设置消费者组
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "b1");

        //2.创建consumer实例对象
        KafkaConsumer<String , String> kafkaConsumer = new KafkaConsumer<String, String>(props);
        //3.1.构造相应的分区集合     通过输入new Topocpartition().var自动补全
        TopicPartition tpc_1_0 = new TopicPartition("tpc_1",0);
        TopicPartition tpc_1_1 = new TopicPartition("tpc_1",0);
        //3.2.'通过assign方法订阅主题
        kafkaConsumer.assign(Arrays.asList(tpc_1_0,tpc_1_1));

        while (true){
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(Long.MAX_VALUE));

/*            for (ConsumerRecord<String, String> record : records){

                }       注释原因：这样遍历的话，开发人员步伐预知下一条遍历到的record是哪个主题，哪个分区
*/
            List<ConsumerRecord<String, String>> records1 = records.records(tpc_1_0);
            for (ConsumerRecord<String, String> rec : records1){
                //做一些业务处理
                System.out.println(rec.key()+","
                        +rec.value()+","
                        +rec.topic()+","
                        +rec.partition()+","
                        +rec.offset());
                System.out.println("---------------------------------------------------");

            }
            List<ConsumerRecord<String, String>> records2 = records.records(tpc_1_1);
            for (ConsumerRecord<String, String> rec : records2){
                //做一些业务处理
                System.out.println(rec.key()+","
                        +rec.value()+","
                        +rec.topic()+","
                        +rec.partition()+","
                        +rec.offset());
                System.out.println("---------------------------------------------------");

            }

        }
    }
}
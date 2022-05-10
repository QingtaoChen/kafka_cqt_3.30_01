package kafka;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Properties;



/**
 * @author wyf
 * @create 2022-04-01 13:06
 */
public class ProducerDemo
{
    private static final String SERVERS = "node1:9092,node2:9092,node3:9092";

    /**
     * 配置生产者的参数
     *
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException
    {

        Properties pros = new Properties();

        //配置方式1：容易将参数的名称写错
//        pros.load(ProducerDemo.class.getClassLoader().getResourceAsStream("client.properties"));
//
//        pros.put("bootstrap.servers", "node1:9092,node2:9092,node3:9092");

        //配置方法2：利用常用类，去进行配置，不容易写错参数，比较容易记忆
        pros.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS);
        pros.put(ProducerConfig.ACKS_CONFIG, "all");
//        pros.put(ProducerConfig.RETRIES_CONFIG, 3);
//        pros.put(ProducerConfig.BATCH_SIZE_CONFIG, 10);
//        pros.put(ProducerConfig.LINGER_MS_CONFIG, 10000);
//        pros.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 10);
//        pros.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1024);
        pros.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        pros.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //关闭kafka幂等性的功能
        pros.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "false");


        /**
         * 步骤2.创建相应的生产者实例
         *
         */
        KafkaProducer<String, String> producer = new KafkaProducer<>(pros);

        /**
         * 步骤3.构建待发送的消息
         *
         */
        for(int i = 0; i < 200; i++)
        {
            ProducerRecord<String, String> msg = new ProducerRecord<>("tpc_6", "name" + i, "bigdata-1901-zxy-" + RandomStringUtils.randomAlphabetic(4,7) );
            producer.send(msg);
        }

        /**
         * 步骤5.关闭
         *
         */
        producer.close();

    }
}
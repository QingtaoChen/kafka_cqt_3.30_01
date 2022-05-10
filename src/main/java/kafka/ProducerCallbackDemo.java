package kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.Future;

public class ProducerCallbackDemo {

    private static final String SERVERS = "node1:9092";

    public static void main(String[] args){

        Properties pros = new Properties();
        pros.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS);
        pros.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        pros.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        /**
         * 步骤2.创建相应的生产者实例
         *
         */
        KafkaProducer<String, String> producer = new KafkaProducer<>(pros);

        /**
         * 步骤3.构建待发送的消息
         *
         */
        for (int i = 0;i < 10;i++){

            ProducerRecord<String,String> rcd = new ProducerRecord<String,String>("tpc_1", "key" + i, "rgzn_bigdata1902_ws   "+i);
            Future<RecordMetadata> send = producer.send(rcd, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (recordMetadata !=null){
                        System.out.println(recordMetadata.topic());
                        System.out.println(recordMetadata.offset());
                        System.out.println(recordMetadata.serializedKeySize());
                        System.out.println(recordMetadata.serializedValueSize());
                        System.out.println(recordMetadata.timestamp());
                    }
                }
            });

        }
        producer.close();
    }
}

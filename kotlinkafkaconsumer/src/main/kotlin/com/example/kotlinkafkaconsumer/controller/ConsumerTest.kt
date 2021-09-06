package com.cc.kafka.demo.consumer

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.*
import java.util.function.Consumer
import kotlin.collections.HashMap

object consumer1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val configs: MutableMap<String, Any> = HashMap()

        //指定初始链接用到的broke地址
        configs["bootstrap.servers"] = "127.0.0.1:9092"

        //指定key的序列化类
        configs[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = IntegerDeserializer::class.java

        //指定value的序列化类
        configs[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java


        // 配置消费组ID
        configs[ConsumerConfig.GROUP_ID_CONFIG] = "consumer_demo1"

        // 如果找不到当前消费者的有效偏移量，则自动重置到最开始

        // latest表示直接重置到消息偏移量的最后一个
        configs[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        val consumer = KafkaConsumer<Int, String>(configs)


        // 先订阅，再消费
        consumer.subscribe(Arrays.asList("UpdateWorkingTimeTopic"))


        // 如果主题中没有可以消费的消息，则该方法可以放到while循环中，每过3秒重新拉取一次

        // 如果还没有拉取到，过3秒再次拉取，防止while循环太密集的poll调用。


        // 批量从主题的分区拉取消息
        val consumerRecords = consumer.poll(3000)


        // 遍历本次从主题的分区拉取的批量消息
        consumerRecords.forEach(Consumer { record ->
            println(record.topic() + "\t"
                    + record.partition() + "\t"
                    + record.offset() + "\t"
                    + record.key() + "\t"
                    + record.value())
        })
        consumer.close()
    }
}
package com.example.demo.common

import com.example.demo.config.KafkaConsumerConfig.KafkaConsumerConsumer.properties
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.lang.Exception
import java.time.Duration

import java.util.Collections



@Component
class KafkaConsumerCom {

    @KafkaListener(topics = arrayOf("UpdateWorkingTimeTopic"))
    fun listen(record: ConsumerRecord<String, String>){
        var kafkaMessager:Optional<String> = Optional.ofNullable(record.value())
        if (kafkaMessager.isPresent()) {
            var message:Any = kafkaMessager.get()
            LOGGER.info("----------------- record =" + record);
            LOGGER.info("----------------- record =" + record.partition());
            LOGGER.info("----------------- record =" + record.topic());
            LOGGER.info("----------------- record =" + record.value());
            LOGGER.info("----------------- record =" + record.headers());
            LOGGER.info("------------------ message =" + message);
        }

    }

    @KafkaListener(topics = arrayOf("UpdateProjectTopic"))
    fun listen2(record: ConsumerRecord<String, String>){
        var kafkaMessager:Optional<String> = Optional.ofNullable(record.value())
        if (kafkaMessager.isPresent()) {
            var message:Any = kafkaMessager.get()
            LOGGER.info("----------------- record =" + record);
            LOGGER.info("----------------- record =" + record.partition());
            LOGGER.info("----------------- record =" + record.topic());
            LOGGER.info("----------------- record =" + record.value());
            LOGGER.info("----------------- record =" + record.headers());
            LOGGER.info("------------------ message =" + message);
        }

    }

    /**
     * 自动提交偏移量
     */
    fun commitAuto() {
        val consumer: KafkaConsumer<String, String> = KafkaConsumer(properties)
        // 订阅主题,可传入一个主题列表，也可以是正则表达式，如果有人创建了与正则表达式匹配的新主题，
        // 会立即触发一次再均衡，消费者就可以读取新添加的主题。
        // 如：test.*，订阅test相关的所有主题
        consumer.subscribe(setOf("test_partition"))
        try {
            while (true) {
                // 消费者持续对kafka进行轮训，否则会被认为已经死亡，它的分区会被移交给群组里的其他消费者。
                // 传给poll方法的是一个超时时间，用于控制poll()方法的阻塞时间（在消费者的缓冲区里没有可用数据时会发生阻塞）
                // 如果该参数被设为0，poll会立即返回，否则它会在指定的毫秒数内一直等待broker返回数据
                // poll方法返回一个记录列表。每条记录包含了记录所属主题的信息、记录所在分区的信息、记录在分区里的偏移量，以及记录的键值对。
                val records: ConsumerRecords<String?, String?> = consumer.poll(Duration.ofMillis(100))
                for (record in records) {
                    println(String.format("topic=%s, partition=%s, offset=%d, key=%s, value=%s",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value()))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // 退出应用前使用close方法关闭消费者。
            // 网络连接和socket也会随之关闭，并立即触发一次再均衡，而不是等待群组协调器发现它不在发送心跳并认定它已死亡，
            // 因为那样需要更长的时间，导致政哥群组在一段时间内无法读取消息。
            consumer.close()
        }
    }

    /**
     * 手动同步提交当前偏移量
     */
    fun commitSelfSync() {
        // 关闭自动提交偏移量，改用手动提交，与下方consumer.commitSync();一起使用
        properties["enable.auto.commit"] = false
        val consumer: KafkaConsumer<Any?, Any?> = KafkaConsumer<Any?, Any?>(properties)
        consumer.subscribe(setOf("test_partition"))
        try {
            while (true) {
                val records = consumer.poll(Duration.ofMillis(100))
                for (record in records) {
                    println(String.format("topic=%s, partition=%s, offset=%d, key=%s, value=%s",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value()))
                }
                consumer.commitSync()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            consumer.close()
        }
    }


    companion object{
        private var LOGGER = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }
}



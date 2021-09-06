package com.example.kotlinkafkaproducer.kafkaMessage

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.FailureCallback
import org.springframework.util.concurrent.SuccessCallback

@Component("kafkaProducer")
class KafkaProducer {


    @Autowired
    private lateinit var template: KafkaTemplate<String, String>

    /**
     * kafka异步发送消息,回调统一处理为打日志
     */
    fun <T> send(topic: KafkaTopic, key: String?, value: KafkaMsgData<T>) {
        print(topic.name)

        val successCallback = SuccessCallback<SendResult<String, String>> {
            LOGGER.info("kafka发送消息成功topic是${topic.name},消息是${ObjectMapper().writeValueAsString(value)}")
        }
        val failureCallback = FailureCallback {
            LOGGER.error("kafka发送消息出错topic是${topic.name},消息是${ObjectMapper().writeValueAsString(value)}",it)
        }
        this.send(topic, key, value, successCallback, failureCallback)
    }


    /**
     * kafka异步发送消息,回调信息由业务方自己处理
     */
    fun <T> send(
        topic: KafkaTopic,
        key: String?,
        value: KafkaMsgData<T>,
        successCallBack: SuccessCallback<SendResult<String, String>>,
        failureCallback: FailureCallback
    ) {
        if (key.isNullOrEmpty()) {
            template.send(topic.name, ObjectMapper().writeValueAsString(value)).addCallback(successCallBack, failureCallback)
        } else {
            template.send(topic.name, key, ObjectMapper().writeValueAsString(value)).addCallback(successCallBack, failureCallback)
        }
    }


    /**
     * kafka同步发送消息
     */
    fun <T> sendSync(topic: KafkaTopic, key: String?, value: KafkaMsgData<T>): SendResult<String, String> {
        return if (key.isNullOrEmpty()) {
            template.send(topic.name, ObjectMapper().writeValueAsString(value)).get()
        } else {
            template.send(topic.name, key, ObjectMapper().writeValueAsString(value)).get()
        }
    }


    companion object {
        private val LOGGER = LoggerFactory.getLogger(KafkaProducer::class.java)
    }




}
package com.example.demo.controller

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component


@Component
class KafkaConsumerController {
    @KafkaListener(topics = ["UpdateWorkingTimeTopic"])
    fun receive(message: String) {
        println("app_log--消费消息:$message")
    }

}
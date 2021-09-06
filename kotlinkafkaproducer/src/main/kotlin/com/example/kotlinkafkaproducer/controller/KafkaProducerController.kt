package com.example.kotlinkafkaproducer.controller

import com.example.kotlinkafkaproducer.kafkaMessage.KafkaWorkTimeMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class KafkaProducerController {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, Any>
    @Autowired
    private lateinit var kafkaWorkTimeMessage: KafkaWorkTimeMessage

    @GetMapping("kafka/normal/{message}")
    fun sendMessageKafka(@PathVariable("message") message: String) {
        kafkaTemplate.send("UpdateWorkingTimeTopic", message)
    }

    @GetMapping("kafka/callbackOne/{message}")
    fun callbackMessageKafka(@PathVariable("message") message: String) {
        kafkaWorkTimeMessage.sendWorkingTimeMessage("jsjjj",message,"2021-09-03")
    }
}
package com.example.demo.config

import java.util.*

class KafkaConsumerConfig {
    object KafkaConsumerConsumer {
        lateinit var  properties: Properties

        init {
            properties["bootstrap.servers"] = "127.0.0.1:9092"
            properties["group.id"] = "test"
            properties["key.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
            properties["value.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
        }
    }
}
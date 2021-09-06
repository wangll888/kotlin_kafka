package com.example.kotlinkafkaproducer.kafkaMessage

import com.example.kotlinkafkaproducer.Utils.DateUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/*
* @Description
* @Date 2021/8/13 18:25
*/
@Service
class KafkaWorkTimeMessage {


    @Autowired
    lateinit var producer: KafkaProducer

    /*
     * @Description 发送项目消息
     * @Date 2021/8/13 18:18
     */
    fun sendProjectMessage(projectId: String, workingTimeOperation: WorkingTimeOperation, userId: String) {
        val model = UpdateProjectMessagePayLoad(projectId, WorkingTimeOperation.ProjectDelete)
        val message = KafkaMsgData(UUID.randomUUID().toString(), KafkaMsgType.UpdateProjectType, DateUtils.format(Date(), "yyyy-MM-dd HH:mm:ss"), userId, model)
        producer.send(KafkaTopic.UpdateProjectTopic, null, message)
    }

    /*
    * @Description 发送用户工时重新计算的消息
    * @Date 2021/8/13 9:31
    *
    * calc_wt_man_days 这部分通过发送消息实现
    */
    fun sendWorkingTimeMessage(userId:String, rootMsgId: String, startDate: String) {
        //构造kafka消息
        val message = KafkaMsgData(
                UUID.randomUUID().toString()
                , KafkaMsgType.UpdateWorkingTimeType
                , DateUtils.format(Date(), "yyyy-MM-dd HH:mm:ss")
                , "admin"
                , UpdateWorkingTimeMessagePayLoad(
                    userId = userId,
                    dateFrom = startDate,
                    workingTimeOperation = WorkingTimeOperation.WorkingTimeRecount)
                , rootMsgId
        )
        //发送kafka消息
        this.producer.send(KafkaTopic.UpdateWorkingTimeTopic, userId, message)
    }

}
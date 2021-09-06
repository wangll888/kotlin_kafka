package com.example.kotlinkafkaproducer.kafkaMessage


data class KafkaMsgData<T>(
    var msgId: String = "",  //消息唯一Id,用于排查问题
    var msgType: KafkaMsgType? = null,
    val sendTime: String = "", //消息发送时间,用于排查问题
    var sendUser: String = "", //消息发送人,一般为腾云当前的登陆用户,用于排查问题
    var payLoad: T? = null,
    var msgRootId: String = "" //消息产生自己另一个消息队列的msgId
)

enum class KafkaMsgType {
    UpdateWorkingTimeType,//工时修改类型
    UpdateProjectType, //项目修改类型
    UpdateDepartmentType,  //部门更新类型
    UpdateProjectDay  //更新项目每天记录
}

enum class KafkaTopic(val topic: String) {
    UpdateWorkingTimeTopic("UpdateWorkingTimeTopic"),
    UpdateProjectTopic("UpdateProjectTopic"),
    UpdateDepartmentTopic("UpdateDepartmentTopic"),
    ProjectDayTopic("ProjectDayTopic")
}

/*
 * @Description 修改工时操作类型
 * @Date 2021/8/13 8:43
 */
enum class WorkingTimeOperation(val description: String) {
    WorkingTimeAdd("工时添加"),
    WorkingTimeUpdate("工时修改"),
    WorkingTimeDelete("工时删除"),
    ProjectDelete("项目删除"),
    ProjectAdd("项目添加"),
    ProjectUpdate("项目修改"),
    WorkingTimeRecount("工时重新计算")

}

data class UpdateWorkingTimeMessagePayLoad(
        //开始时间
    var dateFrom: String = "",

        //结束时间
    var dateTo: String = "",

        //用户id
    var userId: String = "",

        //用户邮箱
    var userEmail: String = "",

        //计算工时类型
    var workingTimeOperation: WorkingTimeOperation = WorkingTimeOperation.WorkingTimeAdd,

        //项目数据
    var projectMessagePayLoad: UpdateProjectMessagePayLoad? = null
)


data class UpdateProjectMessagePayLoad(
        //开始时间
        var projectId: String = "",
        //计算工时类型
        var workingTimeOperation: WorkingTimeOperation = WorkingTimeOperation.WorkingTimeAdd
)

data class UpdateDepartmentMessagePayLoad(
    //类型
    var type: UpdateDepartmentOperation,
    //用户id
    var userId: String = "",
    //开始时间
    var date: String = "",
    //结束时间
    var dateTo: String = ""
)


data class ProjectDayPayLoad(
    //项目id
    var projectId: String = "",

    //总工时
    var hours: Double? = 0.0,

    //总人天
    var manDays: Double? = 0.0,

    //cost
    var cost: Double? = 0.0,

    var operation: ProjectDayOperation? = ProjectDayOperation.Other,

    var date: String = ""
)

enum class ProjectDayOperation {
    ADD,
    Delete,
    //定时任务汇总数据
    UpdateForDailyTaskSum,
    //定时任务添加缺失的数据
    UpdateForDailyTaskMissing,
    UpdateNeoFourJ,
    Other,
    UpdateForTimeANDDate,
}

enum class UpdateDepartmentOperation{
    UpdateRefMissing,
    UpdateRefOne
}



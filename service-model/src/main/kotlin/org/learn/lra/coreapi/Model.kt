package org.learn.lra.coreapi

enum class Service {
    ORDER,
    SHIPMENT,
    INVOICE,
}

enum class ActionType(val path: String) {
    REQUEST("request"),
    STATUS("status")
}

data class Action(val name : String = "",
                  val type: ActionType? = null,
                  val service: Service? = null)

enum class Result {
    COMPLETED,
    NEED_COMPENSATION
}

data class LRAResult(val lraDefinition: LRADefinition? = null, val result: Result? = null) {

    fun isSuccess() = result == Result.COMPLETED
}


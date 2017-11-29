package org.learn.lra.coreapi

enum class Service {
    ORDER,
    SHIPMENT,
    INVOICE,
    NOT_AVAILABLE
}

data class Action(val name : String = "", val service: Service = Service.NOT_AVAILABLE)

enum class Result {
    COMPLETED,
    NEED_COMPENSATION
}

data class LRAResult(val lra: LRA? = null, val result: Result? = null)


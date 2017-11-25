package org.learn.lra.coreapi

enum class Service {
    ORDER,
    SHIPMENT,
    INVOICE,
    NOT_AVAILABLE
}

data class Action(val name : String = "", val service: Service = Service.NOT_AVAILABLE)


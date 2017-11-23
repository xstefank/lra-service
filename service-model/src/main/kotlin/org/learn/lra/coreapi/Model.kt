package org.learn.lra.coreapi

enum class Service {
    ORDER,
    SHIPMENT,
    INVOICE
}

data class Action(val name : String, val service: Service)

interface LRA {

    fun getId() : String

    fun getName() : String

    fun getActions() : List<Action>
}

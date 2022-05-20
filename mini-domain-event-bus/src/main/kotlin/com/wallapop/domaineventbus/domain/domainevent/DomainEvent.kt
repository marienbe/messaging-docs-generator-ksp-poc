package com.wallapop.domaineventbus.domain.domainevent

abstract class DomainEvent(val aggregateId: String) {
    abstract fun name(): String
}

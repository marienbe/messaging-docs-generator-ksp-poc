package com.wallapop.service.domainevent

import com.wallapop.domaineventbus.domain.domainevent.DomainEvent

class InternalEvent(aggregateId: String, val location: String) : DomainEvent(aggregateId) {
    override fun name() = "dontknow.dontcare"
}

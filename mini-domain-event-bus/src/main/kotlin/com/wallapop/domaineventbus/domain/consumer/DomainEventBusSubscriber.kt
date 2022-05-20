package com.wallapop.domaineventbus.domain.consumer

import com.wallapop.domaineventbus.domain.domainevent.DomainEvent

interface DomainEventBusSubscriber<in E : DomainEvent> {
    fun process(event: E)
}

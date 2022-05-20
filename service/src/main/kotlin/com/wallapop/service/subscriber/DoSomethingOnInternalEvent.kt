package com.wallapop.service.subscriber

import com.wallapop.domaineventbus.domain.consumer.DomainEventBusSubscriber
import com.wallapop.service.domainevent.InternalEvent

class DoSomethingOnInternalEvent : DomainEventBusSubscriber<InternalEvent> {
    override fun process(event: InternalEvent) {}
}

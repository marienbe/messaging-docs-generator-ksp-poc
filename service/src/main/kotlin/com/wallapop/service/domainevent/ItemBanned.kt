package com.wallapop.service.domainevent

import com.wallapop.domaineventbus.domain.documentation.MessagingDoc
import com.wallapop.domaineventbus.domain.domainevent.DomainEvent
import com.wallapop.service.domainevent.ItemBanned.Companion.ROUTING_KEY

@MessagingDoc(routingKey = ROUTING_KEY)
class ItemBanned(aggregateId: String, val moderatorId: List<Long>) : DomainEvent(aggregateId) {
    companion object {
        const val ROUTING_KEY = "item.banned"
    }

    override fun name() = ROUTING_KEY
}


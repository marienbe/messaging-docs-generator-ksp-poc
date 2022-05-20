package com.wallapop.service.subscriber

import com.wallapop.domaineventbus.domain.consumer.DomainEventBusSubscriber
import com.wallapop.service.domainevent.ItemBanned

class DoSomethingOnItemBanned : DomainEventBusSubscriber<ItemBanned> {
    override fun process(event: ItemBanned) {}
}

package com.wallapop.messagingdocs.report

import com.wallapop.messagingdocs.type.TypeDescription

data class DomainEventDescription(
    val routingKey: String,
    val attributes: List<Attribute>
)

data class SubscriberDescription(
    val routingKey: String
)

data class Report(
    val domainEventDescriptions: List<DomainEventDescription>,
    val subscriberDescriptions: List<SubscriberDescription>
)

data class Attribute(val name: String, val type: TypeDescription)

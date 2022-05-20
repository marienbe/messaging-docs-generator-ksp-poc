package com.wallapop.messagingdocs.report

sealed class AttributeDefinition {
    data class Primitive(val attributeName: String, val attributeType: String) : AttributeDefinition()
    data class Composite(val attributeName: String, val attributeType: List<AttributeDefinition>): AttributeDefinition()
}

data class DomainEventDescription(
    val routingKey: String,
    val attributeDefinitions: List<AttributeDefinition>
)

data class SubscriberDescription(
    val routingKey: String
)

data class Report(
    val domainEventDescriptions: List<DomainEventDescription>,
    val subscriberDescriptions: List<SubscriberDescription>
)

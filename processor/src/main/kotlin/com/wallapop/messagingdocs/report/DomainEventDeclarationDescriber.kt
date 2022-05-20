package com.wallapop.messagingdocs.report

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wallapop.domaineventbus.domain.documentation.MessagingDoc
import com.wallapop.messagingdocs.report.AttributeDefinition.Primitive

@OptIn(KspExperimental::class)
object DomainEventDeclarationDescriber {
    fun describe(domainEventDeclaration: KSClassDeclaration): DomainEventDescription {
        val routingKey = domainEventDeclaration.routingKey()
        val attributeDefinition = domainEventDeclaration.getAllProperties().map {
            val name = it.simpleName.asString()
            val type = it.type.resolve().declaration.simpleName.asString()

            Primitive(name, type)
        }.toList()

        return DomainEventDescription(routingKey, attributeDefinition)
    }

    private fun KSAnnotated.routingKey() = this.getAnnotationsByType(MessagingDoc::class).map { it.routingKey }.first()
}

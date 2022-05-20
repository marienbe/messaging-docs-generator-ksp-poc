package com.wallapop.messagingdocs.report

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wallapop.domaineventbus.domain.documentation.MessagingDoc
import com.wallapop.messagingdocs.matcher.SingleParameterProcessFunctionMatcher

@OptIn(KspExperimental::class)
object SubscriberDeclarationDescriber {
    fun describe(subscriberDeclaration: KSClassDeclaration): SubscriberDescription {
        val routingKey = subscriberDeclaration
            .getAllFunctions()
            .find(SingleParameterProcessFunctionMatcher())!!
            .parameters
            .component1()
            .type
            .resolve()
            .declaration
            .getAnnotationsByType(MessagingDoc::class)
            .first()
            .routingKey

        return SubscriberDescription(routingKey)
    }
}

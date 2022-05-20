package com.wallapop.messagingdocs.matcher

import com.google.devtools.ksp.isAbstract
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wallapop.domaineventbus.domain.consumer.DomainEventBusSubscriber

class SubscriberDeclarationMatcher : Matcher<KSClassDeclaration> {
    override fun invoke(classDeclaration: KSClassDeclaration): Boolean {
        return !classDeclaration.isAbstract() && classDeclaration.superTypes.any {
            DomainEventBusSubscriber::class.qualifiedName.equals(it.resolve().declaration.qualifiedName?.asString())
        }
    }
}

package com.wallapop.messagingdocs.matcher

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.wallapop.domaineventbus.domain.consumer.DomainEventBusSubscriber

class SingleParameterProcessFunctionMatcher : Matcher<KSFunctionDeclaration> {
    override fun invoke(functioDeclaration: KSFunctionDeclaration): Boolean {
        return functioDeclaration.simpleName.asString() == DomainEventBusSubscriber<*>::process.name && functioDeclaration.parameters.size == 1
    }
}

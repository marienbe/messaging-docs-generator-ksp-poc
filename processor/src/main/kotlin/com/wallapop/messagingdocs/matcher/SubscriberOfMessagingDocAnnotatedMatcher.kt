package com.wallapop.messagingdocs.matcher

import com.google.devtools.ksp.symbol.KSClassDeclaration

class SubscriberOfMessagingDocAnnotatedMatcher : Matcher<KSClassDeclaration> {
    override fun invoke(declaration: KSClassDeclaration): Boolean {
        return declaration
            .getAllFunctions()
            .filter(SingleParameterProcessFunctionMatcher())
            .map { it.parameters.component1().type.resolve().declaration }
            .filter(MessagingDocAnnotatedMatcher())
            .any()
    }
}

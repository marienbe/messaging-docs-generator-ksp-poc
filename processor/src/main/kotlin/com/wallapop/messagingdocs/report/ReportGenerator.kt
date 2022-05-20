package com.wallapop.messagingdocs.report

import com.google.devtools.ksp.symbol.KSClassDeclaration

object ReportGenerator {
    fun generate(
        domainEventDeclarations: List<KSClassDeclaration>,
        subscriberDeclarations: List<KSClassDeclaration>
    ) = Report(
        domainEventDeclarations.map(DomainEventDeclarationDescriber::describe),
        subscriberDeclarations.map(SubscriberDeclarationDescriber::describe)
    )
}

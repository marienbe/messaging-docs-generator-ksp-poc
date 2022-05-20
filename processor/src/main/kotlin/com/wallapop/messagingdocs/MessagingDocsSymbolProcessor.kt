package com.wallapop.messagingdocs

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.wallapop.messagingdocs.ksp.ClassDeclarationFinder
import com.wallapop.messagingdocs.matcher.DomainEventDeclarationMatcher
import com.wallapop.messagingdocs.matcher.MessagingDocAnnotatedMatcher
import com.wallapop.messagingdocs.matcher.SubscriberDeclarationMatcher
import com.wallapop.messagingdocs.matcher.SubscriberOfMessagingDocAnnotatedMatcher
import com.wallapop.messagingdocs.report.ReportGenerator

class MessagingDocsSymbolProcessor(private val logger: KSPLogger) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val classDeclarationFinder = ClassDeclarationFinder(resolver.getAllFiles())

        val domainEventDeclarations = classDeclarationFinder.findMatching(DomainEventDeclarationMatcher())
        val subscriberDeclarations = classDeclarationFinder.findMatching(SubscriberDeclarationMatcher())

        val (annotatedDomainEventDeclarations, unAnnotatedDomainEventDeclarations) = domainEventDeclarations.partition(MessagingDocAnnotatedMatcher())
        val (subscriberOfAnnotatedEventDeclaration, subscriberOfUnAnnotatedEventDeclaration) = subscriberDeclarations.partition(SubscriberOfMessagingDocAnnotatedMatcher())

        val report = ReportGenerator.generate(annotatedDomainEventDeclarations, subscriberOfAnnotatedEventDeclaration)

        logger.warn(report.toString())
        logger.warn("# of undocumented events: ${unAnnotatedDomainEventDeclarations.size}")
        logger.warn("# of undocumented subscribers: ${subscriberOfUnAnnotatedEventDeclaration.size}")

        return emptyList()
    }
}

package com.wallapop.messagingdocs.matcher

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSAnnotated
import com.wallapop.domaineventbus.domain.documentation.MessagingDoc

@OptIn(KspExperimental::class)
class MessagingDocAnnotatedMatcher : Matcher<KSAnnotated> {
    override fun invoke(annotated: KSAnnotated) = annotated.isAnnotationPresent(MessagingDoc::class)
}

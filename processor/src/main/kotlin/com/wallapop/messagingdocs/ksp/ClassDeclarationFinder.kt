package com.wallapop.messagingdocs.ksp

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.wallapop.messagingdocs.matcher.Matcher

class ClassDeclarationFinder(private val files: Sequence<KSFile>) {
    fun findMatching(matcher: Matcher<KSClassDeclaration>): List<KSClassDeclaration> {
        return files.map { it.accept(ClassDeclarationFinderVisitor(matcher), Unit) }.flatten().toList()
    }

    private inner class ClassDeclarationFinderVisitor(
        private val matcher: Matcher<KSClassDeclaration>
    ) : TopDownAccumulatingVisitor<Unit, List<KSClassDeclaration>>(emptyList(), ListCombinator()) {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit): List<KSClassDeclaration> {
            return if (matcher(classDeclaration)) {
                super.visitClassDeclaration(classDeclaration, data) + classDeclaration
            } else {
                super.visitClassDeclaration(classDeclaration, data)
            }
        }
    }
}

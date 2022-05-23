package com.wallapop.messagingdocs.type

import com.google.devtools.ksp.symbol.KSTypeReference

object TypeDescriptionMapper {
    operator fun invoke(typeReference: KSTypeReference): TypeDescription {
        val declaration = typeReference.resolve().declaration
        val name = declaration.qualifiedName ?: declaration.simpleName

        return TypeDescription.Leaf.OtherD(TypeName(name.asString()))
    }
}

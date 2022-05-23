package com.wallapop.messagingdocs.type

data class TypeName(val name: String)

sealed class TypeDescription {
    sealed class Leaf: TypeDescription() {
        object ByteD : Leaf()
        object ShortD : Leaf()
        object IntD : Leaf()
        object LongD : Leaf()
        object FloatD : Leaf()
        object DoubleD : Leaf()
        object BooleanD : Leaf()
        object CharD : Leaf()
        object StringD : Leaf()
        object ArrayD : Leaf()
        data class OtherD(val name: TypeName): Leaf()
    }
    data class Composite(val name: TypeName, val attributes: List<Pair<TypeName, TypeDescription>>): TypeDescription()
    data class Collection(val elementsType: TypeDescription): TypeDescription()
}

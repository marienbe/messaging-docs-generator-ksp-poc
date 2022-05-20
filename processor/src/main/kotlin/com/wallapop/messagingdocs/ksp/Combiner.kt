package com.wallapop.messagingdocs.ksp

typealias CombineOperator<TResult, TValue> = (TResult, TValue) -> TResult

class Combiner<TResult, TValue>(defaultResult: TResult, private val operator: CombineOperator<TResult, TValue>) {
    var result: TResult = defaultResult
        private set

    fun add(value: TValue?) {
        if (value != null) {
            this.result = operator(this.result, value)
        }
    }
}

fun <TResult, TValue> combine(
    defaultResult: TResult,
    operator: CombineOperator<TResult, TValue>,
    block: Combiner<TResult, TValue>.() -> Unit
): TResult {
    val combinatorHelper = Combiner(defaultResult, operator)
    combinatorHelper.block()
    return combinatorHelper.result
}

class ListCombinator<TValue> : CombineOperator<List<TValue>, List<TValue>> {
    override fun invoke(acc: List<TValue>, collection: List<TValue>) = acc + collection
}


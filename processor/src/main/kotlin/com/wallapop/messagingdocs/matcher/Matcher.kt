package com.wallapop.messagingdocs.matcher

fun interface Matcher<TValue> : (TValue) -> Boolean

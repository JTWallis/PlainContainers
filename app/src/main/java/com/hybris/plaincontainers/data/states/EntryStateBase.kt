package com.hybris.plaincontainers.data.states

import com.hybris.plaincontainers.data.model.EntryBase

open class EntryStateBase<out T: EntryBase>(val model: T) {
}
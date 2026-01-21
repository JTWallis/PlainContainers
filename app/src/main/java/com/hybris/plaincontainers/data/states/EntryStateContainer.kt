package com.hybris.plaincontainers.data.states

import com.hybris.plaincontainers.data.model.EntryContainer

class EntryStateContainer(model: EntryContainer) : EntryStateBase<EntryContainer>(model) {
    var isExpanded = false
}
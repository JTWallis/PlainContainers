package com.hybris.plaincontainers.entrylist.entryincrement

import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseAdapter
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.data.states.EntryStateItem

class EntryIncrementAdapter(private val onItemCountChange: (itemPos: Int, addValue: Int) -> Unit)
    : EntryBaseAdapter<EntryStateItem>() {

    override fun getResource(): Int {
        return R.layout.component_entry_drag_count_incrementable
    }

    override fun createViewHolder(view: View): EntryBaseViewHolder<EntryStateItem> {
        return EntryIncrementViewHolder(view, onItemCountChange)
    }
}
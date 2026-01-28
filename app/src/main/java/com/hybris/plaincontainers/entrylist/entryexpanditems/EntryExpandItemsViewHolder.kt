package com.hybris.plaincontainers.entrylist.entryexpanditems

import android.util.Log
import android.view.View
import com.hybris.plaincontainers.components.handles.itemcount.CountHandle
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder

class EntryExpandItemsViewHolder(view: View, onEntryClick: (pos: Int) -> Unit)
    : EntryBaseViewHolder<EntryItem>(view, onEntryClick) {

    //private val amount
    private val countHandle = CountHandle(view, 0, onZeroCount = {})

    init {
        Log.d("INFO", "Init EntryExpandItemsVH")
    }

    override fun bind(item: EntryItem) {
        super.bind(item)

        countHandle.setCount(item.amount)
    }
}
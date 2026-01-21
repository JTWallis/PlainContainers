package com.hybris.plaincontainers.entrylist.entryexpanditems

import android.util.Log
import android.view.View
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.data.states.EntryStateItem

class EntryExpandItemsViewHolder(view: View)
    : EntryBaseViewHolder<EntryStateItem>(view) {

    //private val amount

    init {
        Log.d("INFO", "Init EntryExpandItemsVH")
    }

    override fun bind(item: EntryStateItem) {
        super.bind(item)
        //
    }
}
package com.hybris.plaincontainers.entrylist.entryexpanditems

import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseAdapter
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.data.states.EntryStateItem

class EntryExpandItemsAdapter()
    : EntryBaseAdapter<EntryStateItem>() {

    override fun getResource(): Int {
        return R.layout.component_entry
    }

    override fun createViewHolder(view: View): EntryBaseViewHolder<EntryStateItem> {
        return EntryExpandItemsViewHolder(view)
    }
}
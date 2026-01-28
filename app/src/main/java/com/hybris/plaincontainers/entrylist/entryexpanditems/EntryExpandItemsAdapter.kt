package com.hybris.plaincontainers.entrylist.entryexpanditems

import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseAdapter
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.data.model.EntryItem

class EntryExpandItemsAdapter()
    : EntryBaseAdapter<EntryItem>() {

    override fun getResource(): Int {
        return R.layout.component_entry_count
    }

    override fun createViewHolder(view: View): EntryBaseViewHolder<EntryItem> {
        return EntryExpandItemsViewHolder(view)
    }
}
package com.hybris.plaincontainers.entrylist.entryexpanditems

import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseAdapter
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.data.entities.EntryItemInContainer

class EntryExpandItemsAdapter()
    : EntryBaseAdapter<EntryItemInContainer>() {

    override fun getLayoutResource(): Int {
        return R.layout.component_entry_count
    }

    override fun createViewHolder(view: View): EntryBaseViewHolder<EntryItemInContainer> {
        return EntryExpandItemsViewHolder(view)
    }
}
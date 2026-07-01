package com.hybris.plaincontainers.entrylist.entryexpanditems

import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.itemcount.CountHandle
import com.hybris.plaincontainers.data.entities.EntryItemInContainer
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder

class EntryExpandItemsViewHolder(view: View)
    : EntryBaseViewHolder<EntryItemInContainer>(view, onEntryClick = {}, R.color.backgroundHighlight) {

    private val countHandle = CountHandle(view.findViewById(R.id.containerCount), 0)

    override fun bind(item: EntryItemInContainer) {
        super.bind(item)

        countHandle.setCount(item.amount)
    }
}
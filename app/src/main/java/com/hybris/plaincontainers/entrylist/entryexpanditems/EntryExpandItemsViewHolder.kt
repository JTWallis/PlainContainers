package com.hybris.plaincontainers.entrylist.entryexpanditems

import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.itemcount.CountHandle
import com.hybris.plaincontainers.data.entities.EntryItemInContainer
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder

/**
 * ViewHolder intended for RecyclerViews using "component_entry_count" layouts as their items
 * that correspond to EntryItemInContainer entities.
 * Specifically, this ViewHolder is designed for a nested RecyclerView
 * within an EntryDragExpandViewHolder/-Adapter, used in ContainerOverviewFragment.
 * Binds the EntryItemInContainer count to a CountHandle.
 */
class EntryExpandItemsViewHolder(view: View)
    : EntryBaseViewHolder<EntryItemInContainer>(view, onEntryClick = {}, R.color.backgroundHighlight) {

    private val countHandle = CountHandle(view.findViewById(R.id.containerCount), 0)

    override fun bind(item: EntryItemInContainer) {
        super.bind(item)

        countHandle.setCount(item.amount)
    }
}
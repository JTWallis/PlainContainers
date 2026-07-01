package com.hybris.plaincontainers.entrylist.entryexpanditems

import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseAdapter
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.data.entities.EntryItemInContainer

/**
 * ListAdapter for the "component_entry_count" layout and EntryItemInContainer items.
 * Designed for a nested RecyclerView within an EntryDragExpandViewHolder/-Adapter,
 * used in ContainerOverviewFragment.
 * Defines behaviour for displaying the EntryItemInContainer count.
 */
class EntryExpandItemsAdapter()
    : EntryBaseAdapter<EntryItemInContainer>() {

    override fun getLayoutResource(): Int {
        return R.layout.component_entry_count
    }

    override fun createViewHolder(view: View): EntryBaseViewHolder<EntryItemInContainer> {
        return EntryExpandItemsViewHolder(view)
    }
}
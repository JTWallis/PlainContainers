package com.hybris.plaincontainers.entrylist.entrydragexpand

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.entrylist.expandbutton.ExpandListener
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.entrylist.entrydrag.EntryDragAdapter

/**
 * ListAdapter for the "component_entry_drag" layout and EntryContainer items.
 * Designed for for the outer RecyclerView, used in ContainerOverviewFragment.
 * Defines behaviour for:
 * - Expanding/collapsing a nested RecyclerView, that makes use of
 *   EntryExpandItemsViewHolder-Adapter
 * - Dragging the outer RecyclerView items
 * - Displaying the nested RecyclerView's EntryItemInContainer count
 */
class EntryDragExpandAdapter(
    private val onEntryClick: (pos: Int) -> Unit,
    private val dragListener : DragListener<EntryContainer>,
    private val lifecycleOwner: LifecycleOwner
) : EntryDragAdapter<EntryContainer>(onEntryClick, dragListener), ExpandListener {

    override fun getLayoutResource(): Int {
        return R.layout.component_entry_drag
    }

    override fun createViewHolder(view: View): EntryBaseViewHolder<EntryContainer> {
        return EntryDragExpandViewHolder(view, onEntryClick, dragListener, ::onExpandClick, lifecycleOwner)
    }

    override fun onExpandClick(position: Int) {
        val item = entryList[position]
        item.isExpanded = !item.isExpanded
        notifyItemChanged(position)
    }
}
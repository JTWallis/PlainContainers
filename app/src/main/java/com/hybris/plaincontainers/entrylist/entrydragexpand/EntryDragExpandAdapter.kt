package com.hybris.plaincontainers.entrylist.entrydragexpand

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.entrylist.expandbutton.ExpandListener
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.entrylist.entrydrag.EntryDragAdapter

class EntryDragExpandAdapter(
    private val onEntryClick: (pos: Int) -> Unit,
    private val dragListener : DragListener<EntryContainer>,
    private val lifecycleOwner: LifecycleOwner
) : EntryDragAdapter<EntryContainer>(onEntryClick, dragListener), ExpandListener {

    override fun getResource(): Int {
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
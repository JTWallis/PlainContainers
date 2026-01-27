package com.hybris.plaincontainers.entrylist.entrydragexpand

import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.entrylist.expandbutton.ExpandListener
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.entrylist.entrydrag.EntryDragAdapter

class EntryDragExpandAdapter(private val dragListener : DragListener)
    : EntryDragAdapter<EntryContainer>(dragListener), ExpandListener {

    override fun getResource(): Int {
        return R.layout.component_entry_drag
    }

    override fun createViewHolder(view: View): EntryBaseViewHolder<EntryContainer> {
        return EntryDragExpandViewHolder(view, dragListener, ::onExpandClick)
    }

    override fun onExpandClick(position: Int) {
        val item = entryList[position]
        item.isExpanded = !item.isExpanded
        notifyItemChanged(position)
    }
}
package com.hybris.plaincontainers.entrylist.entrydragincrement

import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydrag.EntryDragAdapter

class EntryDragIncrementAdapter(private val dragListener : DragListener, private val onItemCountChange: (itemPos: Int, addValue: Int) -> Unit)
    : EntryDragAdapter<EntryItem>(dragListener) {

    override fun getResource(): Int {
        return R.layout.component_entry_drag_count_incrementable
    }

    override fun createViewHolder(view: View): EntryBaseViewHolder<EntryItem> {
        return EntryDragIncrementViewHolder(view, dragListener, onItemCountChange)
    }

}
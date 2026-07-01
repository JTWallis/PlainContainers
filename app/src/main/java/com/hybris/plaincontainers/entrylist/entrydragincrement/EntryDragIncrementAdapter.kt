package com.hybris.plaincontainers.entrylist.entrydragincrement

import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.entities.EntryItemInContainer
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydrag.EntryDragAdapter

class EntryDragIncrementAdapter(
    private val onEntryClick: (pos: Int) -> Unit,
    private val dragListener : DragListener<EntryItemInContainer>,
    private val onItemCountChange: (itemPos: Int, addValue: Int) -> Unit
) : EntryDragAdapter<EntryItemInContainer>(onEntryClick, dragListener) {

    override fun getLayoutResource(): Int {
        return R.layout.component_entry_drag_count_incrementable
    }

    override fun createViewHolder(view: View): EntryBaseViewHolder<EntryItemInContainer> {
        return EntryDragIncrementViewHolder(view, onEntryClick, dragListener, onItemCountChange)
    }

}
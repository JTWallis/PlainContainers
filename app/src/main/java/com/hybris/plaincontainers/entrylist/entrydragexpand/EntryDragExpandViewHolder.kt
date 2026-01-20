package com.hybris.plaincontainers.entrylist.entrydragexpand

import android.util.Log
import android.view.View
import com.hybris.plaincontainers.entrylist.dragbutton.DragHandle
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.entrylist.model.EntryBase
import com.hybris.plaincontainers.entrylist.model.EntryContainer

class EntryDragExpandViewHolder(view: View, private val dragListener: DragListener)
    : EntryBaseViewHolder(view) {

    private val dragHandle = DragHandle(this, dragListener)

    init {
        Log.d("INFO", "Init EntryDragExpandVH")
    }

    override fun bind(item: EntryBase) {
        super.bind(item)

        if(item !is EntryContainer) {
            Log.d("WARNING", "Binding item in EntryDragExpandVH is not of type EntryExpand!")
            return
        }

        //(item as EntryExpand).listItems =
    }
}
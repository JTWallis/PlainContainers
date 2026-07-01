package com.hybris.plaincontainers.entrylist.entrydrag

import android.view.View
import com.hybris.plaincontainers.data.entities.EntryBase
import com.hybris.plaincontainers.entrylist.dragbutton.DragHandle
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder

open class EntryDragViewHolder<T : EntryBase>(
    view: View,
    onEntryClick: (pos: Int) -> Unit,
    val dragListener: DragListener<T>
) : EntryBaseViewHolder<T>(view, onEntryClick) {

    private val dragHandle = DragHandle(this, dragListener)

    fun setHandleVisibility(visible: Boolean) {
        dragHandle.setHandleVisibility(visible)
    }

}
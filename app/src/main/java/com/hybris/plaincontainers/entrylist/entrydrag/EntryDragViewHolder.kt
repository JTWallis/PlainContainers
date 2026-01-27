package com.hybris.plaincontainers.entrylist.entrydrag

import android.view.View
import com.hybris.plaincontainers.data.model.EntryBase
import com.hybris.plaincontainers.entrylist.dragbutton.DragHandle
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder

open class EntryDragViewHolder<T: EntryBase>(view: View, private val dragListener: DragListener)
    : EntryBaseViewHolder<T>(view) {

    private val dragHandle = DragHandle(this, dragListener)

    fun setHandleVisibility(visible: Boolean) {
        dragHandle.setHandleVisibility(visible)
    }

}
package com.hybris.plaincontainers.entrylist.entrydrag

import android.view.View
import com.hybris.plaincontainers.data.entities.EntryBase
import com.hybris.plaincontainers.entrylist.dragbutton.DragHandle
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder

/**
 * ViewHolder intended for RecyclerViews using "component_entry_drag*" layouts as their items
 * that correspond to either EntryContainer or EntryItemInContainer entities.
 * Used for a generic definition in ContainerBaseFragment.
 * Defines a hideable DragHandle, that rearranges items within the RecyclerView via drag&drop.
 */
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
package com.hybris.plaincontainers.entrylist.dragbutton

import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.data.entities.EntryBase

interface DragListener<T: EntryBase> {
    /**
     * Event for touching a DragHandle and moving the touch position.
     * @param viewHolder RecyclerView's ViewHolder of the dragged item
     */
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)

    /**
     * Event for releasing a DragHandle after moving the touch position.
     * @param resultList Rearranged RecyclerView items
     */
    fun onEndDrag(resultList: List<T>)
}
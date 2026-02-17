package com.hybris.plaincontainers.entrylist.dragbutton

import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.data.entities.EntryBase

interface DragListener<T: EntryBase> {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    fun onEndDrag(resultList: List<T>)
}
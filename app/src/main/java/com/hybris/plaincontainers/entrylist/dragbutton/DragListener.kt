package com.hybris.plaincontainers.entrylist.dragbutton

import androidx.recyclerview.widget.RecyclerView

interface DragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}
package com.hybris.plaincontainers.entrylist.entrydrag

import android.util.Log
import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.model.EntryBase
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.dragbutton.ItemMoveListener
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseAdapter
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import java.util.Collections

open class EntryDragAdapter<T: EntryBase>(private val onEntryClick: (pos: Int) -> Unit, private val dragListener : DragListener)
    : EntryBaseAdapter<T>(), ItemMoveListener {

    private var isDragVisible = true

    override fun getResource(): Int {
        return R.layout.component_entry_drag
    }

    override fun onBindViewHolder(holder: EntryBaseViewHolder<T>, position: Int) {
        super.onBindViewHolder(holder, position)

        if(holder !is EntryDragViewHolder) return
        holder.setHandleVisibility(isDragVisible)
    }

    override fun createViewHolder(view: View): EntryBaseViewHolder<T> {
        return EntryDragViewHolder(view, onEntryClick, dragListener)
    }

    override fun onItemMove(from: Int, to: Int) {
        Collections.swap(entryList, from, to)
        notifyItemMoved(from, to)
    }

    fun setDragVisibility(show: Boolean) {
        this.isDragVisible = show
        notifyItemRangeChanged(0, itemCount)
    }
}
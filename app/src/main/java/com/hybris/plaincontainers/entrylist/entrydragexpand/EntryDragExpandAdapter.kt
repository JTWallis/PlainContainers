package com.hybris.plaincontainers.entrylist.entrydragexpand

import android.util.Log
import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.dragbutton.ItemMoveListener
import com.hybris.plaincontainers.entrylist.model.EntryBase
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseAdapter
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import java.util.Collections

class EntryDragExpandAdapter(private val entryList: List<EntryBase>, private val dragListener : DragListener)
    : EntryBaseAdapter(entryList), ItemMoveListener {

    private var isDragVisible = true

    override fun getResource(): Int {
        return R.layout.component_entry_drag
    }

    override fun onBindViewHolder(holder: EntryBaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        if(holder !is EntryDragExpandViewHolder) return
        holder.setShowHandle(isDragVisible)
    }

    override fun createViewHolder(view: View): EntryBaseViewHolder {
        return EntryDragExpandViewHolder(view, dragListener)
    }

    override fun onItemMove(from: Int, to: Int) {
        Log.d("INFO", "OnItemMove From " + from + " To " + to)
        Collections.swap(entryList, from, to)
        notifyItemMoved(from, to)
    }

    fun setDragVisibility(show: Boolean) {
        this.isDragVisible = show
        notifyItemRangeChanged(0, itemCount)
    }
}
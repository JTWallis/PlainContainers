package com.hybris.plaincontainers.entrylist.entrydragexpand

import android.util.Log
import android.view.View
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.dragbutton.ItemMoveListener
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseAdapter
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.entrylist.expandbutton.ExpandListener
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.data.states.EntryStateContainer
import java.util.Collections

class EntryDragExpandAdapter(private val dragListener : DragListener)
    : EntryBaseAdapter<EntryStateContainer>(), ItemMoveListener {

    private var isDragVisible = true

    override fun getResource(): Int {
        return R.layout.component_entry_drag
    }

    override fun onBindViewHolder(holder: EntryBaseViewHolder<EntryStateContainer>, position: Int) {
        super.onBindViewHolder(holder, position)

        if(holder !is EntryDragExpandViewHolder) return
        holder.setHandleVisibility(isDragVisible)
    }

    override fun createViewHolder(view: View): EntryBaseViewHolder<EntryStateContainer> {
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
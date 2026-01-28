package com.hybris.plaincontainers.entrylist.entrydragexpand

import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydrag.EntryDragViewHolder
import com.hybris.plaincontainers.entrylist.entryexpanditems.EntryExpandItemsAdapter
import com.hybris.plaincontainers.entrylist.expandbutton.ExpandHandle

class EntryDragExpandViewHolder(view: View, onEntryClick: (pos: Int) -> Unit, private val dragListener: DragListener, expandClick: (Int) -> Unit)
    : EntryDragViewHolder<EntryContainer>(view, onEntryClick, dragListener) {

    private val expandHandle = ExpandHandle(
        view.findViewById<ConstraintLayout>(R.id.containerExpand),
        onClick = {
            expandClick(bindingAdapterPosition)
        }
    )
    private val rcvItems = view.findViewById<RecyclerView>(R.id.rcvItems)

    private val expandItemsAdapter = EntryExpandItemsAdapter(onEntryClick)

    init {
        Log.d("INFO", "Init EntryDragExpandVH")

        rcvItems.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = expandItemsAdapter
            setRecycledViewPool(RecyclerView.RecycledViewPool())
        }
    }

    override fun bind(item: EntryContainer) {
        super.bind(item)

        expandItemsAdapter.setItems(item.items)
        expandHandle.bind(item.isExpanded)
        setExpandedVisibility(item.isExpanded)

        //(item as EntryExpand).listItems =
    }

    fun setExpandedVisibility(expanded: Boolean) {
        rcvItems.visibility =
            if(expanded) View.VISIBLE
            else View.GONE
    }
}
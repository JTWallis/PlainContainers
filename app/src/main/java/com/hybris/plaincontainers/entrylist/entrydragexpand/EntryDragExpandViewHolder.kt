package com.hybris.plaincontainers.entrylist.entrydragexpand

import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.dragbutton.DragHandle
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.entrylist.entryexpanditems.EntryExpandItemsAdapter
import com.hybris.plaincontainers.entrylist.expandbutton.ExpandHandle
import com.hybris.plaincontainers.data.states.EntryStateContainer
import com.hybris.plaincontainers.data.states.EntryStateItem

class EntryDragExpandViewHolder(view: View, private val dragListener: DragListener, expandClick: (Int) -> Unit)
    : EntryBaseViewHolder<EntryStateContainer>(view) {

    private val dragHandle = DragHandle(this, dragListener)
    private val expandHandle = ExpandHandle(
        view.findViewById<ConstraintLayout>(R.id.containerExpand),
        onClick = {
            expandClick(bindingAdapterPosition)
        }
    )
    private val rcvItems = view.findViewById<RecyclerView>(R.id.rcvItems)

    private val expandItemsAdapter = EntryExpandItemsAdapter()

    init {
        Log.d("INFO", "Init EntryDragExpandVH")

        rcvItems.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = expandItemsAdapter
            setRecycledViewPool(RecyclerView.RecycledViewPool())
        }
    }

    override fun bind(item: EntryStateContainer) {
        super.bind(item)

        expandItemsAdapter.setItems(item.model.items.map{ e -> EntryStateItem(e)})
        expandHandle.bind(item.isExpanded)
        setExpandedVisibility(item.isExpanded)

        //(item as EntryExpand).listItems =
    }

    fun setHandleVisibility(visible: Boolean) {
        dragHandle.setHandleVisibility(visible)
    }

    fun setExpandedVisibility(expanded: Boolean) {
        rcvItems.visibility =
            if(expanded) View.VISIBLE
            else View.GONE
    }
}
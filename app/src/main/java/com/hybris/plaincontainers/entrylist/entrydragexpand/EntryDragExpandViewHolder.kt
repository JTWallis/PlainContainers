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

    init {
        Log.d("INFO", "Init EntryDragExpandVH")
    }

    override fun bind(item: EntryStateContainer) {
        super.bind(item)

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
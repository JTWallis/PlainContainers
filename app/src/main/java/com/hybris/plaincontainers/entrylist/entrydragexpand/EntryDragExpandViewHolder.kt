package com.hybris.plaincontainers.entrylist.entrydragexpand

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.data.viewmodels.DragExpandViewModel
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydrag.EntryDragViewHolder
import com.hybris.plaincontainers.entrylist.entryexpanditems.EntryExpandItemsAdapter
import com.hybris.plaincontainers.entrylist.expandbutton.ExpandHandle
import com.hybris.plaincontainers.entrylist.itemdecoration.GapVerticalDecoration
import kotlin.math.ceil

class EntryDragExpandViewHolder(
    view: View,
    onEntryClick: (pos: Int) -> Unit,
    dragListener: DragListener<EntryContainer>,
    expandClick: (Int) -> Unit,
) : EntryDragViewHolder<EntryContainer>(view, onEntryClick, dragListener) {

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
            addItemDecoration(
            GapVerticalDecoration(
                resources.getDimensionPixelSize(R.dimen.rcvExpandedGap)
                )
            )
            setRecycledViewPool(RecyclerView.RecycledViewPool())
        }
    }

    override fun bind(item: EntryContainer) {
        super.bind(item)

        // Add margins for a gap between expanded area edges and items, only if items exist.
        // Otherwise, have no margin and thus no expandable area.
        val itemCountChanged = item.items.size != expandItemsAdapter.itemCount
        if(itemCountChanged) {
            val margin =
                if(item.items.isNotEmpty()) ceil(rcvItems.resources.getDimension(R.dimen.rcvExpandedGap)).toInt()
                else 0

            val params = rcvItems.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = margin
            params.bottomMargin = margin
        }

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
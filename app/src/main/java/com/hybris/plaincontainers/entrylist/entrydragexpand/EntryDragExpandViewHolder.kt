package com.hybris.plaincontainers.entrylist.entrydragexpand

import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import kotlinx.coroutines.launch
import kotlin.math.ceil

class EntryDragExpandViewHolder(
    view: View,
    onEntryClick: (pos: Int) -> Unit,
    dragListener: DragListener<EntryContainer>,
    expandClick: (Int) -> Unit,
    private val lifecycleOwner: LifecycleOwner
) : EntryDragViewHolder<EntryContainer>(view, onEntryClick, dragListener) {

    private val expandHandle = ExpandHandle(
        view.findViewById<ConstraintLayout>(R.id.containerExpand),
        onClick = {
            expandClick(bindingAdapterPosition)
        }
    )
    private val rcvItems = view.findViewById<RecyclerView>(R.id.rcvItems)
    private val layoutEntry = view.findViewById<ConstraintLayout>(R.id.clInner)
    private val layoutThumbnail = view.findViewById<CardView>(R.id.cvEntryThumbnailBorder)
    private val wrapperDrag = view.findViewById<FrameLayout>(R.id.wrapperDrag)

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

        expandHandle.bind(item.isExpanded)
        setExpandedVisibility(item.isExpanded)

        if(item.color != 0) {
            setEntryBorderColor(item.color)
            setThumbnailBorderColor(item.color)
            setDragBackgroundColor(item.color)
        } else {
            setEntryBorderColor(itemView.context.getColor(R.color.defaultBorderEntry))
            setThumbnailBorderColor(itemView.context.getColor(R.color.defaultBorderThumbnail))
            setDragBackgroundColor(itemView.context.getColor(R.color.defaultBgDrag))
        }

        val viewModel = DragExpandViewModel(item.containerId)
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect { items ->
                    // Add margins for a gap between expanded area edges and items, only if items exist.
                    // Otherwise, have no margin and thus no expandable area.
                    val itemCountChanged = items.size != expandItemsAdapter.itemCount
                    if(itemCountChanged) {
                        val margin =
                            if(items.isNotEmpty()) ceil(rcvItems.resources.getDimension(R.dimen.rcvExpandedGap)).toInt()
                            else 0

                        val params = rcvItems.layoutParams as ViewGroup.MarginLayoutParams
                        params.topMargin = margin
                        params.bottomMargin = margin
                    }

                    expandItemsAdapter.setItems(items)
                }
            }
        }
    }

    fun setExpandedVisibility(expanded: Boolean) {
        rcvItems.visibility =
            if(expanded) View.VISIBLE
            else View.GONE
    }

    private fun setEntryBorderColor(@ColorInt color: Int) {
        layoutEntry.setBackgroundTintList(ColorStateList.valueOf(color))
    }

    private fun setThumbnailBorderColor(@ColorInt color: Int) {
        layoutThumbnail.setBackgroundColor(color)
    }

    private fun setDragBackgroundColor(@ColorInt color: Int) {
        wrapperDrag.setBackgroundColor(color)
    }
}
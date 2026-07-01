package com.hybris.plaincontainers.entrylist.entrydragincrement

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.itemcount.CountHandle
import com.hybris.plaincontainers.components.handles.itemcount.DecrementHandle
import com.hybris.plaincontainers.components.handles.itemcount.IncrementHandle
import com.hybris.plaincontainers.data.entities.EntryItemInContainer
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydrag.EntryDragViewHolder

class EntryDragIncrementViewHolder(view: View, onEntryClick: (pos: Int) -> Unit, dragListener: DragListener<EntryItemInContainer>, onCountChange: (position: Int, addValue: Int) -> Unit)
    : EntryDragViewHolder<EntryItemInContainer>(view, onEntryClick, dragListener) {

    private val countHandle = CountHandle(view.findViewById(R.id.containerCount), 0, onZeroCount = {}, R.color.backgroundFill)

    // Though compiler marks as unused, the handles need to persist for correct behavior.
    private val decrementHandle = DecrementHandle(
        view.findViewById<ConstraintLayout>(R.id.containerBtnDecrement),
        onClick = { onCountChange(absoluteAdapterPosition, -1) })

    private val incrementHandle = IncrementHandle(
        view.findViewById<ConstraintLayout>(R.id.containerBtnIncrement),
        onClick = { onCountChange(absoluteAdapterPosition, 1) })

    override fun bind(item: EntryItemInContainer) {
        super.bind(item)

        countHandle.setCount(item.amount)
    }
}
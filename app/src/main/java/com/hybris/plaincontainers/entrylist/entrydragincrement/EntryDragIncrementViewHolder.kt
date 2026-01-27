package com.hybris.plaincontainers.entrylist.entrydragincrement

import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.itemcount.CountHandle
import com.hybris.plaincontainers.components.handles.itemcount.DecrementHandle
import com.hybris.plaincontainers.components.handles.itemcount.IncrementHandle
import com.hybris.plaincontainers.entrylist.entrybase.EntryBaseViewHolder
import com.hybris.plaincontainers.data.states.EntryStateItem

class EntryIncrementViewHolder(view: View, onCountChange: (position: Int, addValue: Int) -> Unit)
    : EntryBaseViewHolder<EntryStateItem>(view) {

    private val countHandle = CountHandle(view, 0, onZeroCount = {})
    private val decrementHandle = DecrementHandle(
        view.findViewById<ConstraintLayout>(R.id.containerBtnDecrement),
        onClick = { onCountChange(absoluteAdapterPosition, -1) })

    private val incrementHandle = IncrementHandle(
        view.findViewById<ConstraintLayout>(R.id.containerBtnIncrement),
        onClick = { onCountChange(absoluteAdapterPosition, 1) })

    init {
        Log.d("INFO", "Init EntryIncrementVH")
    }

    override fun bind(item: EntryStateItem) {
        super.bind(item)

        countHandle.setCount(item.model.amount)
    }
}
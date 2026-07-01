package com.hybris.plaincontainers.entrylist.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView ItemDecoration, that adds a top-margin to every item, excluding the first one.
 */
class TopGapDecoration(private val gap: Int)
    : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val pos = parent.getChildAdapterPosition(view)

        if(pos > 0) {
            outRect.top = gap
        }
    }
}
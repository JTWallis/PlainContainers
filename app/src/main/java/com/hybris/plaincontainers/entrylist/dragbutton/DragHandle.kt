package com.hybris.plaincontainers.entrylist.dragbutton

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.R

class DragHandle(viewHolder: RecyclerView.ViewHolder, dragListener: DragListener) {

    private val layoutDrag = viewHolder.itemView.findViewById<ConstraintLayout>(R.id.layoutDrag)

    init {
        // Suppress warning for visually impaired for now.
        @SuppressLint("ClickableViewAccessibility")

        layoutDrag.setOnTouchListener { _, e ->
            if (e.action == MotionEvent.ACTION_UP || e.action == MotionEvent.ACTION_DOWN) {
                Log.d("INFO", "OnTouch Dragged " + e.action)
                dragListener.onStartDrag(viewHolder)
            }
            false
        }
    }
}
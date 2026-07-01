package com.hybris.plaincontainers.entrylist.dragbutton

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.entities.EntryBase

/**
 * Logic side of the "component_drag" layout, which is used through an "include layout" directive.
 * Binds the DragListener.OnStartDrag event to the drag button's onTouchListener.
 * Also exposes a function to hide the drag button.
 */
class DragHandle<T: EntryBase>(viewHolder: RecyclerView.ViewHolder, dragListener: DragListener<T>) {

    private val layoutDrag = viewHolder.itemView.findViewById<ConstraintLayout>(R.id.containerDrag)

    init {
        // Suppress warning for visually impaired for now.
        @SuppressLint("ClickableViewAccessibility")

        layoutDrag.setOnTouchListener { _, e ->
            if (e.action == MotionEvent.ACTION_UP || e.action == MotionEvent.ACTION_DOWN) {
                dragListener.onStartDrag(viewHolder)
            }
            false
        }
    }

    fun setHandleVisibility(visible: Boolean) {
        layoutDrag.visibility =
            if(visible) View.VISIBLE
            else View.INVISIBLE
    }
}
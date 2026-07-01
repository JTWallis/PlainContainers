package com.hybris.plaincontainers.entrylist.expandbutton

import android.view.View
import android.widget.ImageView
import com.hybris.plaincontainers.R

/**
 * Logic side of the "component_expand" layout, which is used through an "include layout" directive.
 * Sets the onClick callback, as well as the icon and background-color,
 * based on if the RecyclerView entry is expanded/clicked or not.
 */
class ExpandHandle(private val layoutExpand: View, onClick: () -> Unit) {

    private val ivExpand = layoutExpand.findViewById<ImageView>(R.id.ivExpand)

    init {
        layoutExpand.setOnClickListener { onClick() }
    }

    fun bind(isExpanded: Boolean) {
        ivExpand.setImageResource(
            if(isExpanded) R.drawable.angle_small_up_24
            else R.drawable.angle_small_down_24
        )

        val contentDescriptionId =
            if(isExpanded) R.string.accessibility_btn_contract
            else R.string.accessibility_btn_expand

        ivExpand.contentDescription = layoutExpand.context.getString(contentDescriptionId)

        layoutExpand.setBackgroundResource(
            if(isExpanded) R.color.backgroundHighlightSelectColored
            else R.color.backgroundHighlight
        )
    }
}
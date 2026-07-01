package com.hybris.plaincontainers.components.handles.itemcount

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import com.hybris.plaincontainers.R

class CountHandle(
    private val view: View,
    private var count: Int,
    @ColorRes backgroundColorId: Int = R.color.backgroundHighlight
) {

    private val tvHandle = view.findViewById<TextView>(R.id.tvCount)

    init {
        view.setBackgroundColor(view.context.getColor(backgroundColorId))
        updateCountText()
    }

    fun setCount(newCount: Int) {
        count =
            if(newCount <= 0) 0
            else newCount

        updateCountText()
    }

    private fun updateCountText() {
        tvHandle.text = view.context.getString(R.string.count_component_amount, count)
    }

}
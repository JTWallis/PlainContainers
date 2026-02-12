package com.hybris.plaincontainers.components.handles.itemcount

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import com.hybris.plaincontainers.R

class CountHandle(
    private val view: View,
    private var count: Int,
    private val onZeroCount: () -> Unit,
    @ColorRes backgroundColorId: Int = R.color.backgroundHighlight
) {

    private val tvHandle = view.findViewById<TextView>(R.id.tvCount)

    init {
        view.setBackgroundColor(view.context.getColor(backgroundColorId))
        updateCountText()
    }

    fun setCount(newCount: Int) {
        if(newCount <= 0) {
            count = 0
            onZeroCount()
        } else {
            count = newCount
        }

        updateCountText()
    }

    fun incrementCount() {
        setCount(count + 1)
    }

    fun decrementCount() {
        setCount(count - 1)
    }

    private fun updateCountText() {
        tvHandle.text = view.context.getString(R.string.count_component_amount, count)
    }

}
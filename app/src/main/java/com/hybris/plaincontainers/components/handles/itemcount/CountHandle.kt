package com.hybris.plaincontainers.components.handles.itemcount

import android.view.View
import android.widget.TextView
import com.hybris.plaincontainers.R

class CountHandle(
    view: View,
    private var count: Int,
    private val onZeroCount: () -> Unit
) {

    private val tvHandle = view.findViewById<TextView>(R.id.tvCount)

    init {
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
        tvHandle.text = "${count}x"
    }

}
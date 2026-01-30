package com.hybris.plaincontainers.components.handles

import android.view.View
import android.widget.Button
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.cardview.widget.CardView

open class ButtonHandleBase(
    view: View,
    onClick: () -> Unit,
    @IdRes cardviewId: Int,
    @IdRes buttonId: Int) {

    protected val cvHandle = view.findViewById<CardView>(cardviewId)
    protected val btnHandle = view.findViewById<Button>(buttonId)!!

    init {
        btnHandle.setOnClickListener { onClick() }
    }

    open fun setText(text: String) {
        btnHandle.text = text
    }

    open fun setVisibility(visible: Boolean) {
        cvHandle.visibility =
            if(visible) View.VISIBLE
            else View.INVISIBLE
    }

    open fun setBackgroundColor(@ColorRes color: Int) {
        btnHandle.setBackgroundResource(color)
    }

}
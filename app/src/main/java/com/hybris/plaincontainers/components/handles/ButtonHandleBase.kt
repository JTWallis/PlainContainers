package com.hybris.plaincontainers.components.handles

import android.view.View
import android.widget.Button
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes

/**
 * Base helper class for buttons that sets the onClick callback
 * and an optional, localized contentDescription.
 * Also provides functions to set the text, visibility and background-color.
 */
open class ButtonHandleBase(
    private val view: View,
    onClick: () -> Unit,
    @IdRes buttonId: Int,
    @StringRes contentDescriptionId: Int? = null
    ) {

    protected val btnHandle = view.findViewById<Button>(buttonId)!!

    init {
        if(contentDescriptionId != null) {
            btnHandle.contentDescription = view.context.getString(contentDescriptionId)
        }

        btnHandle.setOnClickListener { onClick() }
    }

    open fun setText(text: String) {
        btnHandle.text = text
    }

    open fun setVisibility(visible: Boolean) {
        view.visibility =
            if(visible) View.VISIBLE
            else View.INVISIBLE
    }

    open fun setBackgroundColor(@ColorRes color: Int) {
        btnHandle.setBackgroundResource(color)
    }

}
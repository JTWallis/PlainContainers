package com.hybris.plaincontainers.components.handles.buttoniconlabeled

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.ButtonHandleBase

open class ButtonIconLabeledHandle(
    view: View,
    onClick: () -> Unit,
    buttonText: String,
    @DrawableRes iconId: Int,
    placeIconToEnd: Boolean = false,
) : ButtonHandleBase(
    view,
    onClick,
    R.id.btnLabeled
) {

    init {
        val drawable = ContextCompat.getDrawable(view.context, iconId)
        var start: Drawable?
        var end: Drawable?
        if(placeIconToEnd) {
            start = null
            end = drawable
        } else {
            start = drawable
            end = null
        }

        btnHandle.setCompoundDrawablesWithIntrinsicBounds(start, null, end, null)
        btnHandle.text = buttonText
    }

}
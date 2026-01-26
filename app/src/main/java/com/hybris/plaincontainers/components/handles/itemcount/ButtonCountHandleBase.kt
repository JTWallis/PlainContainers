package com.hybris.plaincontainers.components.handles.itemcount

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.ButtonHandleBase

open class ButtonCountHandleBase(
    view: View,
    onClick: () -> Unit,
    @DrawableRes buttonIcon: Int,
    @ColorRes buttonColor: Int
) : ButtonHandleBase(view, onClick, R.id.cvBtnIncrement, R.id.btnIncrement) {

    protected val ivIcon = view.findViewById<ImageView>(R.id.ivIncrementIcon)!!

    init {
        btnHandle.backgroundTintList =
            AppCompatResources.getColorStateList(view.context, buttonColor)

        ivIcon.setImageResource(buttonIcon)
    }
}
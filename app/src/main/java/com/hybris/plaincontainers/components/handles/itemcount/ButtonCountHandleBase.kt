package com.hybris.plaincontainers.components.handles.itemcount

import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.ButtonHandleBase

open class ButtonCountHandleBase(
    view: View,
    onClick: () -> Unit,
    @DrawableRes buttonIcon: Int,
    @ColorRes buttonColor: Int,
    @StringRes contentDescriptionId: Int = R.string.accessibility_btn_increment
) : ButtonHandleBase(view, onClick, R.id.btnIncrement, contentDescriptionId) {

    protected val ivIcon = view.findViewById<ImageView>(R.id.ivIncrementIcon)!!

    init {
        btnHandle.backgroundTintList =
            AppCompatResources.getColorStateList(view.context, buttonColor)

        ivIcon.setImageResource(buttonIcon)

        ivIcon.setContentDescription(view.context.getString(contentDescriptionId))
    }
}
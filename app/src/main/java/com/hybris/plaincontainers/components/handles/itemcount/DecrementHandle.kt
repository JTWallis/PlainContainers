package com.hybris.plaincontainers.components.handles.itemcount

import android.view.View
import com.hybris.plaincontainers.R

class DecrementHandle(
    view: View,
    onClick: () -> Unit)
: ButtonCountHandleBase(
    view,
    onClick,
    R.drawable.minus_24,
    R.color.button_red,
    R.string.accessibility_btn_decrement
)
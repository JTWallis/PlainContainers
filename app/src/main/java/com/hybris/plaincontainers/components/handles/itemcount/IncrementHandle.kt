package com.hybris.plaincontainers.components.handles.itemcount

import android.view.View
import com.hybris.plaincontainers.R

class IncrementHandle(
    view: View,
    onClick: () -> Unit)
: ButtonCountHandleBase(
    view,
    onClick,
    R.drawable.plus_24,
    R.color.button_green
)
package com.hybris.plaincontainers.components.handles.itemcount

import android.view.View
import com.hybris.plaincontainers.R

/**
 * Subclass of ButtonCountHandleBase, that automatically sets an icon, color and contentDescription.
 */
class IncrementHandle(
    view: View,
    onClick: () -> Unit)
: ButtonCountHandleBase(
    view,
    onClick,
    R.drawable.plus_24,
    R.color.button_green
)
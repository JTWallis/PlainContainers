package com.hybris.plaincontainers.components.handles

import android.view.View
import com.hybris.plaincontainers.R

class SortHandle(
    view: View,
    onClick: () -> Unit,
    buttonText: String
) : ButtonIconLabeledHandle(
    view,
    onClick,
    buttonText,
    R.drawable.list_24
) {}
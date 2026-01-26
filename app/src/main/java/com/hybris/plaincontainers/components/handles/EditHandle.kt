package com.hybris.plaincontainers.components.handles

import android.view.View
import com.hybris.plaincontainers.R

class EditHandle(
    view: View,
    onClick: () -> Unit,
    buttonText: String
) : ButtonIconLabeledHandle(
    view,
    onClick,
    buttonText,
    R.drawable.pencil_24,
    true
) {}
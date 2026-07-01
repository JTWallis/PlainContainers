package com.hybris.plaincontainers.components.handles.buttoniconlabeled

import android.view.View
import com.hybris.plaincontainers.R

/**
 * Subclass of ButtonIconLabeledHandle, that automatically sets a text, icon and icon-alignment.
 */
class AddHandle(
    view: View,
    onClick: () -> Unit,
    buttonText: String
) : ButtonIconLabeledHandle(
    view,
    onClick,
    buttonText,
    R.drawable.add_24,
    true
)
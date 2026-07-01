package com.hybris.plaincontainers.views

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.annotation.LayoutRes

/**
 * Base helper class for the Android PopupWindow widget.
 */
open class Popup(
    invokerView: View,
    @LayoutRes layoutRes: Int,
) {

    protected val contentView: View = LayoutInflater.from(invokerView.context).inflate(layoutRes, null)
    private val popupWindow = PopupWindow(
        contentView,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        true
    )

    /**
     * Delegate to PopupWindow.dismiss():
     * ```code
     * Disposes of the popup window.
     * This method can be invoked only after View.showAsDropDown has been executed.
     * Failing that, calling this method will have no effect.
     * ```
     */
    fun dismiss() {
        popupWindow.dismiss()
    }

    /**
     * Delegate to PopupWindow.showAtLocation():
     * ```code
     * Display the content view in a popup window at the specified location.
     * If the popup window cannot fit on screen, it will be clipped.
     * ```
     */
    fun show(view: View, gravity: Int = Gravity.CENTER, x: Int = 0, y: Int = 0) {
        popupWindow.showAtLocation(view, gravity, x, y)
    }
}
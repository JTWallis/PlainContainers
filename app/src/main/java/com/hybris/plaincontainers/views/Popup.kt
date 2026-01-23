package com.hybris.plaincontainers.views

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.annotation.LayoutRes

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

    fun show(view: View, gravity: Int = Gravity.CENTER, x: Int = 0, y: Int = 0) {
        popupWindow.showAtLocation(view, gravity, x, y)
    }
}
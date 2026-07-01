package com.hybris.plaincontainers.views.choicepopup

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.views.Popup

/**
 * Popup variant for the "popup_choice" layout.
 * Defines two buttons with a callback for each, a title and a subtitle.
 * Setting isChoiceImportant to true paints the subtitle and right button in a red color.
 */
open class ChoicePopup(
    invokerView: View,
    private val onClickLeft: () -> Unit,
    private val onClickRight: () -> Unit,
    isChoiceImportant: Boolean = false,
    @LayoutRes layoutRes: Int = R.layout.popup_choice,
    @IdRes titleRes: Int = R.id.tvPopupChoiceTitle,
    @IdRes subtitleRes: Int = R.id.tvPopupChoiceSubtitle,
    @IdRes btnLeftRes: Int = R.id.btnPopupChoiceLeft,
    @IdRes btnRightRes: Int = R.id.btnPopupChoiceRight
) : Popup(invokerView, layoutRes) {

    private val tvTitle = contentView.findViewById<TextView>(titleRes)
    protected val tvSubtitle = contentView.findViewById<TextView>(subtitleRes)!!
    private val btnBack = contentView.findViewById<ImageButton>(R.id.btnPopupChoiceBack)
    private val btnChoiceLeft = contentView.findViewById<Button>(btnLeftRes)
    private val btnChoiceRight = contentView.findViewById<Button>(btnRightRes)

    init {
        tvSubtitle.text = ""

        btnBack.setOnClickListener {
            dismiss()
        }

        btnChoiceLeft.setOnClickListener {
            onClickLeftDelegate()
            dismiss()
        }
        btnChoiceRight.setOnClickListener {
            onClickRightDelegate()
            dismiss()
        }

        if(isChoiceImportant) {
            val color = invokerView.resources.getColorStateList(R.color.textColorImportant, null)
            tvSubtitle.setTextColor(color)
            btnChoiceRight.setBackgroundTintList(color)
        }
    }

    protected open fun onClickLeftDelegate() {
        onClickLeft()
    }

    protected open fun onClickRightDelegate() {
        onClickRight()
    }

    private fun setText(text: String, tv: TextView) {
        tv.text = text
    }

    fun setTextTitle(text: String) {
        setText(text, tvTitle)
    }

    fun setTextSubtitle(text: String) {
        setText(text, tvSubtitle)
    }

    fun setTextButtonLeft(text: String) {
        setText(text, btnChoiceLeft)
    }

    fun setTextButtonRight(text: String) {
        setText(text, btnChoiceRight)
    }
}
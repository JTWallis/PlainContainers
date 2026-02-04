package com.hybris.plaincontainers.views.choicepopup

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.views.Popup

class ChoicePopup(
    invokerView: View,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
    isChoiceImportant: Boolean = false
) : Popup(invokerView, R.layout.popup_choice) {

    private val tvTitle = contentView.findViewById<TextView>(R.id.tvPopupChoiceTitle)
    private val tvSubtitle = contentView.findViewById<TextView>(R.id.tvPopupChoiceSubtitle)
    private val btnChoiceLeft = contentView.findViewById<Button>(R.id.btnPopupChoiceLeft)
    private val btnChoiceRight = contentView.findViewById<Button>(R.id.btnPopupChoiceRight)

    init {
        tvSubtitle.text = ""

        btnChoiceLeft.setOnClickListener {
            onClickLeft()
            dismiss()
        }
        btnChoiceRight.setOnClickListener {
            onClickRight()
            dismiss()
        }

        if(isChoiceImportant) {
            val color = invokerView.resources.getColorStateList(R.color.importantText, null)
            tvSubtitle.setTextColor(color)
            btnChoiceRight.setBackgroundTintList(color)
        }
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
package com.hybris.plaincontainers.views.warningpopup

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.views.Popup

class WarningPopup(invokerView: View, title: String): Popup(invokerView, R.layout.popup_warning) {

    private val tvTitle = contentView.findViewById<TextView>(R.id.tvPopupWarningTitle)
    private val btnWarning = contentView.findViewById<Button>(R.id.btnPopupWarningOk)

    init {
        tvTitle.text = title

        btnWarning.setOnClickListener {
            dismiss()
        }
    }
}
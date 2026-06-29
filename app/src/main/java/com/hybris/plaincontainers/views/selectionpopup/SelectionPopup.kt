package com.hybris.plaincontainers.views.selectionpopup

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.views.Popup

class SelectionPopup(
    invokerView: View,
    title: String,
    selections: Array<String>,
    initSelection: Int,
    private val onSortSelectionConfirm: (position: Int) -> Unit)
: Popup(invokerView, R.layout.popup_selection) {

    private val tvTitle = contentView.findViewById<TextView>(R.id.tvPopupSelectionTitle)
    private val list = contentView.findViewById<ListView>(R.id.lvPopupSelectionList)
    private val btnBack = contentView.findViewById<ImageButton>(R.id.btnSelectionBack)
    private val btnConfirm = contentView.findViewById<Button>(R.id.btnSelectionOk)

    private val adapter = SelectionPopupAdapter(
        invokerView.context,
        selections,
        initSelection,
        onClick = { pos -> onSelectionClick(pos) }
    )

    private var currentSelection = initSelection

    init {
        list.adapter = adapter
        tvTitle.text = title

        btnBack.setOnClickListener { dismiss() }
        btnConfirm.setOnClickListener { onSelectionConfirm() }
    }

    private fun onSelectionClick(position: Int) {
        currentSelection = position
    }

    private fun onSelectionConfirm() {
        onSortSelectionConfirm(currentSelection)
        dismiss()
    }
}
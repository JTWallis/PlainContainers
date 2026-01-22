package com.hybris.plaincontainers.entrylist.sortbutton

import android.view.View
import android.widget.Button
import com.hybris.plaincontainers.R

class SortHandle(view: View, onClick: () -> Unit) {

    private val btnSort = view.findViewById<Button>(R.id.btnSort)

    init {
        btnSort.setOnClickListener { onClick() }
    }

    fun setText(text: String) {
        btnSort.text = "Sort by $text"
    }
}
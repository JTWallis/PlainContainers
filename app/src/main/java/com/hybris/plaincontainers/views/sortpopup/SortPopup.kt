package com.hybris.plaincontainers.views.sortpopup

import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.views.Popup

class SortPopup(
    invokerView: View,
    private var sortSelection: SortSelection,
    private val onSortChanged: (SortSelection) -> Unit)
: Popup(invokerView, R.layout.popup_list) {

    private val txtTitle = contentView.findViewById<TextView>(R.id.txtPopupListTitle)
    private val list = contentView.findViewById<ListView>(R.id.lvPopupList)

    private val sortOptions: Array<String> = arrayOf(
        invokerView.context.getString(R.string.popup_sort_entry_name),
        invokerView.context.getString(R.string.popup_sort_entry_date_added),
        invokerView.context.getString(R.string.popup_sort_entry_date_modified),
        invokerView.context.getString(R.string.popup_sort_entry_custom)
    )

    private val adapter = SortPopupAdapter(
        invokerView.context,
        sortOptions,
        sortSelection,
        arrayOf(sortOptions.lastIndex),
        onClick = {e -> onSortOptionClicked(e)})

    init {
        list.adapter = adapter
    }

    fun setTitle(text: String) {
        txtTitle.text = text
    }

    private fun onSortOptionClicked(position: Int) {
        Log.d("INFO", "Clicked position $position")

        val selectedPos = sortSelection.option.ordinal

        if(selectedPos == position) {
            sortSelection.isAscending = !sortSelection.isAscending
        } else {
            sortSelection.isAscending = true
            sortSelection.option = SortOption.entries[position]
        }

        onSortChanged(sortSelection)
        adapter.notifyDataSetChanged()
    }

}
package com.hybris.plaincontainers.views.selectionpopup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioButton
import com.hybris.plaincontainers.R

/**
 * Adapter for a selectable ListView, specifically for a SelectionPopup.
 * Takes an array of localized strings for the possible selection values.
 * Populates the ListView with inflated "component_selection" layouts.
 * Sets the RadioButton checks, based on the passed selection index.
 * The onClick callback triggers on each new selection click, ignoring clicks on the same selection.
 */
class SelectionPopupAdapter(
    context: Context,
    private val data: Array<String>,
    private var selection: Int,
    private val onClick: (position: Int) -> Unit
): BaseAdapter() {
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        var view: RadioButton? = convertView as RadioButton?
        if(view == null) {
            view = inflater.inflate(R.layout.component_selection, parent, false) as RadioButton
        }

        view.text = data[position]
        view.isChecked = (position == selection)
        view.setOnClickListener {
            if(position != selection) {
                selection = position
                onClick(position)
                notifyDataSetChanged()
            }
        }

        return view
    }
}
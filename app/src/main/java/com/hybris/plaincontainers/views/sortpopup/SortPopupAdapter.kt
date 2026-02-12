package com.hybris.plaincontainers.views.sortpopup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.hybris.plaincontainers.R

class SortPopupAdapter(
    private val context: Context,
    private val data: Array<String>,
    private var sortSelection: SortSelection,
    private val invisibleIconPositions: Array<Int>,
    private val onClick: (Int) -> Unit
): BaseAdapter() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return data.count()
    }

    override fun getItem(position: Int): Any? {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {

        var view = convertView
        if(view == null) {
            view = inflater.inflate(R.layout.component_row_sort, null)
        }

        val tv = view.findViewById<TextView>(R.id.txtRowSortText)
        tv.text = data[position]

        val iv = view.findViewById<ImageView>(R.id.ivRowSortIcon)
        if(position in invisibleIconPositions) {
            iv.visibility = View.INVISIBLE
        } else {
            @DrawableRes var imageResource: Int
            @StringRes var contentDescriptionId: Int

            if(position == sortSelection.option.ordinal && !sortSelection.isAscending) {
                imageResource = R.drawable.angle_small_up_24
                contentDescriptionId = R.string.accessibility_icon_descending
            } else {
                imageResource = R.drawable.angle_small_down_24
                contentDescriptionId = R.string.accessibility_icon_ascending
            }

            iv.setImageResource(imageResource)
            iv.contentDescription = view.context.getString(contentDescriptionId)
        }

        val layout = view.findViewById<LinearLayout>(R.id.layoutSort)
        layout.setOnClickListener { onClick(position) }

        layout.setBackgroundColor(
            if(position == sortSelection.option.ordinal)
                context.resources.getColor(R.color.backgroundHighlightSelect, null)
            else
                context.resources.getColor(R.color.backgroundHighlight, null)
        )

        return view
    }
}
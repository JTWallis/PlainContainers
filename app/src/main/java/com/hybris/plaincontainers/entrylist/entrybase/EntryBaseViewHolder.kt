package com.hybris.plaincontainers.entrylist.entrybase

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.model.EntryBase

open class EntryBaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvName: TextView = itemView.findViewById(R.id.tvEntryName)
    private val ivThumbnail: ImageView = itemView.findViewById(R.id.ivEntryThumbnail)

    open fun bind(item: EntryBase) {
        tvName.text = item.name
        //ivThumbnail.setImageURI(Uri.parse(item.thumbnailSrc))
    }
}
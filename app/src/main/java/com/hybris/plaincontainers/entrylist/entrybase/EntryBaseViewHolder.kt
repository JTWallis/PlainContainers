package com.hybris.plaincontainers.entrylist.entrybase

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.model.EntryBase
import java.io.File

open class EntryBaseViewHolder<T: EntryBase>(itemView: View, onEntryClick: (pos: Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val tvName: TextView = itemView.findViewById(R.id.tvEntryName)
    private val ivThumbnail: ImageView = itemView.findViewById(R.id.ivEntryThumbnail)

    init {
        tvName.setOnClickListener { onEntryClick(absoluteAdapterPosition) }
        ivThumbnail.setOnClickListener { onEntryClick(absoluteAdapterPosition) }
    }

    open fun bind(item: T) {
        tvName.text = item.name
        //ivThumbnail.setImageURI(Uri.parse(item.thumbnailSrc))
        val file = File(item.thumbnailSrc)
        if(file.exists()) {
            ivThumbnail.setImageURI(Uri.fromFile(file))
        }
    }
}
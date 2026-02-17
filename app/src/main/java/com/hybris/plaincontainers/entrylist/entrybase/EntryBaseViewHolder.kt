package com.hybris.plaincontainers.entrylist.entrybase

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.FileUtils
import com.hybris.plaincontainers.data.entities.EntryBase

open class EntryBaseViewHolder<T: EntryBase>(
    itemView: View,
    onEntryClick: (pos: Int) -> Unit,
    @ColorRes backgroundColorId: Int = R.color.backgroundFill
): RecyclerView.ViewHolder(itemView) {

    private val tvName: TextView = itemView.findViewById(R.id.tvEntryName)
    private val ivThumbnail: ImageView = itemView.findViewById(R.id.ivEntryThumbnail)

    init {
        val entryBase = itemView.findViewById<ConstraintLayout>(R.id.containerEntryBase)
        entryBase.setBackgroundColor(itemView.context.getColor(backgroundColorId))

        tvName.setOnClickListener { onEntryClick(absoluteAdapterPosition) }
        ivThumbnail.setOnClickListener { onEntryClick(absoluteAdapterPosition) }
    }

    open fun bind(item: T) {
        tvName.text = item.name
        //ivThumbnail.setImageURI(Uri.parse(item.thumbnailSrc))

        val uri = item.thumbnailSrc?.toUri()
        if(FileUtils.isValidUri(uri)) {
            ivThumbnail.setImageURI(uri)
        } else {
            ivThumbnail.setImageResource(R.drawable.beans)
        }
    }
}
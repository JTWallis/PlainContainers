package com.hybris.plaincontainers.entrylist.entrybase

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.states.EntryStateBase
import com.hybris.plaincontainers.data.model.EntryBase

open class EntryBaseViewHolder<T: EntryStateBase<EntryBase>>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvName: TextView = itemView.findViewById(R.id.tvEntryName)
    private val ivThumbnail: ImageView = itemView.findViewById(R.id.ivEntryThumbnail)

    open fun bind(item: T) {
        tvName.text = item.model.name
        //ivThumbnail.setImageURI(Uri.parse(item.thumbnailSrc))
    }
}
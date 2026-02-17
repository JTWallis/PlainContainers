package com.hybris.plaincontainers.entrylist.entrybase

import androidx.recyclerview.widget.DiffUtil
import com.hybris.plaincontainers.data.entities.EntryBase

class EntryBaseDiffCallback<T: EntryBase>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.internalId == newItem.internalId
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return (
            oldItem.name == newItem.name &&
            oldItem.thumbnailSrc == newItem.thumbnailSrc &&
            oldItem.description == newItem.description &&
            oldItem.dateModified == newItem.dateModified
        )
    }
}
package com.hybris.plaincontainers.entrylist.entrybase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hybris.plaincontainers.data.entities.EntryBase

abstract class EntryBaseAdapter<T: EntryBase>(
    var entryList: List<T> = emptyList(),
    diffCallback: DiffUtil.ItemCallback<T> = EntryBaseDiffCallback<T>()
) : ListAdapter<T, EntryBaseViewHolder<T>>(diffCallback) {

    abstract fun getResource(): Int
    abstract fun createViewHolder(view: View): EntryBaseViewHolder<T>

    fun setItems(items: List<T>) {
        entryList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryBaseViewHolder<T> {
        val view = LayoutInflater.from(parent.context)
            .inflate(getResource(), parent, false) //EntryBaseComponent(parent.context)

        return createViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryBaseViewHolder<T>, position: Int) {
        val item = entryList[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }
}
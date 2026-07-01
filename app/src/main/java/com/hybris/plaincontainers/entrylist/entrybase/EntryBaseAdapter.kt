package com.hybris.plaincontainers.entrylist.entrybase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hybris.plaincontainers.data.entities.EntryBase

/**
 * ListAdapter base class for RecyclerViews intended for EntryBaseItems,
 * specifically EntryContainer and EntryItem.
 * Binds the entryList data to the RecyclerView items.
 * Subclasses override getLayoutResource to specify the component layouts
 * that the RecyclerView will be populated with.
 */
abstract class EntryBaseAdapter<T: EntryBase>(
    var entryList: List<T> = emptyList(),
    diffCallback: DiffUtil.ItemCallback<T> = EntryBaseDiffCallback<T>()
) : ListAdapter<T, EntryBaseViewHolder<T>>(diffCallback) {

    abstract fun getLayoutResource(): Int
    abstract fun createViewHolder(view: View): EntryBaseViewHolder<T>

    /**
     * Sets the entryList to the passed items and notifies the RecyclerView about the change.
     * @param items List of EntryBase instances
     */
    fun setItems(items: List<T>) {
        entryList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryBaseViewHolder<T> {
        val view = LayoutInflater.from(parent.context)
            .inflate(getLayoutResource(), parent, false) //EntryBaseComponent(parent.context)

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
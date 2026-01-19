package com.hybris.plaincontainers.entrylist.entrybase

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.entrylist.model.EntryBase

abstract class EntryBaseAdapter(
    private val entryList: List<EntryBase>
) : RecyclerView.Adapter<EntryBaseViewHolder>() {

    abstract fun getResource(): Int
    abstract fun createViewHolder(view: View): EntryBaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryBaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(getResource(), parent, false) //EntryBaseComponent(parent.context)

        return createViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryBaseViewHolder, position: Int) {
        Log.d("INFO", "OnBindViewHolder")
        val item = entryList[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }
}
package com.hybris.plaincontainers

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryBaseAdapter(private val entryList: List<EntryBase>)
    : RecyclerView.Adapter<EntryBaseAdapter.EntryBaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryBaseViewHolder {
        val view = EntryBaseComponent(parent.context)

        return EntryBaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryBaseViewHolder, position: Int) {
        val item = entryList[position]

        holder.tvName.text = item.name
        //holder.ivThumbnail.setImageURI(Uri.parse(item.thumbnailSrc))

    }

    override fun getItemCount(): Int {
        return entryList.size
    }


    class EntryBaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvEntryName)
        val ivThumbnail: ImageView = itemView.findViewById(R.id.ivEntryThumbnail)

    }
}
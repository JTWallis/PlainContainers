package com.hybris.plaincontainers

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class EntryBaseComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var layoutEntry : ConstraintLayout
    private var ivThumbnail : ImageView
    private var tvName : TextView

    init {
        inflate(context, R.layout.component_entry, this)

        Log.d("INFO", "Created EntryBaseComponent")

        layoutEntry = findViewById(R.id.layoutEntryBase)
        ivThumbnail = findViewById(R.id.ivEntryThumbnail)
        tvName = findViewById(R.id.tvEntryName)

        layoutEntry.setOnClickListener { onLayoutEntryClicked() }
    }

    fun onLayoutEntryClicked() {
        Log.d("INFO", "Clicked " + tvName.text)
    }
}
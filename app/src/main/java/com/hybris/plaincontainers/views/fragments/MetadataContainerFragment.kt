package com.hybris.plaincontainers.views.fragments

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hybris.plaincontainers.R

abstract class MetadataContainerFragment: MetadataBaseFragment() {

    private lateinit var tvColor: TextView
    private lateinit var ivColorIcon: ImageView
    private lateinit var viewColorPick: View

    protected abstract fun getInitColor(): String

    override fun initViews(view: View) {
        tvColor = view.findViewById(R.id.tvEditColor)
        ivColorIcon = view.findViewById(R.id.ivEditColor)
        viewColorPick = view.findViewById(R.id.viewEditColor)

        super.initViews(view)
    }

    override fun initViewFillData() {
        super.initViewFillData()

        //viewColorPick.setBackgroundColor()
    }

    override fun initListeners() {
        super.initListeners()

        ivColorIcon.setOnClickListener { onColorClicked() }
        viewColorPick.setOnClickListener { onColorClicked() }
    }

    private fun onColorClicked() {

    }

    protected fun getColor(): String {
        return "#FFF"
    }
}
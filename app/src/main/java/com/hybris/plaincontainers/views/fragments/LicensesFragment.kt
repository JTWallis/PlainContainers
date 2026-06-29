package com.hybris.plaincontainers.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RawRes
import androidx.fragment.app.Fragment
import com.hybris.plaincontainers.R

class LicensesFragment: Fragment(R.layout.fragment_licenses) {
    private lateinit var tvColorPicker: TextView
    private lateinit var tvFlaticons: TextView
    private lateinit var tvApache: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvColorPicker = view.findViewById(R.id.tvLicensesColorPicker)
        tvFlaticons = view.findViewById(R.id.tvLicensesFlatIcons)
        tvApache = view.findViewById(R.id.tvLicensesApache)

        populate(tvColorPicker, R.raw.skydoves_colorpicker_license)
        populate(tvFlaticons, R.raw.flaticons_license)
        populate(tvApache, R.raw.apache_license_2_0)
    }

    private fun populate(view: TextView, @RawRes resource: Int) {
        val inputStream = resources.openRawResource(resource)
        val text = inputStream.bufferedReader().use { it.readText() }
        view.text = text
        inputStream.close()
    }
}
package com.hybris.plaincontainers.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R

class AboutFragment: Fragment(R.layout.fragment_about) {
    private lateinit var btnLicenses: Button
    private lateinit var btnVersion: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListeners()
    }

    private fun initViews(view: View) {
        btnLicenses = view.findViewById(R.id.btnAboutLicenses)
        btnVersion = view.findViewById(R.id.btnAboutVersion)

        val version = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0).versionName
        btnVersion.text = "${btnVersion.text} $version"
    }

    private fun initListeners() {
        btnLicenses.setOnClickListener {
            findNavController().navigate(R.id.action_about_to_licenses)
        }
    }

}
package com.hybris.plaincontainers.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.viewmodels.AppBarViewModel
import kotlin.getValue

class AboutFragment: Fragment(R.layout.fragment_about) {
    private val appbarVm: AppBarViewModel by activityViewModels()
    private lateinit var btnLicenses: Button
    private lateinit var btnVersion: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListeners()

        appbarVm.setModelEmpty()
    }

    private fun initViews(view: View) {
        btnLicenses = view.findViewById(R.id.btnAboutLicenses)
        btnVersion = view.findViewById(R.id.btnAboutVersion)

        val version = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0).versionName
        btnVersion.text = getString(R.string.about_version, version)
    }

    private fun initListeners() {
        btnLicenses.setOnClickListener {
            findNavController().navigate(R.id.action_about_to_licenses)
        }
    }

}
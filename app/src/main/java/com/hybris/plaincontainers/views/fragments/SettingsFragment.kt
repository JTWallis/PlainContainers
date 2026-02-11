package com.hybris.plaincontainers.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hybris.plaincontainers.R

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    private lateinit var btnChangeLanguage: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListeners()
    }

    private fun initViews(view: View) {
        btnChangeLanguage = view.findViewById(R.id.btnSettingsLanguage)
    }

    private fun initListeners() {
        btnChangeLanguage.setOnClickListener { onBtnChangeLanguageClick() }
    }

    private fun onBtnChangeLanguageClick() {

    }


}
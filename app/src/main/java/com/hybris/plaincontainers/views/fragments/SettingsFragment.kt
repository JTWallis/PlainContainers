package com.hybris.plaincontainers.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.ItemCountZeroBehavior
import com.hybris.plaincontainers.data.LocaleUtils
import com.hybris.plaincontainers.data.SettingsManager
import com.hybris.plaincontainers.data.SupportedLocale
import com.hybris.plaincontainers.data.appbar.AppBarViewModel
import com.hybris.plaincontainers.views.selectionpopup.SelectionPopup
import kotlin.getValue

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    private val appbarVm: AppBarViewModel by activityViewModels()
    private lateinit var btnChangeLanguage: Button
    private lateinit var btnChangeZeroItemCount: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListeners()

        appbarVm.setModelEmpty()
    }

    private fun initViews(view: View) {
        btnChangeLanguage = view.findViewById(R.id.btnSettingsLanguage)
        btnChangeZeroItemCount = view.findViewById(R.id.btnChangeZeroItemCount)
    }

    private fun initListeners() {
        btnChangeLanguage.setOnClickListener { onBtnChangeLanguageClick() }
        btnChangeZeroItemCount.setOnClickListener { onBtnChangeZeroItemCountClick() }
    }

    private fun onBtnChangeLanguageClick() {
        val data: Array<String> = SupportedLocale.entries.map { e ->
            requireContext().getString(e.toLocalizationId())
        }.toTypedArray()
        val currentLocale = SettingsManager.getLocale()
        val initSelection = SupportedLocale.entries.indexOf(currentLocale)

        val popup = SelectionPopup(
            btnChangeLanguage,
            requireContext().getString(R.string.settings_general_language),
            data,
            initSelection,
            onSortSelectionConfirm = { pos -> onChangeLanguage(pos)}
        )

        popup.show(btnChangeLanguage)
    }

    private fun onBtnChangeZeroItemCountClick() {
        val data: Array<String> = ItemCountZeroBehavior.entries.map { e ->
            requireContext().getString(e.toLocalizationId())
        }.toTypedArray()

        val currentBehavior = SettingsManager.getItemCountZeroBehavior()
        val initSelection = ItemCountZeroBehavior.entries.indexOf(currentBehavior)

        val popup = SelectionPopup(
            btnChangeZeroItemCount,
            requireContext().getString(R.string.settings_operations_item_count_zero),
            data,
            initSelection,
            onSortSelectionConfirm = { pos -> onChangeZeroItemCount(pos)}
        )

        popup.show(btnChangeZeroItemCount)
    }

    private fun onChangeLanguage(localePos: Int) {
        val locale = SupportedLocale.entries[localePos]

        SettingsManager.setLocale(locale)
        LocaleUtils.setLocale(requireContext(), locale)
        requireActivity().recreate()
    }

    private fun onChangeZeroItemCount(enumPos: Int) {
        val behavior = ItemCountZeroBehavior.entries[enumPos]
        SettingsManager.setItemCountZeroBehavior(behavior)
    }

}
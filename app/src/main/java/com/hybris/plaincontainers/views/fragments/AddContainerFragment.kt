package com.hybris.plaincontainers.views.fragments

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.model.EntryContainer
import java.io.Serializable

class AddContainerFragment: MetadataContainerFragment() {

    override fun initPackageData() {}

    override fun initAppbarTitles() {
        labelAppbarTitle = requireContext().getString(R.string.appbar_title_add_container)
    }

    override fun hasBtnDelete(): Boolean {
        return false
    }

    override fun onBtnConfirmClick() {
        if(getName().isEmpty()) {
            return
        }

        val entryContainer = EntryContainer(
            getName(),
            getPhotoUri(),
            getColor()
        )

        JsonManager.addContainer(entryContainer)
        findNavController().navigateUp()
    }

    override fun getInitName(): String {
        return ""
    }

    override fun getInitImageUri(): String {
        return ""
    }

    override fun getInitDescription(): String {
        return ""
    }

    override fun getInitColor(): Int {
        // TODO: Add default color instead of 0
        return 0
    }

    override fun getContainerPackage(): Serializable {
        return object: Serializable {}
    }
}
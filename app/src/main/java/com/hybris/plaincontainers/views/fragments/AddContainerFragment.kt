package com.hybris.plaincontainers.views.fragments

import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.model.EntryContainer
import java.io.Serializable

class AddContainerFragment: MetadataContainerFragment() {


    override fun initPackageData() {}

    override fun hasBtnDelete(): Boolean {
        return false
    }

    override fun onBtnDeleteClick() {}

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

    override fun getAppbarTitle(): String {
        return "Add new Container"
    }

    override fun getAppbarSubtitle(): String {
        return ""
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

    override fun getInitColor(): String {
        return ""
    }

    override fun getContainerPackage(): Serializable {
        return object: Serializable {}
    }
}
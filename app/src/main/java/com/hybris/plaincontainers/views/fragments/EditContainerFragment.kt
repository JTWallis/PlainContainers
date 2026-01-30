package com.hybris.plaincontainers.views.fragments

import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.fragmentargs.EditContainerFragmentArg
import com.hybris.plaincontainers.data.model.EntryContainer
import java.io.Serializable

class EditContainerFragment(): MetadataContainerFragment() {

    private var containerPos: Int = -1
    private lateinit var containerMetadata: EntryContainer

    override fun initPackageData() {
        val fragArgs = getContainerPackage() as EditContainerFragmentArg
        containerPos = fragArgs.containerPos
        containerMetadata = JsonManager.getContainer(containerPos)
    }

    fun hasIdenticalValues(): Boolean {
        return containerMetadata.name == getName() &&
                containerMetadata.thumbnailSrc == getPhotoUri() &&
                //containerMetadata.description == getDescription() &&
                containerMetadata.color == getColor()
    }

    override fun hasBtnDelete(): Boolean {
        return true
    }

    override fun onBtnConfirmClick() {
        if(getName().isEmpty()) {
            // TODO: Throw Warning popup
            return
        }

        if(!hasIdenticalValues()) {
            val container = EntryContainer(
                getName(),
                getPhotoUri(),
                getColor(),
                containerMetadata.sortParams,
                containerMetadata.items
            )

            JsonManager.writeContainer(containerPos, container)
        }

        findNavController().navigateUp()
    }

    override fun onBtnDeleteClick() {

    }

    override fun getAppbarTitle(): String {
        return "Edit Container Info"
    }

    override fun getAppbarSubtitle(): String {
        return containerMetadata.name
    }

    override fun getInitName(): String {
        return containerMetadata.name
    }

    override fun getInitImageUri(): String {
        return containerMetadata.thumbnailSrc
    }

    override fun getInitDescription(): String {
        //return containerMetadata.description
        return ""
    }

    override fun getInitColor(): String {
        return containerMetadata.color
    }

    override fun getContainerPackage(): Serializable {
        return getSerializable("edit_container_frag_arg", EditContainerFragmentArg::class.java)
    }
}
package com.hybris.plaincontainers.views.fragments

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.fragmentargs.EditContainerFragmentArg
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.views.choicepopup.ChoicePopup
import java.io.Serializable

class EditContainerFragment(): MetadataContainerFragment() {

    private var containerPos: Int = -1
    private lateinit var containerMetadata: EntryContainer

    override fun initPackageData() {
        val fragArgs = getContainerPackage() as EditContainerFragmentArg
        containerPos = fragArgs.containerPos
        containerMetadata = JsonManager.getContainer(containerPos)
    }

    override fun initAppbarTitles() {
        labelAppbarTitle = requireContext().getString(R.string.appbar_title_edit_container)
        labelAppbarSubtitle = containerMetadata.name
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

    override fun onBtnDeleteClick(view: View) {
        val popup = ChoicePopup(
            view,
            onClickLeft = { },
            onClickRight = { onBtnDeleteConfirmClick() },
            true
        )
        popup.setTextTitle("Do you really want to delete this container, including its item entries?")
        popup.setTextSubtitle("This action is irreversible!")
        popup.setTextButtonLeft("Cancel")
        popup.setTextButtonRight("Delete!")

        popup.show(view)
    }

    private fun onBtnDeleteConfirmClick() {
        JsonManager.removeContainer(containerPos)
        findNavController().popBackStack(R.id.containerOverviewFragment, false)
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

    override fun getInitColor(): Int {
        return containerMetadata.color
    }

    override fun getContainerPackage(): Serializable {
        return getSerializable("edit_container_frag_arg", EditContainerFragmentArg::class.java)
    }
}
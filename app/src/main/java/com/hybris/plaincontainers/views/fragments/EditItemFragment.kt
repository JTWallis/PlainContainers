package com.hybris.plaincontainers.views.fragments

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.fragmentargs.EditItemFragmentArg
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.views.choicepopup.ChoicePopup
import java.io.Serializable

class EditItemFragment: MetadataBaseFragment() {
    private var containerPos = -1
    private var itemPos = -1
    private lateinit var itemMetadata: EntryItem

    override fun initPackageData() {
        val fragArg = getContainerPackage() as EditItemFragmentArg
        containerPos = fragArg.containerPos
        itemPos = fragArg.itemPos

        itemMetadata = JsonManager.getItem(containerPos, itemPos)
    }

    override fun initAppbarTitles() {
        labelAppbarTitle = requireContext().getString(R.string.appbar_title_edit_item)
        labelAppbarSubtitle = itemMetadata.name
    }

    private fun hasIdenticalValues(): Boolean {
        return itemMetadata.name == getName() &&
                itemMetadata.thumbnailSrc == getPhotoUri()
                //containerMetadata.description == getDescription() &&
    }

    override fun onBtnConfirmClick() {
        if(getName().isEmpty()) {
            return
        }

        if(!hasIdenticalValues()) {
            val item = EntryItem(
                getName(),
                getPhotoUri(),
                itemMetadata.amount
            )

            JsonManager.writeItem(containerPos, itemPos, item)
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
        popup.setTextTitle("Do you really want to delete this item from its container?")
        popup.setTextSubtitle("This action is irreversible!")
        popup.setTextButtonLeft("Cancel")
        popup.setTextButtonRight("Delete!")

        popup.show(view)
    }

    private fun onBtnDeleteConfirmClick() {
        JsonManager.removeItem(containerPos, itemPos)
        findNavController().navigateUp()
    }

    override fun getInitName(): String {
        return itemMetadata.name
    }

    override fun getInitImageUri(): String {
        return itemMetadata.thumbnailSrc
    }

    override fun getInitDescription(): String {
        return ""
    }

    override fun getContainerPackage(): Serializable {
        return getSerializable("edit_item_frag_arg", EditItemFragmentArg::class.java)
    }

    override fun hasBtnDelete(): Boolean {
        return true
    }
}
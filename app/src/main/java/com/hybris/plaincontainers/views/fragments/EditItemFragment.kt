package com.hybris.plaincontainers.views.fragments

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.appbar.AppBarModel
import com.hybris.plaincontainers.data.fragmentargs.EditItemFragmentArg
import com.hybris.plaincontainers.data.entities.EntryItem
import com.hybris.plaincontainers.data.viewmodels.EditItemViewModel
import com.hybris.plaincontainers.views.choicepopup.ChoicePopup
import kotlinx.coroutines.launch
import java.io.Serializable

class EditItemFragment() : MetadataBaseFragment() {
    private var containerId: Long = -1
    private var itemId: Long = -1
    private lateinit var itemMetadata: EntryItem
    private lateinit var viewModel: EditItemViewModel

    override fun initPackageData() {
        val fragArg = getContainerPackage() as EditItemFragmentArg
        containerId = fragArg.containerId
        itemId = fragArg.itemId

        viewModel = EditItemViewModel(containerId, itemId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.item.collect { item ->
                    itemMetadata = item
                    initAppbarSubtitle()
                }
            }
        }
    }

    override fun initAppbarSubtitle() {
        appbarVm.model.value = AppBarModel(
            subtitle = itemMetadata.name
        )
    }

    private fun hasIdenticalValues(): Boolean {
        return itemMetadata.name == getName() &&
                itemMetadata.thumbnailSrc == getPhotoUri() &&
                itemMetadata.description == getDescription()
    }

    override fun onBtnConfirmClick() {
        if(getName().isEmpty()) {
            return
        }

        val dateModified = System.currentTimeMillis().toInt()

        if(!hasIdenticalValues()) {
            val item = EntryItem(
                itemMetadata.itemId,
                getName(),
                getPhotoUri(),
                getDescription(),
                itemMetadata.dateAdded,
                dateModified,
            )

            viewModel.update(item)
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
        popup.setTextTitle(requireContext().getString(R.string.metadata_popup_delete_title_item))
        popup.setTextSubtitle(requireContext().getString(R.string.metadata_popup_delete_subtitle))
        popup.setTextButtonLeft(requireContext().getString(R.string.metadata_popup_delete_cancel))
        popup.setTextButtonRight(requireContext().getString(R.string.metadata_popup_delete_confirm))

        popup.show(view)
    }

    private fun onBtnDeleteConfirmClick() {
        viewModel.delete(itemMetadata)
        findNavController().navigateUp()
    }

    override fun getInitName(): String {
        return itemMetadata.name
    }

    override fun getInitImageUri(): String {
        return itemMetadata.thumbnailSrc ?: ""
    }

    override fun getInitDescription(): String {
        return itemMetadata.description ?: ""
    }

    override fun getContainerPackage(): Serializable {
        return getSerializable("edit_item_frag_arg", EditItemFragmentArg::class.java)
    }

    override fun hasBtnDelete(): Boolean {
        return true
    }
}
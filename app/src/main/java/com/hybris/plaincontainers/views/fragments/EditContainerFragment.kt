package com.hybris.plaincontainers.views.fragments

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.appbar.AppBarModel
import com.hybris.plaincontainers.data.builders.EntryContainerBuilder
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.data.fragmentargs.EditContainerFragmentArg
import com.hybris.plaincontainers.data.viewmodels.EditContainerViewModel
import com.hybris.plaincontainers.views.choicepopup.ChoicePopup
import kotlinx.coroutines.launch
import java.io.Serializable

class EditContainerFragment(): MetadataContainerFragment() {

    private var containerId: Long = -1
    private lateinit var containerMetadata: EntryContainer
    override lateinit var viewModel: EditContainerViewModel

    override fun initPackageData() {
        val fragArgs = getContainerPackage() as EditContainerFragmentArg
        containerId = fragArgs.containerId

        viewModel = EditContainerViewModel(containerId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.container.collect { container ->
                    if(container != null) {
                        containerMetadata = container
                        initAppbarSubtitle()
                    } else {
                        throw NullPointerException("Container is null!! containerId: $containerId")
                    }
                }
            }
        }
    }

    override fun initAppbarSubtitle() {
        labelAppbarSubtitle = containerMetadata.name
    }

    fun hasIdenticalValues(): Boolean {
        return containerMetadata.name == getName() &&
                containerMetadata.thumbnailSrc == getPhotoUri() &&
                containerMetadata.description == getDescription() &&
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

        val dateModified = System.currentTimeMillis().toInt()

        if(!hasIdenticalValues()) {
            val container = EntryContainerBuilder.from(
                containerMetadata,
                name = getName(),
                thumbnailSrc = getPhotoUri(),
                description = getDescription(),
                dateModified = dateModified,
                color = getColor()
            )

            viewModel.updateContainer(container)
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
        popup.setTextTitle(requireContext().getString(R.string.metadata_popup_delete_title_container))
        popup.setTextSubtitle(requireContext().getString(R.string.metadata_popup_delete_subtitle))
        popup.setTextButtonLeft(requireContext().getString(R.string.metadata_popup_delete_cancel))
        popup.setTextButtonRight(requireContext().getString(R.string.metadata_popup_delete_confirm))

        popup.show(view)
    }

    private fun onBtnDeleteConfirmClick() {
        viewModel.deleteContainer(containerMetadata)

        findNavController().popBackStack(R.id.containerOverviewFragment, false)
    }

    override fun getInitName(): String {
        return containerMetadata.name
    }

    override fun getInitImageUri(): String {
        return containerMetadata.thumbnailSrc ?: ""
    }

    override fun getInitDescription(): String {
        return containerMetadata.description ?: ""
    }

    override fun getInitColor(): Int {
        return containerMetadata.color ?: 100
    }

    override fun getContainerPackage(): Serializable {
        return getSerializable("edit_container_frag_arg", EditContainerFragmentArg::class.java)
    }
}
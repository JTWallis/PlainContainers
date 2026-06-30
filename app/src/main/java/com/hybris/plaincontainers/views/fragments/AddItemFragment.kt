package com.hybris.plaincontainers.views.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.fragmentargs.AddItemFragmentArg
import com.hybris.plaincontainers.data.entities.EntryItem
import com.hybris.plaincontainers.data.model.BarcodeMetadata
import com.hybris.plaincontainers.data.viewmodels.AddItemViewModel
import com.hybris.plaincontainers.views.warningpopup.WarningPopup
import java.io.Serializable

class AddItemFragment() : MetadataBaseFragment() {
    private var containerId: Long = -1
    private lateinit var viewModel: AddItemViewModel
    private var navigateBarcodeFrag: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewFillData()

        if(navigateBarcodeFrag) {
            findNavController().navigate(R.id.action_add_item_to_barcode)
        }
    }

    override fun initPackageData() {
        val fragArgs = getContainerPackage() as AddItemFragmentArg
        containerId = fragArgs.containerId
        navigateBarcodeFrag = fragArgs.navigateBarcodeFrag
        fragArgs.navigateBarcodeFrag = false

        viewModel = AddItemViewModel(containerId)
    }

    override fun initListeners() {
        super.initListeners()

        parentFragmentManager.setFragmentResultListener(
            "barcode_result",
            this
        ) {_, bundle ->
            val text = bundle.getString("barcode_text")
            if(text != null) onBarcodeReceived(text)
        }
    }

    private fun onBarcodeReceived(barcode: String) {
        viewModel.fetchBarcodeMetadata(
            barcode,
            { metadata -> onBarcodeMetadataSuccess(metadata) },
            { error -> onBarcodeMetadataFail(error)}
        )

        viewModel.fetchBarcodeThumbnail(
            requireContext(),
            barcode,
            { uri -> onBarcodeThumbnailSuccess(uri) },
            { error -> onBarcodeThumbnailFail(error) }
        )
    }

    private fun onBarcodeMetadataSuccess(metadata: BarcodeMetadata) {
        setName(metadata.productName)
        setDescription(metadata.productDescription)
    }

    private fun onBarcodeThumbnailSuccess(uri: Uri) {
        setPhotoUri(uri)
    }

    private fun onBarcodeMetadataFail(error: String) {
        Log.w("AddItemFragment", "onBarcodeMetadataFail: Fetch barcode metadata fail $error")

        val popup = WarningPopup(requireView(), requireContext().getString(R.string.popup_warning_barcode_metadata_fail))
        popup.show(requireView())
    }

    private fun onBarcodeThumbnailFail(error: String) {
        Log.w("AddItemFragment", "onBarcodeThumbnailFail: Fetch barcode thumbnail fail $error")
    }

    override fun onBtnConfirmClick() {
        super.onBtnConfirmClick()
        if(getName().isEmpty()) return

        val date = System.currentTimeMillis().toInt()

        val entryItem = EntryItem(
            0,
            getName(),
            getPhotoUri(),
            getDescription(),
            date,
            date
        )

        viewModel.insertToContainer(entryItem)
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

    override fun getContainerPackage(): Serializable {
        return getSerializable("add_item_frag_arg", AddItemFragmentArg::class.java)
    }

    override fun hasBtnDelete(): Boolean {
        return false
    }
}
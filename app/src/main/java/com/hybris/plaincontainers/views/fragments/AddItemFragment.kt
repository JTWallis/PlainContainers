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

/**
 * Logic side of the "fragment_edit" layout, specifically for adding an item for a container.
 * Extends the MetadataContainerFragment base class,
 * by coupling the populated metadata with a database operation,
 * to insert a new EntryItem via CrossRef into an EntryContainer on successful confirm button click.
 *
 * Additionally, this class handles the quick-add by Barcode functionality.
 * If this fragment was navigated to, via setting a flag in ContainerDetailsFragment,
 * the navigation continues to BarcodeFragment and ultimately back to this fragment,
 * along a possible barcode-text result.
 * On no barcode-text result, there is no further behaviour.
 * On barcode-text result though, two HTTP calls are made to the API,
 * to fetch the possible barcode-metadata and -thumbnail.
 * On successful fetches, the Name, Description and Photo Views are populated accordingly.
 * On failed barcode-metadata fetch, a Warning Popup is shown,
 * notifying the user that data exists for this barcode-text.
 */
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
        val fragArgs = getFragmentArg() as AddItemFragmentArg
        containerId = fragArgs.containerId
        navigateBarcodeFrag = fragArgs.navigateBarcodeFrag
        fragArgs.navigateBarcodeFrag = false

        viewModel = AddItemViewModel(containerId)
    }

    override fun initListeners() {
        super.initListeners()

        parentFragmentManager.setFragmentResultListener(
            getString(R.string.frag_result_barcode_result_request),
            this
        ) {_, bundle ->
            val text = bundle.getString(getString(R.string.frag_result_barcode_result_text))
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

    override fun getFragmentArg(): Serializable {
        return getSerializable(
            getString(R.string.frag_arg_add_item),
            AddItemFragmentArg::class.java
        )
    }

    override fun hasBtnDelete(): Boolean {
        return false
    }
}
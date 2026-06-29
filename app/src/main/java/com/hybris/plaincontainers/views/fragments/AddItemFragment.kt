package com.hybris.plaincontainers.views.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.data.fragmentargs.AddItemFragmentArg
import com.hybris.plaincontainers.data.entities.EntryItem
import com.hybris.plaincontainers.data.viewmodels.AddItemViewModel
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

    override fun onBtnConfirmClick() {
        if(getName().isEmpty()) {
            return
        }

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
package com.hybris.plaincontainers.views.fragments

import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.fragmentargs.AddItemFragmentArg
import com.hybris.plaincontainers.data.model.EntryItem
import java.io.Serializable

class AddItemFragment: MetadataBaseFragment() {
    private var containerPos = -1

    override fun initPackageData() {
        val fragArgs = getContainerPackage() as AddItemFragmentArg
        containerPos = fragArgs.containerPos
    }

    override fun onBtnConfirmClick() {
        if(getName().isEmpty()) {
            return
        }

        val date = System.currentTimeMillis().toInt()

        val entryItem = EntryItem(
            getName(),
            getPhotoUri(),
            date,
            date,
            1
        )

        JsonManager.addItem(containerPos, entryItem)
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
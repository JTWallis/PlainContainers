package com.hybris.plaincontainers.views.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.data.entities.EntryContainer
import com.hybris.plaincontainers.data.viewmodels.AddContainerViewModel
import com.hybris.plaincontainers.views.sortpopup.SortOption
import java.io.Serializable

/**
 * Logic side of the "fragment_edit" layout, specifically for adding a container.
 * Extends the MetadataContainerFragment base class,
 * by coupling the populated metadata with a database operation,
 * to insert a new EntryContainer on successful confirm button click.
 */
class AddContainerFragment: MetadataContainerFragment() {

    private val viewModel = AddContainerViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewFillData()
    }

    override fun initPackageData() {}

    override fun hasBtnDelete(): Boolean {
        return false
    }

    override fun onBtnConfirmClick() {
        super.onBtnConfirmClick()
        if(getName().isEmpty()) return

        val date = System.currentTimeMillis().toInt()

        val containerEntity = EntryContainer(
            0,
            getName(),
            getPhotoUri(),
            getDescription(),
            date,
            date,
            getColor(),
            SortOption.NAME.ordinal,
            true,
            0
        )

        viewModel.insertContainer(containerEntity)

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

    override fun getInitColor(): Int {
        return 0
    }

    override fun getFragmentArg(): Serializable {
        return object: Serializable {}
    }
}
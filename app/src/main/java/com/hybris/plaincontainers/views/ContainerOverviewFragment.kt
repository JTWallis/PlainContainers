package com.hybris.plaincontainers.views

import android.os.Bundle
import com.hybris.plaincontainers.data.model.EntryContainer

import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.buttoniconlabeled.EditHandle
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.appbar.AppBarModel
import com.hybris.plaincontainers.data.fragmentargs.ContainerFragmentArg
import com.hybris.plaincontainers.data.fragmentargs.RootFragmentArg
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydragexpand.EntryDragExpandAdapter
import java.io.Serializable

class ContainerOverviewFragment(): ContainerBaseFragment<EntryContainer>() {
    private lateinit var layoutBtnEdit: CardView
    private lateinit var handleEdit: EditHandle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appbarVm.model.value = AppBarModel(
            title = "Container Overview",
            subtitle = "Test"
        )
    }

    override fun initViews(view: View) {
        super.initViews(view)

        layoutBtnEdit = view.findViewById(R.id.layoutEdit)
        handleEdit = EditHandle(layoutBtnEdit, onClick = {}, "Edit Container")
    }

    override fun createAdapter(dragListener: DragListener) {
        rcvAdapter = EntryDragExpandAdapter(onEntryClick = {pos -> onItemEntryClicked(pos)}, dragListener)
        rcvAdapter.setItems(listItems)
    }

    override fun writeJsonChanges() {
        JsonManager.writeContainers(listItems)
    }

    override fun getContainerPackage(): Serializable {
        return getSerializable("root_frag_arg", RootFragmentArg::class.java)
    }

}
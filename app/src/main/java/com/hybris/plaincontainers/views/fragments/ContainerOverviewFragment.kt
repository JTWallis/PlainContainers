package com.hybris.plaincontainers.views.fragments

import android.util.Log
import com.hybris.plaincontainers.data.model.EntryContainer

import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.buttoniconlabeled.EditHandle
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.fragmentargs.ContainerFragmentArg
import com.hybris.plaincontainers.data.fragmentargs.RootFragmentArg
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydragexpand.EntryDragExpandAdapter
import com.hybris.plaincontainers.views.sortpopup.SortSelection
import java.io.Serializable

class ContainerOverviewFragment(): ContainerBaseFragment<EntryContainer>() {
    override lateinit var listItems: MutableList<EntryContainer>
    override lateinit var sortParams: SortSelection
    private lateinit var layoutBtnEdit: CardView
    private lateinit var handleEdit: EditHandle

    override fun initViews(view: View) {
        super.initViews(view)

        layoutBtnEdit = view.findViewById(R.id.layoutEdit)
        handleEdit = EditHandle(layoutBtnEdit, onClick = {}, "Edit Container")
    }

    override fun initPackageData() {
        val rootContainer = JsonManager.getRoot()
        listItems = ArrayList(rootContainer.containers)
        sortParams = rootContainer.sortParams
    }

    override fun createAdapter(dragListener: DragListener) {
        rcvAdapter = EntryDragExpandAdapter(onEntryClick = {pos -> onItemEntryClicked(pos)}, dragListener)
        rcvAdapter.setItems(listItems)
    }

    override fun writeJsonChanges() {
        JsonManager.writeContainers(listItems)
    }

    override fun onItemEntryClicked(listPosition: Int) {
        val item = listItems[listPosition]
        val pack = ContainerFragmentArg(
            listPosition
        )

        val bundle = bundleOf("container_frag_arg" to pack)

        findNavController().navigate(R.id.action_overview_to_details, args = bundle)
    }

    override fun getAppbarTitle(): String {
        return "Container Overview"
    }

    override fun getAppbarSubtitle(): String {
        return ""
    }

    override fun getContainerPackage(): Serializable {
        return getSerializable("root_frag_arg", RootFragmentArg::class.java)
    }

}
package com.hybris.plaincontainers.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.cardview.widget.CardView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.buttoniconlabeled.EditHandle
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.appbar.AppBarModel
import com.hybris.plaincontainers.data.fragmentargs.ContainerFragmentArg
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydragincrement.EntryDragIncrementAdapter
import java.io.Serializable

class ContainerDetailsFragment(): ContainerBaseFragment<EntryItem>() {
    override lateinit var listItems: MutableList<EntryItem>
    override lateinit var sortParams: SortSelection
    private var containerPos: Int = -1
    private var containerName = ""
    private lateinit var layoutBtnEdit: CardView
    private lateinit var handleEdit: EditHandle


    override fun initPackageData() {
        super.initPackageData()

        val containerPackage = getContainerPackage() as ContainerFragmentArg
        containerPos = containerPackage.listPosition
        containerName = containerPackage.metadata.name
    }

    override fun initViews(view: View) {
        super.initViews(view)

        layoutBtnEdit = view.findViewById(R.id.layoutEdit)
        handleEdit = EditHandle(layoutBtnEdit, onClick = {}, "Edit Container")
    }

    override fun createAdapter(dragListener: DragListener) {
        rcvAdapter = EntryDragIncrementAdapter(
            onEntryClick = {pos -> onItemEntryClicked(pos) },
            dragListener,
            onItemCountChange = {_,_ -> }
        )
        rcvAdapter.setItems(listItems)
    }

    override fun writeJsonChanges() {
        JsonManager.writeItems(listItems, containerPos)
    }

    override fun onItemEntryClicked(listPosition: Int) {
        Log.d("INFO", "Clicked $listPosition")
    }

    override fun getAppbarTitle(): String {
        return "Container"
    }

    override fun getAppbarSubtitle(): String {
        return containerMetadata.name
    }

    override fun getContainerPackage(): Serializable {
        return getSerializable("container_frag_arg", ContainerFragmentArg::class.java)
    }

}
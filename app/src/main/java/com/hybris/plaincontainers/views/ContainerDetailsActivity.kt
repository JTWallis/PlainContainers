package com.hybris.plaincontainers.views

import androidx.cardview.widget.CardView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.buttoniconlabeled.EditHandle
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydragincrement.EntryDragIncrementAdapter

class ContainerDetailsActivity(): ContainerBaseActivity<EntryItem>() {

    private var containerPos: Int = -1
    private lateinit var layoutBtnEdit: CardView
    private lateinit var handleEdit: EditHandle

    override fun initPackageData() {
        super.initPackageData()

        val containerPackage = getContainerPackage()
        containerPos = containerPackage.containerPos
    }

    override fun initViews() {
        super.initViews()

        layoutBtnEdit = findViewById(R.id.layoutEdit)
        handleEdit = EditHandle(layoutBtnEdit, onClick = {}, "Edit Container")
    }

    override fun createAdapter(dragListener: DragListener) {
        rcvAdapter = EntryDragIncrementAdapter(dragListener, onItemCountChange = {_,_ -> })
        rcvAdapter.setItems(listItems)
    }

    override fun writeJsonChanges() {
        JsonManager.writeItems(listItems, containerPos)
    }

}
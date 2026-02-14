package com.hybris.plaincontainers.views.fragments

import android.util.Log
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.buttoniconlabeled.EditHandle
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.fragmentargs.AddItemFragmentArg
import com.hybris.plaincontainers.data.fragmentargs.ContainerFragmentArg
import com.hybris.plaincontainers.data.fragmentargs.EditContainerFragmentArg
import com.hybris.plaincontainers.data.fragmentargs.EditItemFragmentArg
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydragincrement.EntryDragIncrementAdapter
import com.hybris.plaincontainers.views.choicepopup.ChoicePopup
import com.hybris.plaincontainers.views.sortpopup.SortSelection
import java.io.Serializable

class ContainerDetailsFragment(): ContainerBaseFragment<EntryItem>() {
    override lateinit var listItems: MutableList<EntryItem>
    override lateinit var sortParams: SortSelection
    private var containerPos: Int = -1
    private lateinit var containerMetadata: EntryContainer
    override val labelBtnAdd = R.string.details_btn_add


    override fun initPackageData() {
        val containerPackage = getContainerPackage() as ContainerFragmentArg
        containerPos = containerPackage.listPosition

        containerMetadata = JsonManager.getContainer(containerPos)
        listItems = containerMetadata.items.toMutableList()
        sortParams = containerMetadata.sortParams
    }

    override fun initAppbarSubtitle() {
        labelAppbarSubtitle = containerMetadata.name
    }

    override fun createAdapter(dragListener: DragListener) {
        rcvAdapter = EntryDragIncrementAdapter(
            onEntryClick = {pos -> onItemEntryClicked(pos) },
            dragListener,
            onItemCountChange = { pos, addVal -> onItemCountChanged(pos, addVal) }
        )
        rcvAdapter.setItems(listItems)
    }

    override fun writeJsonChanges() {
        JsonManager.writeItems(containerPos, listItems)
    }

    override fun onBtnEditClicked() {
        val fragArg = EditContainerFragmentArg(
            containerPos
        )

        val bundle = bundleOf("edit_container_frag_arg" to fragArg)

        findNavController().navigate(R.id.action_detail_to_edit_container, args = bundle)
    }

    override fun onBtnAddClicked(view: View) {
        val popup = ChoicePopup(
            view,
            onClickLeft = { onBtnAddManualClicked() },
            onClickRight = { onBtnAddBarcodeClicked() }
        )
        popup.setTextTitle(requireContext().getString(R.string.details_popup_add_title))
        popup.setTextSubtitle("")
        popup.setTextButtonLeft(requireContext().getString(R.string.details_popup_add_btn_manual))
        popup.setTextButtonRight(requireContext().getString(R.string.details_popup_add_btn_barcode))

        popup.show(view)
    }

    override fun onItemEntryClicked(listPosition: Int) {

        val fragArg = EditItemFragmentArg(containerPos, listPosition)
        val bundle = bundleOf("edit_item_frag_arg" to fragArg)
        findNavController().navigate(R.id.action_detail_to_edit_item, args = bundle)
    }

    private fun onItemCountChanged(itemPos: Int, addValue: Int) {
        val item = listItems[itemPos]
        if(item.amount <= 0 && addValue < 0) {
            return
        }

        val newAmount = (item.amount + addValue).coerceAtLeast(0)
        if(newAmount == 0) {
            // TODO: Give user prompt about item deletion
            //  (Or delete immediately on default behavior setting)
        }

        // TODO: Create helper class ItemBuilder or similar
        val newItem = EntryItem(
            item.name,
            item.thumbnailSrc,
            item.description,
            item.dateAdded,
            item.dateModified,
            newAmount
        )

        listItems[itemPos] = newItem
        JsonManager.writeItem(containerPos, itemPos, newItem)
        rcvAdapter.notifyItemChanged(itemPos)
    }

    private fun onBtnAddManualClicked() {
        val fragArg = AddItemFragmentArg(containerPos)
        val bundle = bundleOf("add_item_frag_arg" to fragArg)
        findNavController().navigate(R.id.action_detail_to_add_item, args = bundle)
    }

    private fun onBtnAddBarcodeClicked() {

    }

    override fun getContainerPackage(): Serializable {
        return getSerializable("container_frag_arg", ContainerFragmentArg::class.java)
    }

    override fun hasEditButton(): Boolean {
        return true
    }

}
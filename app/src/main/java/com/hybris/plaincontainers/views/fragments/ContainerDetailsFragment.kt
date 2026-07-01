package com.hybris.plaincontainers.views.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.ItemCountZeroBehavior
import com.hybris.plaincontainers.data.SettingsManager
import com.hybris.plaincontainers.data.model.AppBar
import com.hybris.plaincontainers.data.builders.EntryContainerBuilder
import com.hybris.plaincontainers.data.fragmentargs.AddItemFragmentArg
import com.hybris.plaincontainers.data.fragmentargs.ContainerFragmentArg
import com.hybris.plaincontainers.data.fragmentargs.EditContainerFragmentArg
import com.hybris.plaincontainers.data.fragmentargs.EditItemFragmentArg
import com.hybris.plaincontainers.data.entities.EntryItemInContainer
import com.hybris.plaincontainers.data.viewmodels.DetailsViewModel
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydragincrement.EntryDragIncrementAdapter
import com.hybris.plaincontainers.views.choicepopup.ChoicePopup
import com.hybris.plaincontainers.views.choicepopup.ChoiceRememberPopup
import com.hybris.plaincontainers.views.sortpopup.SortOption
import kotlinx.coroutines.launch
import java.io.Serializable

class ContainerDetailsFragment(): ContainerBaseFragment<EntryItemInContainer>() {
    private lateinit var viewModel: DetailsViewModel
    private var containerId: Long = -1
    private var containerMetadata = EntryContainerBuilder.empty()
    override val labelBtnAdd = R.string.details_btn_add

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect { list ->
                    rcvAdapter.setItems(list)
                }
            }
        }
    }

    override fun initPackageData() {
        val containerPackage = getFragmentArg() as ContainerFragmentArg
        containerId = containerPackage.containerId

        viewModel = DetailsViewModel(containerId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.container.collect { container ->
                if(container != null) {
                    containerMetadata = container

                    setSetSortParams(
                        SortOption.entries[containerMetadata.sortOption],
                        containerMetadata.sortAscending
                    )

                    initAppbarSubtitle()
                }
            }
        }
    }

    override fun initAppbarSubtitle() {
        appbarVm.model.value = AppBar(
            subtitle = containerMetadata.name
        )
    }

    override fun createAdapter(dragListener: DragListener<EntryItemInContainer>) {
        rcvAdapter = EntryDragIncrementAdapter(
            onEntryClick = {pos -> onItemEntryClicked(pos) },
            dragListener,
            onItemCountChange = { pos, addVal -> onItemCountChanged(pos, addVal) }
        )
    }

    override fun persistSortParams(sortOptionOrdinal: Int, sortAscending: Boolean) {
        viewModel.updateContainerSortParams(sortOptionOrdinal, sortAscending)
    }

    override fun persistDraggedOrder(list: List<EntryItemInContainer>) {
        // Bulk update with changed OrderPositions.
        val items = list.mapIndexed{index, e ->
            EntryItemInContainer(
                e.item,
                e.amount,
                index
            )
        }.toTypedArray()

        viewModel.updateItemsInContainer(*items)
    }

    override fun onBtnEditClicked() {
        val fragArg = EditContainerFragmentArg(
            containerId
        )

        val bundle = bundleOf(getString(R.string.frag_arg_edit_container) to fragArg)

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
        val itemId = viewModel.items.value[listPosition].item.itemId
        val fragArg = EditItemFragmentArg(containerId, itemId)
        val bundle = bundleOf(getString(R.string.frag_arg_edit_item) to fragArg)
        findNavController().navigate(R.id.action_detail_to_edit_item, args = bundle)
    }

    private fun onItemCountChanged(itemPos: Int, addValue: Int) {
        val item = viewModel.items.value[itemPos]

        if(item.amount <= 0 && addValue < 0) {
            return
        }

        val newAmount = (item.amount + addValue).coerceAtLeast(0)
        if(newAmount == 0) {
            val onZeroBehavior = SettingsManager.getItemCountZeroBehavior()
            if(onZeroBehavior == ItemCountZeroBehavior.DELETE) {
                onItemCountZeroDelete(item)
                return
            } else if(onZeroBehavior == ItemCountZeroBehavior.ASK) {
                val popup = ChoiceRememberPopup(
                    requireView(),
                    onClickLeft = { remember ->
                        if(remember) onItemCountZeroRemember(ItemCountZeroBehavior.IGNORE)
                    },
                    onClickRight = { remember ->
                        if(remember) onItemCountZeroRemember(ItemCountZeroBehavior.DELETE)
                        onItemCountZeroDelete(item)
                    }
                )
                popup.setTextTitle(requireContext().getString(R.string.popup_choice_remember_item_zero))
                popup.setTextSubtitle(requireContext().getString(R.string.popup_choice_remember_subtitle))
                popup.setTextButtonLeft(requireContext().getString(R.string.popup_choice_remember_item_zero_ignore))
                popup.setTextButtonRight(requireContext().getString(R.string.metadata_btn_delete))

                popup.show(requireView())
            }
        }

        viewModel.updateAmountInContainer(item.item.itemId, newAmount)
    }

    private fun onItemCountZeroRemember(behavior: ItemCountZeroBehavior) {
        SettingsManager.setItemCountZeroBehavior(behavior)
    }

    private fun onItemCountZeroDelete(item: EntryItemInContainer) {
        viewModel.deleteInContainer(item)
    }

    private fun onBtnAddManualClicked() {
        val fragArg = AddItemFragmentArg(containerId)
        val bundle = bundleOf(getString(R.string.frag_arg_add_item) to fragArg)
        findNavController().navigate(R.id.action_detail_to_add_item, args = bundle)
    }

    private fun onBtnAddBarcodeClicked() {
        val fragArg = AddItemFragmentArg(containerId, true)
        val bundle = bundleOf(getString(R.string.frag_arg_add_item) to fragArg)
        findNavController().navigate(R.id.action_detail_to_add_item, args = bundle)
    }

    override fun getFragmentArg(): Serializable {
        return getSerializable(
            getString(R.string.frag_arg_container),
            ContainerFragmentArg::class.java
        )
    }

    override fun hasEditButton(): Boolean {
        return true
    }

}
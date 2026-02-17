package com.hybris.plaincontainers.views.fragments

import android.os.Bundle
import com.hybris.plaincontainers.data.entities.EntryContainer

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.builders.EntryContainerBuilder
import com.hybris.plaincontainers.data.fragmentargs.ContainerFragmentArg
import com.hybris.plaincontainers.data.viewmodels.OverviewViewModel
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydragexpand.EntryDragExpandAdapter
import com.hybris.plaincontainers.views.sortpopup.SortOption
import kotlinx.coroutines.launch
import java.io.Serializable

class ContainerOverviewFragment(): ContainerBaseFragment<EntryContainer>() {
    private val viewModel = OverviewViewModel()
    override val labelBtnAdd: Int = R.string.overview_btn_add

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.root.collect { root ->
                    setSetSortParams(
                        SortOption.entries[root.sortOption],
                        root.sortAscending
                    )
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.containers.collect { list ->
                    rcvAdapter.setItems(list)
                }
            }
        }
    }

    override fun initPackageData() {}

    override fun createAdapter(dragListener: DragListener<EntryContainer>) {
        rcvAdapter = EntryDragExpandAdapter(
            onEntryClick = {pos -> onItemEntryClicked(pos)},
            dragListener,
            viewLifecycleOwner
        )
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

    override fun onBtnAddClicked(view: View) {
        findNavController().navigate(R.id.action_overview_to_add_container)
    }

    override fun getContainerPackage(): Serializable {
        return object: Serializable {}
    }

    override fun hasEditButton(): Boolean {
        return false
    }

}
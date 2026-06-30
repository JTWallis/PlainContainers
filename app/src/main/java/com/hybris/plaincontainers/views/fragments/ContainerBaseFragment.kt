package com.hybris.plaincontainers.views.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.buttoniconlabeled.AddHandle
import com.hybris.plaincontainers.components.handles.buttoniconlabeled.EditHandle
import com.hybris.plaincontainers.components.handles.buttoniconlabeled.SortHandle
import com.hybris.plaincontainers.data.SettingsManager
import com.hybris.plaincontainers.data.entities.EntryBase
import com.hybris.plaincontainers.entrylist.dragbutton.DragAdapter
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydrag.EntryDragAdapter
import com.hybris.plaincontainers.entrylist.itemdecoration.GapVerticalDecoration
import com.hybris.plaincontainers.views.sortpopup.SortChangeListener
import com.hybris.plaincontainers.views.sortpopup.SortOption
import com.hybris.plaincontainers.views.sortpopup.SortPopup
import com.hybris.plaincontainers.views.sortpopup.SortSelection

abstract class ContainerBaseFragment<T: EntryBase>(): FragmentBase(R.layout.activity_containers), SortChangeListener {

    private var sortParams: SortSelection = SortSelection(SortOption.DATE_ADDED, true)
    private lateinit var layoutBtnSort: CardView
    private lateinit var handleSort: SortHandle
    private lateinit var switchDrag: SwitchCompat
    private lateinit var layoutBtnEdit: CardView
    private lateinit var handleEdit: EditHandle
    private lateinit var layoutBtnAdd: CardView
    private lateinit var handleAdd: AddHandle
    private lateinit var rcvList: RecyclerView
    protected lateinit var rcvAdapter: EntryDragAdapter<T>

    @get:StringRes protected abstract val labelBtnAdd: Int
    @get:StringRes protected val labelBtnEdit: Int = R.string.details_btn_edit


    protected abstract fun createAdapter(dragListener: DragListener<T>)
    protected abstract fun persistSortParams(sortOptionOrdinal: Int, sortAscending: Boolean)
    protected abstract fun persistDraggedOrder(list: List<T>)
    protected abstract fun onItemEntryClicked(listPosition: Int)
    protected abstract fun onBtnAddClicked(view: View)
    protected abstract fun hasEditButton(): Boolean

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycleView(view)

        switchDrag.setOnCheckedChangeListener { _, isChecked ->
            SettingsManager.setDragEnabled(isChecked)
            rcvAdapter.setDragVisibility(isChecked)
        }

        rcvAdapter.setDragVisibility(switchDrag.isChecked)
    }

    override fun initViews(view: View) {
        layoutBtnSort = view.findViewById(R.id.layoutSort)
        handleSort = SortHandle(
            layoutBtnSort,
            onClick = { onBtnSortClicked(layoutBtnSort) },
            requireContext().getString(sortParams.option.sortLabelId)
        )
        switchDrag = view.findViewById(R.id.switchDrag)
        layoutBtnEdit = view.findViewById(R.id.layoutEdit)
        handleEdit = EditHandle(layoutBtnEdit, onClick = { onBtnEditClicked() }, requireContext().getString(labelBtnEdit))
        layoutBtnAdd = view.findViewById(R.id.layoutAdd)
        handleAdd = AddHandle(layoutBtnAdd, onClick = { onBtnAddClicked(layoutBtnAdd) }, requireContext().getString(labelBtnAdd))
        rcvList = view.findViewById(R.id.rcvContainers)

        if(!hasEditButton()) {
            handleEdit.setVisibility(false)
        }

        switchDrag.isChecked = SettingsManager.isDragEnabled()
    }

    private fun initRecycleView(view: View) {
        rcvList.layoutManager = LinearLayoutManager(view.context)
        rcvList.addItemDecoration(
            GapVerticalDecoration(
                getResources().getDimensionPixelSize(R.dimen.rcvEntryGap)
            )
        )

        lateinit var itemTouchHelper : ItemTouchHelper
        val dragListener = object : DragListener<T> {
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }

            override fun onEndDrag(resultList: List<T>) {
                persistSortParams(SortOption.CUSTOM.ordinal, true)
                persistDraggedOrder(resultList)
            }
        }

        createAdapter(dragListener)
        val callback = DragAdapter(rcvAdapter)

        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rcvList)
        rcvList.adapter = rcvAdapter
    }

    protected fun setSetSortParams(sortOption: SortOption, isAscending: Boolean) {
        sortParams.option = sortOption
        sortParams.isAscending = isAscending
        handleSort.setText(requireContext().getString(sortOption.sortLabelId))
    }

    private fun onBtnSortClicked(view: View) {
        val popup = SortPopup(
            view,
            sortParams,
            onSortChanged = { e -> onPopupSortOptionChanged(e) })
        popup.setTitle(requireContext().getString(R.string.popup_sort_title))
        popup.show(view)
    }

    protected open fun onBtnEditClicked() {

    }

    override fun onPopupSortOptionChanged(sortSelection: SortSelection) {
        setSetSortParams(sortSelection.option, sortSelection.isAscending)
        persistSortParams(sortParams.option.ordinal, sortParams.isAscending)
    }

}
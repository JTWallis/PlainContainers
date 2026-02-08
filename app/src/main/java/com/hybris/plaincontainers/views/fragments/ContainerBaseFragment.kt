package com.hybris.plaincontainers.views.fragments

import android.os.Bundle
import android.util.Log
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
import com.hybris.plaincontainers.data.ListUtils
import com.hybris.plaincontainers.data.model.EntryBase
import com.hybris.plaincontainers.entrylist.dragbutton.DragAdapter
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydrag.EntryDragAdapter
import com.hybris.plaincontainers.entrylist.itemdecoration.GapVerticalDecoration
import com.hybris.plaincontainers.views.choicepopup.ChoicePopup
import com.hybris.plaincontainers.views.sortpopup.SortChangeListener
import com.hybris.plaincontainers.views.sortpopup.SortOption
import com.hybris.plaincontainers.views.sortpopup.SortPopup
import com.hybris.plaincontainers.views.sortpopup.SortSelection

abstract class ContainerBaseFragment<T: EntryBase>(): FragmentBase(R.layout.activity_containers), SortChangeListener {

    protected abstract var listItems: MutableList<T>
    protected abstract var sortParams: SortSelection
    private lateinit var layoutBtnSort: CardView
    private lateinit var handleSort: SortHandle
    private lateinit var switchDrag: SwitchCompat
    private lateinit var layoutBtnEdit: CardView
    private lateinit var handleEdit: EditHandle
    private lateinit var layoutBtnAdd: CardView
    private lateinit var handleAdd: AddHandle
    private lateinit var rcvList: RecyclerView
    private lateinit var itemMovedObserver: RecyclerView.AdapterDataObserver
    protected lateinit var rcvAdapter: EntryDragAdapter<T>

    @get:StringRes protected abstract val labelBtnAdd: Int
    @get:StringRes protected val labelBtnEdit: Int = R.string.details_btn_edit


    protected abstract fun createAdapter(dragListener: DragListener)
    protected abstract fun writeJsonChanges()
    protected abstract fun onItemEntryClicked(listPosition: Int)
    protected abstract fun onBtnAddClicked(view: View)
    protected abstract fun hasEditButton(): Boolean

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycleView(view)

        switchDrag.setOnCheckedChangeListener { _, isChecked ->
            rcvAdapter.setDragVisibility(isChecked)
        }

    }

    override fun onDestroy() {
        if(::rcvAdapter.isInitialized) {
            rcvAdapter.unregisterAdapterDataObserver(itemMovedObserver)
        }

        super.onDestroy()
    }


    override fun initViews(view: View) {
        layoutBtnSort = view.findViewById(R.id.layoutSort)
        handleSort = SortHandle(layoutBtnSort, onClick = { onBtnSortClicked(layoutBtnSort) }, "Custom")
        switchDrag = view.findViewById(R.id.switchDrag)
        layoutBtnEdit = view.findViewById(R.id.layoutEdit)
        handleEdit = EditHandle(layoutBtnEdit, onClick = { onBtnEditClicked() }, requireContext().getString(labelBtnEdit))
        layoutBtnAdd = view.findViewById(R.id.layoutAdd)
        handleAdd = AddHandle(layoutBtnAdd, onClick = { onBtnAddClicked(layoutBtnAdd) }, requireContext().getString(labelBtnAdd))
        rcvList = view.findViewById(R.id.rcvContainers)

        if(!hasEditButton()) {
            handleEdit.setVisibility(false)
        }
    }

    private fun initRecycleView(view: View) {
        rcvList.layoutManager = LinearLayoutManager(view.context)
        rcvList.addItemDecoration(
            GapVerticalDecoration(
                getResources().getDimensionPixelSize(R.dimen.rcvEntryGap)
            )
        )

        lateinit var itemTouchHelper : ItemTouchHelper
        val dragListener = object : DragListener {
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }
        }

        createAdapter(dragListener)
        val callback = DragAdapter(rcvAdapter)

        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rcvList)
        rcvList.adapter = rcvAdapter

        itemMovedObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                setSetSortOption(SortOption.CUSTOM, true)
                writeJsonChanges()
            }
        }

        rcvAdapter.registerAdapterDataObserver(itemMovedObserver)
    }

    private fun setSetSortOption(sortOption: SortOption, isAscending: Boolean) {
        sortParams.option = sortOption
        sortParams.isAscending = isAscending
        handleSort.setText(sortOption.toString())
    }

    private fun sortList(sortOption: SortOption, isAscending: Boolean) {
        val hashBefore = listItems.hashCode()
        ListUtils.sortEntryList(listItems, SortSelection(sortOption, isAscending))
        val hashAfter = listItems.hashCode()

        if(hashBefore != hashAfter) {
            rcvAdapter.notifyItemRangeChanged(0, listItems.count())
        }

        setSetSortOption(sortOption, isAscending)
    }

    private fun onBtnSortClicked(view: View) {
        Log.d("INFO", "BtnSort Clicked!")

        val popup = SortPopup(
            view,
            sortParams,
            onSortChanged = { e -> onSortOptionChanged(e) })
        popup.setTitle("Sort by:")
        popup.show(view)
    }

    protected open fun onBtnEditClicked() {

    }

    override fun onSortOptionChanged(sortSelection: SortSelection) {
        sortList(sortSelection.option, sortSelection.isAscending)
        writeJsonChanges()
    }

    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.actionSettings -> {
                //findNavController().navigate(R.id.)
                true
            }
            R.id.actionAbout -> {
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    */

}
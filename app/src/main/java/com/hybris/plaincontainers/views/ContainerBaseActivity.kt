package com.hybris.plaincontainers.views

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.buttoniconlabeled.AddHandle
import com.hybris.plaincontainers.components.handles.buttoniconlabeled.SortHandle
import com.hybris.plaincontainers.data.ContainerActivityPackage
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
import java.io.Serializable

abstract class ContainerBaseActivity<T: EntryBase>(): AppCompatActivity(), SortChangeListener {

    protected lateinit var listItems: MutableList<T>
    private lateinit var sortParams: SortSelection
    private lateinit var layoutToolbar: Toolbar
    private lateinit var layoutBtnSort: CardView
    private lateinit var handleSort: SortHandle
    private lateinit var switchDrag: SwitchCompat
    private lateinit var layoutBtnAdd: CardView
    private lateinit var handleAdd: AddHandle
    private lateinit var rcvList: RecyclerView
    private lateinit var itemMovedObserver: RecyclerView.AdapterDataObserver
    protected lateinit var rcvAdapter: EntryDragAdapter<T>


    protected abstract fun createAdapter(dragListener: DragListener)
    protected abstract fun writeJsonChanges()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_containers);

        initPackageData()
        initViews()
        initActionBar()
        initRecycleView()

        switchDrag.setOnCheckedChangeListener { _, isChecked ->
            rcvAdapter.setDragVisibility(isChecked)
        }

        Log.d("INFO", "ContainerBaseActivity Create")
    }

    override fun onDestroy() {
        rcvAdapter.unregisterAdapterDataObserver(itemMovedObserver)

        super.onDestroy()
    }

    protected open fun initPackageData() {
        val containerPackage = getContainerPackage()
        listItems = containerPackage.listItems as MutableList<T>
        sortParams = containerPackage.sortParams
    }

    protected open fun initViews() {
        layoutToolbar = findViewById(R.id.layoutToolbar)
        layoutBtnSort = findViewById(R.id.layoutSort)
        handleSort = SortHandle(layoutBtnSort, onClick = { onBtnSortClicked(layoutBtnSort) }, "Custom")
        switchDrag = findViewById(R.id.switchDrag)
        layoutBtnAdd = findViewById(R.id.layoutAdd)
        handleAdd = AddHandle(layoutBtnAdd, onClick = { onBtnAddClicked(layoutBtnAdd) }, "Add Container")
        rcvList = findViewById(R.id.rcvContainers)
    }

    private fun initActionBar() {
        layoutToolbar.title = "Container Overview"
        layoutToolbar.subtitle = ""
        setSupportActionBar(layoutToolbar.findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecycleView() {
        rcvList.layoutManager = LinearLayoutManager(this)
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
        when(sortOption) {
            SortOption.NAME -> {
                if(isAscending) listItems.sortBy { e -> e.name }
                else listItems.sortByDescending { e -> e.name }

                rcvAdapter.notifyItemRangeChanged(0, listItems.count())
            }
            SortOption.DATE_ADDED -> {}
            SortOption.DATE_MODIFIED -> {}
            SortOption.CUSTOM -> {}
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

    private fun onBtnAddClicked(view: View) {
        val popup = ChoicePopup(
            view,
            onClickLeft = { onBtnAddManualClicked() },
            onClickRight = { onBtnAddBarcodeClicked() }
        )
        popup.setTextTitle("Select method to add item")
        popup.setTextSubtitle("This action is irreversible!")
        popup.setTextButtonLeft("Manual")
        popup.setTextButtonRight("Barcode")

        popup.show(view)
    }

    private fun onBtnAddManualClicked() {
    }

    private fun onBtnAddBarcodeClicked() {
    }

    override fun onSortOptionChanged(sortSelection: SortSelection) {
        sortList(sortSelection.option, sortSelection.isAscending)
        writeJsonChanges()
    }

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

    protected fun getContainerPackage(): ContainerActivityPackage {
        return getSerializable("container_package", ContainerActivityPackage::class.java)
    }

    private fun <T: Serializable?> getSerializable(name: String, clazz: Class<T>): T {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra(name, clazz)!!
        else
            intent.getSerializableExtra(name) as T
    }

}
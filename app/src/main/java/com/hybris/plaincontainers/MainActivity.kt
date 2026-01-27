package com.hybris.plaincontainers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hybris.plaincontainers.components.handles.AddHandle
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.entrylist.dragbutton.DragAdapter
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydragexpand.EntryDragExpandAdapter
import com.hybris.plaincontainers.entrylist.itemdecoration.GapVerticalDecoration
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.data.model.RootContainer
import com.hybris.plaincontainers.data.states.EntryStateContainer
import com.hybris.plaincontainers.components.handles.SortHandle
import com.hybris.plaincontainers.ui.theme.PlainContainersTheme
import com.hybris.plaincontainers.views.ContainerDetailsActivity
import com.hybris.plaincontainers.views.choicepopup.ChoicePopup
import com.hybris.plaincontainers.views.sortpopup.SortPopup
import com.hybris.plaincontainers.views.sortpopup.SortChangeListener
import com.hybris.plaincontainers.views.sortpopup.SortOption
import com.hybris.plaincontainers.views.sortpopup.SortSelection

class MainActivity : AppCompatActivity(), SortChangeListener {

    private lateinit var layoutToolbar: Toolbar
    private lateinit var btnScan: Button;
    private lateinit var tvScanResult: TextView;
    private lateinit var layoutBtnSort: CardView
    private lateinit var handleSort: SortHandle
    private lateinit var toggleDrag: SwitchCompat
    private lateinit var layoutBtnAdd: CardView
    private lateinit var handleAdd: AddHandle
    private lateinit var rcvContainers: RecyclerView
    private lateinit var rcvAdapter: EntryDragExpandAdapter
    private lateinit var manger: JsonManager
    private lateinit var rootContainer: RootContainer
    private lateinit var dummyList: MutableList<EntryContainer>

    private val scanLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val barcode = result.data?.getStringExtra("barcode")
            tvScanResult.text = barcode ?: "No result"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_containers);

        layoutToolbar = findViewById(R.id.layoutToolbar)
        btnScan = findViewById(R.id.btnScan);
        tvScanResult = findViewById(R.id.tvScanResult);
        layoutBtnSort = findViewById(R.id.layoutSort)
        handleSort = SortHandle(layoutBtnSort, onClick = { onBtnSortClicked(layoutBtnSort) }, "Custom")
        toggleDrag = findViewById(R.id.switchDrag)
        layoutBtnAdd = findViewById(R.id.layoutAdd)
        handleAdd = AddHandle(layoutBtnAdd, onClick = { onBtnAddClicked(layoutBtnAdd) }, "Add Container")
        rcvContainers = findViewById(R.id.rcvContainers)

        layoutToolbar.title = "Container Overview"
        layoutToolbar.subtitle = ""
        setSupportActionBar(layoutToolbar.findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        JsonManager.init(this)
        val rootNullable = JsonManager.readRoot()

        if(rootNullable == null) {
            val itemList = ArrayList<EntryItem>()
            itemList.add(EntryItem("Beans", "Beanz.png", 2) )
            itemList.add(EntryItem("Beans2", "Beanz.png", 2) )
            itemList.add(EntryItem("Beans3", "Beanz.png", 2) )
            itemList.add(EntryItem("Beans4", "Beanz.png", 2) )
            dummyList = ArrayList()
            dummyList.add(EntryContainer("Heinz Bakeddd Beans", "123.png", "#F00",SortSelection(SortOption.CUSTOM, true), itemList))
            dummyList.add(EntryContainer("Heinz SOY Beans", "123.png", "#F00",SortSelection(SortOption.CUSTOM, true), ArrayList()))
            dummyList.add(EntryContainer("Heinz Ketchup", "123.png", "#F00",SortSelection(SortOption.CUSTOM, true), ArrayList()))
            rootContainer = RootContainer(SortSelection(SortOption.CUSTOM, true), dummyList)
            JsonManager.writeRoot(rootContainer)
        } else {
            rootContainer = rootNullable
            dummyList = rootContainer.containers.toMutableList()
        }


        rcvContainers.layoutManager = LinearLayoutManager(this)
        rcvContainers.addItemDecoration(
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
        rcvAdapter = EntryDragExpandAdapter(dragListener)
        rcvAdapter.setItems(dummyList)
        val callback = DragAdapter(rcvAdapter)

        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rcvContainers)
        rcvContainers.adapter = rcvAdapter

        val itemMovedObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                setSetSortOption(SortOption.CUSTOM, true)
                rootContainer.containers = dummyList
                JsonManager.writeRoot(rootContainer)
            }
        }

        rcvAdapter.registerAdapterDataObserver(itemMovedObserver)
        //adapter.unregisterAdapterDataObserver(itemMovedObserver)

        toggleDrag.setOnCheckedChangeListener { _, isChecked ->
            rcvAdapter.setDragVisibility(isChecked)
        }

        initListeners()
    }

    private fun initListeners() {
        btnScan.setOnClickListener {
            onBtnScanClicked()
        }
    }

    private fun onBtnSortClicked(view: View) {
        Log.d("INFO", "BtnSort Clicked!")

        val popup = SortPopup(
            view,
            rootContainer.sortParams,
            onSortChanged = { e -> onSortOptionChanged(e) })
        popup.setTitle("Sort by:")
        popup.show(view)
    }

    private fun startActivityContainerDetails() {
        val container = dummyList[0]
        val pack = ContainerActivityPackage(
            0,
            container.items.toMutableList(),
            container.sortParams
        )

        val int = Intent(this, ContainerDetailsActivity::class.java)
        int.putExtra("container_package", pack)

        startActivity(int)
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

    private fun onBtnScanClicked() {


        scanLauncher.launch(
            Intent(this, ScannerActivity::class.java)
        )
    }

    private fun setSetSortOption(sortOption: SortOption, isAscending: Boolean) {
        rootContainer.sortParams.option = sortOption
        rootContainer.sortParams.isAscending = isAscending
        handleSort.setText(sortOption.toString())
    }

    private fun sortList(sortOption: SortOption, isAscending: Boolean) {
        when(sortOption) {
            SortOption.NAME -> {
                if(isAscending) dummyList.sortBy{ e -> e.name }
                else dummyList.sortByDescending { e -> e.name }

                rootContainer.containers = dummyList
                rcvAdapter.notifyItemRangeChanged(0, dummyList.count())
            }
            SortOption.DATE_ADDED -> {}
            SortOption.DATE_MODIFIED -> {}
            SortOption.CUSTOM -> {}
        }

        setSetSortOption(sortOption, isAscending)
    }

    override fun onSortOptionChanged(sortSelection: SortSelection) {
        sortList(sortSelection.option, sortSelection.isAscending)
        JsonManager.writeRoot(rootContainer)
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
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Surface(color = Color.Gray) {
        Text(
            text = "Hello $name!",
            modifier = modifier.padding(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlainContainersTheme {
        Greeting("Gustavo")
    }
}
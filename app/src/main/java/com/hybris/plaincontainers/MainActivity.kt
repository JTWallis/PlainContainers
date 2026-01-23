package com.hybris.plaincontainers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
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
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.entrylist.dragbutton.DragAdapter
import com.hybris.plaincontainers.entrylist.dragbutton.DragListener
import com.hybris.plaincontainers.entrylist.entrydragexpand.EntryDragExpandAdapter
import com.hybris.plaincontainers.entrylist.itemdecoration.GapVerticalDecoration
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.data.model.RootContainer
import com.hybris.plaincontainers.data.states.EntryStateContainer
import com.hybris.plaincontainers.entrylist.sortbutton.SortHandle
import com.hybris.plaincontainers.ui.theme.PlainContainersTheme

class MainActivity : ComponentActivity() {

    private lateinit var btnScan: Button;
    private lateinit var tvScanResult: TextView;
    private lateinit var layoutBtnSort: CardView
    private lateinit var toggleDrag: SwitchCompat
    private lateinit var rcvContainers: RecyclerView
    private lateinit var handleSort: SortHandle
    private lateinit var rcvAdapter: EntryDragExpandAdapter
    private lateinit var manger: JsonManager
    private lateinit var rootContainer: RootContainer
    private lateinit var dummyList: MutableList<EntryStateContainer>

    private val scanLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val barcode = result.data?.getStringExtra("barcode")
            tvScanResult.text = barcode ?: "No result"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        btnScan = findViewById(R.id.btnScan);
        tvScanResult = findViewById(R.id.tvScanResult);
        layoutBtnSort = findViewById(R.id.layoutSort)
        toggleDrag = findViewById(R.id.switchDrag)
        rcvContainers = findViewById(R.id.rcvContainers)

        handleSort = SortHandle(layoutBtnSort, onClick = { onBtnSortClicked(layoutBtnSort) })

        manger = JsonManager(this)
        val rootNullable = manger.readRoot()

        if(rootNullable == null) {
            val itemList = ArrayList<EntryItem>()
            itemList.add(EntryItem("Beans", "Beanz.png", 2) )
            itemList.add(EntryItem("Beans2", "Beanz.png", 2) )
            itemList.add(EntryItem("Beans3", "Beanz.png", 2) )
            itemList.add(EntryItem("Beans4", "Beanz.png", 2) )
            dummyList = ArrayList()
            dummyList.add(EntryStateContainer(EntryContainer("Heinz Bakeddd Beans", "123.png", "#F00", itemList)))
            dummyList.add(EntryStateContainer(EntryContainer("Heinz SOY Beans", "123.png", "#F00", ArrayList())))
            dummyList.add(EntryStateContainer(EntryContainer("Heinz Ketchup", "123.png", "#F00", ArrayList())))
            rootContainer = RootContainer(SortOption.CUSTOM, true, dummyList.map { e -> e.model})
            manger.writeRoot(rootContainer)
        } else {
            rootContainer = rootNullable
            dummyList = rootContainer.containers.map{ e -> EntryStateContainer(e) }.toMutableList()
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
                rootContainer.containers = dummyList.map{ e -> e.model}
                manger.writeRoot(rootContainer)
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

    }

    private fun onBtnScanClicked() {


        scanLauncher.launch(
            Intent(this, ScannerActivity::class.java)
        )
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
package com.hybris.plaincontainers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
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
import com.hybris.plaincontainers.entrylist.model.EntryBase
import com.hybris.plaincontainers.entrylist.itemdecoration.GapVerticalDecoration
import com.hybris.plaincontainers.entrylist.model.EntryContainer
import com.hybris.plaincontainers.ui.theme.PlainContainersTheme

class MainActivity : ComponentActivity() {

    private lateinit var btnScan: Button;
    private lateinit var tvScanResult: TextView;
    private lateinit var toggleDrag: SwitchCompat
    private lateinit var rcvContainers: RecyclerView

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
        toggleDrag = findViewById(R.id.switchDrag)
        rcvContainers = findViewById(R.id.rcvContainers)

        val manger = JsonManager(this)
        val dummyList = manger.readContainers()

        if(dummyList.isEmpty()) {
            dummyList.add(EntryContainer("Heinz Bakeddd Beans", "123.png", "#F00", ArrayList()))
            dummyList.add(EntryContainer("Heinz SOY Beans", "123.png", "#F00", ArrayList()))
            dummyList.add(EntryContainer("Heinz Ketchup", "123.png", "#F00", ArrayList()))
            manger.writeContainers(dummyList)
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
        val adapter = EntryDragExpandAdapter(dummyList, dragListener)
        val callback = DragAdapter(adapter)

        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rcvContainers)
        rcvContainers.adapter = adapter

        val itemMovedObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                manger.writeContainers(dummyList)
            }
        }

        adapter.registerAdapterDataObserver(itemMovedObserver)
        //adapter.unregisterAdapterDataObserver(itemMovedObserver)

        initListeners()
    }

    private fun initListeners() {
        btnScan.setOnClickListener {
            onBtnScanClicked()
        }
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
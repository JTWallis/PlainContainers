package com.hybris.plaincontainers

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.LocaleUtils
import com.hybris.plaincontainers.data.SupportedLocale
import com.hybris.plaincontainers.data.model.EntryContainer
import com.hybris.plaincontainers.data.model.EntryItem
import com.hybris.plaincontainers.data.model.RootContainer
import com.hybris.plaincontainers.data.appbar.AppBarViewModel
import com.hybris.plaincontainers.data.fragmentargs.RootFragmentArg
import com.hybris.plaincontainers.views.sortpopup.SortOption
import com.hybris.plaincontainers.views.sortpopup.SortSelection

class MainActivity : AppCompatActivity() {
    private val appbarVm: AppBarViewModel by viewModels()
    private lateinit var layoutToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        Log.d("INFO", "MainActivity Create")

        layoutToolbar = findViewById(R.id.layoutToolbar)
        layoutToolbar.title = "Container Overview"
        layoutToolbar.subtitle = ""
        setSupportActionBar(layoutToolbar.findViewById(R.id.toolbar))

        JsonManager.init(this)
        val rootContainer: RootContainer
        val rootNullable = JsonManager.readRoot()

        if(rootNullable == null) {
            val itemList = ArrayList<EntryItem>()
            itemList.add(EntryItem("Beans", "Beanz.png", 2) )
            itemList.add(EntryItem("Beans2", "Beanz.png", 2) )
            itemList.add(EntryItem("Beans3", "Beanz.png", 2) )
            itemList.add(EntryItem("Beans4", "Beanz.png", 2) )
            val dummyList = ArrayList<EntryContainer>()
            dummyList.add(EntryContainer("Heinz Bakeddd Beans", "123.png", 0,SortSelection(SortOption.CUSTOM, true), itemList))
            dummyList.add(EntryContainer("Heinz SOY Beans", "123.png", 0,SortSelection(SortOption.CUSTOM, true), ArrayList()))
            dummyList.add(EntryContainer("Heinz Ketchup", "123.png", 0,SortSelection(SortOption.CUSTOM, true), ArrayList()))
            rootContainer = RootContainer(SortSelection(SortOption.CUSTOM, true), dummyList)
            JsonManager.writeRoot(rootContainer)
        } else {
            rootContainer = rootNullable
        }

        val pack = RootFragmentArg(
            rootContainer.containers.toMutableList(),
            rootContainer.sortParams
        )

        val bundle = bundleOf("root_frag_arg" to pack)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        layoutToolbar.setOnMenuItemClickListener { menuItem -> onOptionsItemSelected(menuItem) }

        layoutToolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
        navController.setGraph(R.navigation.nav_graph, bundle)

        appbarVm.model.observe(this) { model ->
            layoutToolbar.apply {
                title = model.title
                subtitle = model.subtitle
            }
        }
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

    override fun attachBaseContext(base: Context) {
        val langTag = LocaleUtils.getLocale(base).toLanguageTag()
        val locale = LocaleUtils.getLocaleFromLanguageTag(langTag)
        val newBase = LocaleUtils.setLocale(base, locale)

        super.attachBaseContext(newBase)
    }
}

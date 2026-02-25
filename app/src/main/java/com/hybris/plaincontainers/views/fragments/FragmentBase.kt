package com.hybris.plaincontainers.views.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.LocaleUtils
import com.hybris.plaincontainers.data.SupportedLocale
import com.hybris.plaincontainers.data.appbar.AppBarModel
import com.hybris.plaincontainers.data.appbar.AppBarViewModel
import java.io.Serializable
import kotlin.getValue

abstract class FragmentBase(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected val appbarVm: AppBarViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenuProvider()
        initPackageData()
        initViews(view)

        appbarVm.setModelEmpty()
    }

    protected abstract fun initViews(view: View)
    protected abstract fun initPackageData()
    protected abstract fun getContainerPackage(): Serializable

    protected open fun initAppbarSubtitle() {}

    private fun initMenuProvider() {
        val menuHost: MenuHost = requireActivity()
        val menuId = R.menu.toolbar_menu

        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(menuId, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return onAppbarMenuItemSelected(menuItem)
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onAppbarMenuItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.actionSettings -> {
                findNavController().navigate(R.id.action_any_to_settings)
                // DEBUG Change to German
                //LocaleUtils.setLocale(this, SupportedLocale.DE)
                //recreate()
                true
            }
            R.id.actionAbout -> {
                findNavController().navigate(R.id.action_any_to_about)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    protected fun <T: Serializable?> getSerializable(name: String, clazz: Class<T>): T {
        Log.d("INFO", "Getting $name with class $clazz")
        Log.d("INFO", "Arguments: $arguments")
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            arguments?.getSerializable(name, clazz)!!
        else
            arguments?.getSerializable(name) as T
    }

}
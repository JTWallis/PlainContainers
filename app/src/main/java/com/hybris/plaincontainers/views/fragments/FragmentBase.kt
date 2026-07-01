package com.hybris.plaincontainers.views.fragments

import android.os.Build
import android.os.Bundle
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
import com.hybris.plaincontainers.data.viewmodels.AppBarViewModel
import java.io.Serializable
import kotlin.getValue

/**
 * Base class for every Fragment, that intends to use a Menu in the AppBar
 * and optionally FragmentArguments, passed on a navigation.
 * Can be inherited from the same way as a regular androidx.fragment.app.Fragment class.
 */
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

    /**
     * Helper function wrapper for getSerializable.
     * The returned Serializable can be cast to the according *FragmentArg type,
     * to parse the fragment argument data during compile time.
     * @return FragmentArg instance matching a type in the data.fragmentargs package.
     * Can possibly instead return an empty Serializable object,
     * if a Fragment does not intend to receive fragment args.
     */
    protected abstract fun getFragmentArg(): Serializable

    /**
     * Displays an optional subtitle in the AppBar,
     * by setting a new Model value for appbarVm.
     */
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

    /**
     * Used in an override of GetFragmentArg to handle the passed
     * fragment argument data during compile time.
     * @param name FragmentArgument key
     * @param clazz Class instance for a FragmentArgument, tied to the key
     * @return Serializable class instance, intended for FragmentArguments.
     */
    protected fun <T: Serializable?> getSerializable(name: String, clazz: Class<T>): T {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            arguments?.getSerializable(name, clazz)!!
        else
            arguments?.getSerializable(name) as T
    }

}
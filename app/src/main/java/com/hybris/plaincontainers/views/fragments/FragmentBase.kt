package com.hybris.plaincontainers.views.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hybris.plaincontainers.data.appbar.AppBarModel
import com.hybris.plaincontainers.data.appbar.AppBarViewModel
import java.io.Serializable
import kotlin.getValue

abstract class FragmentBase(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected val appbarVm: AppBarViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPackageData()
        initViews(view)

        appbarVm.model.value = AppBarModel(
            title = getAppbarTitle(),
            subtitle = getAppbarSubtitle()
        )
    }

    protected abstract fun initViews(view: View)
    protected abstract fun initPackageData()
    protected abstract fun getAppbarTitle(): String
    protected abstract fun getAppbarSubtitle(): String
    protected abstract fun getContainerPackage(): Serializable

    protected fun <T: Serializable?> getSerializable(name: String, clazz: Class<T>): T {
        Log.d("INFO", "Getting $name with class $clazz")
        Log.d("INFO", "Arguments: $arguments")
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            arguments?.getSerializable(name, clazz)!!
        else
            arguments?.getSerializable(name) as T
    }

}
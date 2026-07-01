package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hybris.plaincontainers.data.model.AppBar

/**
 * ViewModel for AppBar, used by any Fragment that intents to modify the AppBar subtitle,
 * by setting the model value.
 */
class AppBarViewModel: ViewModel() {
    val model = MutableLiveData<AppBar>()

    fun setModelEmpty() {
        model.value = AppBar(
            subtitle = ""
        )
    }
}
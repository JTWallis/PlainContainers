package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hybris.plaincontainers.data.model.AppBar

class AppBarViewModel: ViewModel() {
    val model = MutableLiveData<AppBar>()

    fun setModelEmpty() {
        model.value = AppBar(
            subtitle = ""
        )
    }
}
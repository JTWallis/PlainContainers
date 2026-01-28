package com.hybris.plaincontainers.data.appbar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppBarViewModel: ViewModel() {
    val model = MutableLiveData<AppBarModel>()
}
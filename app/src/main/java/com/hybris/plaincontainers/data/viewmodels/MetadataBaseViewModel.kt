package com.hybris.plaincontainers.data.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class MetadataBaseViewModel: ViewModel() {
    abstract val entry: Flow<Int>
}
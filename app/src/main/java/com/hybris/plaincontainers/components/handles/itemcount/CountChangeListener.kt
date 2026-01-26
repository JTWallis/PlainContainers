package com.hybris.plaincontainers.components.handles.itemcount

interface CountChangeListener {
    fun onItemCountChange(itemPos: Int, addValue: Int)
}
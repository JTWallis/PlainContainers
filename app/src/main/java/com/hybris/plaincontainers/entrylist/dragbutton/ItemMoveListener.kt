package com.hybris.plaincontainers.entrylist.dragbutton

interface ItemMoveListener {
    fun onItemMove(from: Int, to: Int)
    fun onClearView()
}
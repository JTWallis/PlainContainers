package com.hybris.plaincontainers.entrylist.expandbutton

interface ExpandListener {
    /**
     * Event for clicking the ExpandHandle within an RecyclerView.
     * @param position Item index within the RecyclerView
     */
    fun onExpandClick(position: Int)
}
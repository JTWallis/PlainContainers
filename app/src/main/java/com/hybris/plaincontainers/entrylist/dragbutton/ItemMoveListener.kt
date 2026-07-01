package com.hybris.plaincontainers.entrylist.dragbutton

interface ItemMoveListener {
    /**
     * Event for moving a RecyclerView item to a new position.
     * @param from Old index position
     * @param to New index position
     */
    fun onItemMove(from: Int, to: Int)

    /**
     * Event for releasing a RecyclerView item.
     */
    fun onClearView()
}
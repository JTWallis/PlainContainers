package com.hybris.plaincontainers.data.fragmentargs

import java.io.Serializable

/**
 * Fragment-argument for EditItemFragment.
 */
class EditItemFragmentArg(
    val containerId: Long,
    val itemId: Long
): Serializable
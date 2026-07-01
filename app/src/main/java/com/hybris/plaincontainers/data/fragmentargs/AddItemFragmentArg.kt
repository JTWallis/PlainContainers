package com.hybris.plaincontainers.data.fragmentargs

import java.io.Serializable

class AddItemFragmentArg(
    val containerId: Long,

    // One-time-consume flag: Read for quick-add navigation and immediately set to false,
    //  to prevent retrigger on navigateUp().
    var navigateBarcodeFrag: Boolean = false
): Serializable
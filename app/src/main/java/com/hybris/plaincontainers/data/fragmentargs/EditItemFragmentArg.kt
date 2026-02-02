package com.hybris.plaincontainers.data.fragmentargs

import java.io.Serializable

class EditItemFragmentArg(
    val containerPos: Int,
    val itemPos: Int
): Serializable {}
package com.hybris.plaincontainers.data

import androidx.annotation.StringRes
import com.hybris.plaincontainers.R

enum class ItemCountZeroBehavior(
    @get:StringRes private val localizationId: Int
) {
    ASK(R.string.settings_operations_item_count_zero_ask),
    IGNORE(R.string.popup_choice_remember_item_zero_ignore),
    DELETE(R.string.metadata_btn_delete);

    @StringRes fun toLocalizationId(): Int {
        return localizationId
    }
}
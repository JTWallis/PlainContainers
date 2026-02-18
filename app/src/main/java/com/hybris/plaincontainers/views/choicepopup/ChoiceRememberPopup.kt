package com.hybris.plaincontainers.views.choicepopup

import android.view.View
import android.widget.CheckBox
import com.hybris.plaincontainers.R

class ChoiceRememberPopup(
    invokerView: View,
    private val onClickLeft: (rememberChoice: Boolean) -> Unit,
    private val onClickRight: (rememberChoice: Boolean) -> Unit,
    isChoiceImportant: Boolean = false,
): ChoicePopup(
    invokerView,
    onClickLeft = {},
    onClickRight = {},
    isChoiceImportant,
    R.layout.popup_choice_remember,
    R.id.tvPopupChoiceRememberTitle,
    R.id.checkPopupChoiceRememberSubtitle,
    R.id.btnPopupChoiceRememberLeft,
    R.id.btnPopupChoiceRememberRight
) {

    private var remember: Boolean = false

    init {
        val checkSubtitle = (tvSubtitle as CheckBox)
        checkSubtitle.setOnCheckedChangeListener { _, isChecked ->
            remember = isChecked
        }
    }

    override fun onClickLeftDelegate() {
        onClickLeft(remember)
    }

    override fun onClickRightDelegate() {
        onClickRight(remember)
    }
}
package com.hybris.plaincontainers.components.handles

import android.view.View
import com.hybris.plaincontainers.R

class AddHandle(view: View, onClick: () -> Unit)
    : ButtonHandleBase(view, onClick, R.id.cvAdd, R.id.btnAdd) {}
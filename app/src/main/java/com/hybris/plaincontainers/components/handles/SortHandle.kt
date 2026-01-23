package com.hybris.plaincontainers.components.handles

import android.view.View
import com.hybris.plaincontainers.R

class SortHandle(view: View, onClick: () -> Unit)
    : ButtonHandleBase(view, onClick, R.id.cvSort, R.id.btnSort) {}
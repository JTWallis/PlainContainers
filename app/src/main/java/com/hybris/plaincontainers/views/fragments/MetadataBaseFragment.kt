package com.hybris.plaincontainers.views.fragments

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.buttoniconlabeled.ButtonIconLabeledHandle

abstract class MetadataBaseFragment: FragmentBase(R.layout.fragment_edit) {

    private lateinit var tvName: TextView
    private lateinit var etName: EditText
    private lateinit var tvPhoto: TextView
    private lateinit var ivPhoto: ImageView
    private var uriPhoto: String = "Nothing here yet"
    private lateinit var tvDescription: TextView
    private lateinit var etDescription: EditText
    private lateinit var layoutBtnDelete: CardView
    private var btnDeleteHandle: ButtonIconLabeledHandle? = null
    private lateinit var layoutBtnConfirm: CardView
    private lateinit var btnConfirmHandle: ButtonIconLabeledHandle


    protected abstract fun hasBtnDelete(): Boolean
    protected abstract fun getInitName(): String
    protected abstract fun getInitImageUri(): String
    protected abstract fun getInitDescription(): String
    protected abstract fun onBtnDeleteClick()
    protected abstract fun onBtnConfirmClick()

    override fun initViews(view: View) {
        tvName = view.findViewById(R.id.tvEditName)
        etName = view.findViewById(R.id.etEditName)
        tvPhoto = view.findViewById(R.id.tvEditPhoto)
        ivPhoto = view.findViewById(R.id.ivEditPhoto)
        tvDescription = view.findViewById(R.id.tvEditDescription)
        etDescription = view.findViewById(R.id.etEditDescription)

        layoutBtnDelete = view.findViewById(R.id.btnEditDelete)
        if(hasBtnDelete()) {
            btnDeleteHandle = ButtonIconLabeledHandle(
                layoutBtnDelete,
                onClick = { onBtnDeleteClick() },
                "Delete",
                R.drawable.trash_24,
                true
            )
            btnDeleteHandle!!.setBackgroundColor(R.color.button_red)
        } else {
            layoutBtnDelete.visibility = View.INVISIBLE
        }

        layoutBtnConfirm = view.findViewById(R.id.btnEditConfirm)

        btnConfirmHandle = ButtonIconLabeledHandle(
            layoutBtnConfirm,
            onClick = { onBtnConfirmClick() },
            "Apply",
            R.drawable.check_24,
            true
        )
        btnConfirmHandle.setBackgroundColor(R.color.button_green)

        initViewFillData()
    }

    protected open fun initViewFillData() {
        etName.setText(getInitName())
        //ivPhoto.setImageURI()
        etDescription.setText(getInitDescription())
    }

    protected open fun initListeners() {}

    protected fun getName(): String {
        return etName.text.toString()
    }

    protected fun getPhotoUri(): String {
        return uriPhoto
    }

    protected fun getDescription(): String {
        return etDescription.text.toString()
    }

}
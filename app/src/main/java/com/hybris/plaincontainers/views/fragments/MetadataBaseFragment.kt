package com.hybris.plaincontainers.views.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.cardview.widget.CardView
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.components.handles.buttoniconlabeled.ButtonIconLabeledHandle
import com.hybris.plaincontainers.views.choicepopup.ChoicePopup
import java.io.File
import androidx.core.net.toUri
import com.hybris.plaincontainers.data.FileUtils

abstract class MetadataBaseFragment: FragmentBase(R.layout.fragment_edit) {

    private lateinit var tvName: TextView
    private lateinit var etName: EditText
    private lateinit var tvPhoto: TextView
    private lateinit var ivPhoto: ImageView
    private var uriPhoto: Uri = Uri.EMPTY
    private lateinit var tvDescription: TextView
    private lateinit var etDescription: EditText
    private lateinit var layoutBtnDelete: CardView
    private var btnDeleteHandle: ButtonIconLabeledHandle? = null
    private lateinit var layoutBtnConfirm: CardView
    private lateinit var btnConfirmHandle: ButtonIconLabeledHandle
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>


    protected abstract fun hasBtnDelete(): Boolean
    protected abstract fun getInitName(): String
    protected abstract fun getInitImageUri(): String
    protected abstract fun getInitDescription(): String
    protected abstract fun onBtnConfirmClick()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPickMedia()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

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
                onClick = { onBtnDeleteClick(layoutBtnDelete) },
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
        etDescription.setText(getInitDescription())
        uriPhoto = getInitImageUri().toUri()
    }

    protected open fun initListeners() {
        ivPhoto.setOnClickListener { onPhotoClick(ivPhoto) }
    }

    private fun initPickMedia() {
        pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
            if(uri != null) {
                uriPhoto = FileUtils.createScaledThumbnail(requireContext(), uri)
            }
        }
    }


    private fun onPhotoClick(view: View) {
        val popup = ChoicePopup(
            view,
            onClickLeft = { onPhotoCaptureClick() },
            onClickRight = { onPhotoGalleryClick() }
        )
        popup.setTextTitle("Select method to add photo")
        popup.setTextSubtitle("")
        popup.setTextButtonLeft("Capture")
        popup.setTextButtonRight("From Gallery")

        popup.show(view)
    }

    private fun onPhotoCaptureClick() {

    }

    private fun onPhotoGalleryClick() {
        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
    }

    protected open fun onBtnDeleteClick(view: View) {

    }

    protected fun getName(): String {
        return etName.text.toString()
    }

    protected fun getPhotoUri(): String {
        return uriPhoto.toString()
    }

    protected fun getDescription(): String {
        return etDescription.text.toString()
    }

}
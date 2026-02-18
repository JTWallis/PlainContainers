package com.hybris.plaincontainers.views.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.data.FileUtils
import com.hybris.plaincontainers.data.viewmodels.MetadataBaseViewModel
import com.hybris.plaincontainers.views.warningpopup.WarningPopup
import kotlinx.coroutines.launch

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
                requireContext().getString(R.string.metadata_btn_delete),
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
            requireContext().getString(R.string.metadata_btn_apply),
            R.drawable.check_24,
            true
        )
        btnConfirmHandle.setBackgroundColor(R.color.button_green)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.entry.collect { _ ->
                    initViewFillData()
                }
            }
        }
    }

    protected open fun initViewFillData() {
        // When temporarily leaving the fragment, e.g. for the camera capture, this function gets
        //  called again, with no guarantee that it will be called before the camera callback.
        //  Thus, check for empty values to not accidentally override the fields.
        if(etName.text.isEmpty()) {
            etName.setText(getInitName())
        }

        if(etDescription.text.isEmpty()) {
            etDescription.setText(getInitDescription())
        }

        if(uriPhoto == Uri.EMPTY) {
            uriPhoto = getInitImageUri().toUri()
        }

        updatePhotoFromUri()
    }

    protected open fun initListeners() {
        ivPhoto.setOnClickListener { onPhotoClick(ivPhoto) }

        // Listener for returned Photo Capture
        parentFragmentManager.setFragmentResultListener(
            "capture_result",
            this
        ) {_, bundle ->
            val uri = bundle.getString("capture_uri")
            if(uri == null) {
                Log.e("ERROR", "Received null as uri!")
            } else {
                onPhotoCaptureReceived(uri.toUri())
            }
        }

        // Listener for when the user does not grant Camera permission
        parentFragmentManager.setFragmentResultListener(
            "capture_permission",
            this
        ) {_, bundle ->
            val permissionDenied = bundle.getBoolean("capture_permission_denied")
            if(permissionDenied) {
                onPhotoCapturePermissionDenied()
            }
        }
    }

    private fun initPickMedia() {
        pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
            if(uri != null) {
                uriPhoto = FileUtils.createScaledPhoto(requireContext(), uri)
                updatePhotoFromUri()
            }
        }
    }

    private fun updatePhotoFromUri() {
        if(!FileUtils.isValidUri(uriPhoto)) {
            return
        }

        ivPhoto.setImageURI(uriPhoto)
    }

    private fun onPhotoClick(view: View) {
        val popup = ChoicePopup(
            view,
            onClickLeft = { onPhotoCaptureClick() },
            onClickRight = { onPhotoGalleryClick() }
        )
        popup.setTextTitle(requireContext().getString(R.string.metadata_popup_photo_title))
        popup.setTextSubtitle("")
        popup.setTextButtonLeft(requireContext().getString(R.string.metadata_popup_photo_btn_capture))
        popup.setTextButtonRight(requireContext().getString(R.string.metadata_popup_photo_btn_gallery))

        popup.show(view)
    }

    private fun onPhotoCaptureClick() {
        findNavController().navigate(R.id.captureFragment)
    }

    private fun onPhotoCaptureReceived(uri: Uri) {
        uriPhoto = uri
        updatePhotoFromUri()
    }

    private fun onPhotoCapturePermissionDenied() {
        val popup = WarningPopup(
            requireView(),
            requireContext().getString(R.string.popup_warning_camera_permission_denied)
        )
        popup.show(requireView())
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
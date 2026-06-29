package com.hybris.plaincontainers.views.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import java.io.Serializable

abstract class CameraFragmentBase : FragmentBase(R.layout.fragment_capture) {
    protected lateinit var previewView: PreviewView
    protected lateinit var viewCrop: View
    protected lateinit var btnCapture: Button
    protected lateinit var useCase: UseCase

    protected abstract fun buildUseCase(): UseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        useCase = buildUseCase()
        checkRequestPermission()
    }

    override fun initViews(view: View) {
        previewView = view.findViewById(R.id.previewCapture)
        viewCrop = view.findViewById(R.id.viewCaptureCrop)
        btnCapture = view.findViewById(R.id.btnCapture)
    }

    protected fun startCamera() {
        val camProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        camProviderFuture.addListener({
            val camProvider = camProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.surfaceProvider = previewView.surfaceProvider
            val camSelector = CameraSelector.DEFAULT_BACK_CAMERA

            camProvider.unbindAll()
            camProvider.bindToLifecycle(
                viewLifecycleOwner,
                camSelector,
                preview,
                useCase
            )
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun checkRequestPermission() {
        val cameraPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { granted ->
            if(granted) startCamera()
            else onPermissionDenied()
        }

        val permission = android.Manifest.permission.CAMERA

        when {
            ContextCompat.checkSelfPermission(
                requireContext(), permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                startCamera()
            }
            shouldShowRequestPermissionRationale(permission) -> {
                onPermissionDenied()
            }
            else -> {
                cameraPermissionLauncher.launch(permission)
            }
        }
    }

    protected fun onPermissionDenied() {
        val deniedPermission = true
        parentFragmentManager.setFragmentResult(
            "capture_permission",
            bundleOf("capture_permission_denied" to deniedPermission)
        )

        findNavController().navigateUp()
    }

    override fun initPackageData() {}

    override fun getContainerPackage(): Serializable {
        return object : Serializable {}
    }
}
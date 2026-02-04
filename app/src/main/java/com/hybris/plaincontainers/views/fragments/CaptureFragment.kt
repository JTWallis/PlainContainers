package com.hybris.plaincontainers.views.fragments

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.R
import com.hybris.plaincontainers.data.FileUtils
import java.io.File
import java.io.Serializable


class CaptureFragment : FragmentBase(R.layout.fragment_capture) {

    private lateinit var viewCrop: View
    private lateinit var btnCapture: Button
    private lateinit var imageCapture: ImageCapture

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkRequestPermission()
    }

    override fun initViews(view: View) {
        viewCrop = view.findViewById(R.id.viewCaptureCrop)
        btnCapture = view.findViewById(R.id.btnCapture)
        btnCapture.setOnClickListener { takePhoto() }
    }

    private fun initCameraProvider() {
        val camProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        camProviderFuture.addListener({
            val camProvider = camProviderFuture.get()
            val preview = Preview.Builder().build()
            val camSelector = CameraSelector.DEFAULT_BACK_CAMERA

            camProvider.unbindAll()
            camProvider.bindToLifecycle(
                viewLifecycleOwner,
                camSelector,
                preview,
                imageCapture
            )
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun startCamera() {
        imageCapture = ImageCapture.Builder()
            .setTargetRotation(view!!.display.rotation)
            .build()

        initCameraProvider()
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

    private fun onPermissionDenied() {
        val deniedPermission = true
        parentFragmentManager.setFragmentResult(
            "capture_permission",
            bundleOf("capture_permission_denied" to deniedPermission)
        )

        findNavController().navigateUp()
    }

    private fun createBitmapFromImageProxy(image: ImageProxy): Bitmap {
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

        val rotation = image.imageInfo.rotationDegrees
        if(rotation == 0) return bitmap

        val matrix = Matrix().apply {
            postRotate(rotation.toFloat())
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun takePhoto() {
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(requireContext()),
            object: ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    val bitmap = createBitmapFromImageProxy(image)
                    image.close()

                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("ERROR", "Capture failed: $exception")
                    super.onError(exception)
                }
            }
        )
    }


    override fun initPackageData() {}

    override fun getAppbarTitle(): String {
        return "Capture Photo"
    }

    override fun getAppbarSubtitle(): String {
        return ""
    }

    override fun getContainerPackage(): Serializable {
        return object: Serializable {}
    }
}
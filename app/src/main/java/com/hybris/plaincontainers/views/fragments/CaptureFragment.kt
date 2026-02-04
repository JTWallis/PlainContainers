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
    }

    override fun initViews(view: View) {
        viewCrop = view.findViewById(R.id.viewCaptureCrop)
        btnCapture = view.findViewById(R.id.btnCapture)
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
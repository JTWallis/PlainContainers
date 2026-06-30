package com.hybris.plaincontainers.views.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.UseCase
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.hybris.plaincontainers.data.FileUtils


class CaptureFragment : CameraFragmentBase() {

    override fun initViews(view: View) {
        super.initViews(view)
        btnCapture.setOnClickListener { takePhoto() }
    }

    override fun buildUseCase(): UseCase {
        return ImageCapture.Builder()
            .setTargetRotation(requireView().display.rotation)
            .build()
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

    private fun cropBitmap(bitmap: Bitmap): Bitmap {
        val widthRatio = bitmap.width.toFloat() / requireView().width.toFloat()
        val heightRatio =  bitmap.height.toFloat() / requireView().height.toFloat()

        val rect = Rect(
            (viewCrop.left.coerceAtLeast(0) * widthRatio).toInt(),
            (viewCrop.top.coerceAtLeast(0) * heightRatio).toInt(),
            (viewCrop.right.coerceAtMost(bitmap.width) * widthRatio).toInt(),
            (viewCrop.bottom.coerceAtMost(bitmap.height) * heightRatio).toInt()
        )

        return Bitmap.createBitmap(
            bitmap,
            rect.left,
            rect.top,
            rect.right - rect.left,
            rect.bottom - rect.top
        )
    }

    fun takePhoto() {
        (useCase as ImageCapture).takePicture(
            ContextCompat.getMainExecutor(requireContext()),
            object: ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    val bitmap = createBitmapFromImageProxy(image)
                    image.close()

                    val cropped = cropBitmap(bitmap)
                    val uri = FileUtils.createScaledPhoto(
                        requireContext(),
                        cropped
                    ).toString()
                    parentFragmentManager.setFragmentResult(
                        "capture_result",
                        bundleOf("capture_uri" to uri)
                    )
                    findNavController().navigateUp()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("CaptureFragment", "takePhoto: Capture failed: $exception")
                    super.onError(exception)
                }
            }
        )
    }
}
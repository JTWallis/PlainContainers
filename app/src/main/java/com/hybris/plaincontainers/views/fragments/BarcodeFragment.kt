package com.hybris.plaincontainers.views.fragments

import android.view.View
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.UseCase
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.hybris.plaincontainers.BarcodeAnalyzer

class BarcodeFragment : CameraFragmentBase() {

    private val barcodeScanner by lazy { initBarcodeScanner() }

    override fun initViews(view: View) {
        super.initViews(view)
        viewCrop.visibility = View.INVISIBLE
        btnCapture.visibility = View.INVISIBLE
    }

    private fun initBarcodeScanner(): BarcodeScanner {
        initPackageData()

        return BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build()
        )
    }

    override fun buildUseCase(): UseCase {
        val analysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build();

        analysis.setAnalyzer(
            ContextCompat.getMainExecutor(requireContext()),
            BarcodeAnalyzer(barcodeScanner) { result ->
                returnScanResult(result)
            }
        )

        return analysis
    }

    private fun returnScanResult(text: String) {
        parentFragmentManager.setFragmentResult(
            "barcode_result",
            bundleOf("barcode_text" to text)
        )

        findNavController().navigateUp()
    }
}
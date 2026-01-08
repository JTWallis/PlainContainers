package com.hybris.plaincontainers

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(
    private val scanner: BarcodeScanner,
    private val onBarcodeDetected: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private var isProcessing = false;

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        if(isProcessing) {
            imageProxy.close();
            return;
        }

        val mediaImage = imageProxy.image;
        if(mediaImage == null) {
            imageProxy.close();
            return;
        }

        isProcessing = true;
        val image = InputImage.fromMediaImage(
            mediaImage,
            imageProxy.imageInfo.rotationDegrees
        );

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                barcodes.firstOrNull()?.rawValue?.let {
                    onBarcodeDetected(it);
                }
            }
            .addOnFailureListener {
                println("Failure on processing Barcode image!");
            }
            .addOnCompleteListener {
                isProcessing = false;
                imageProxy.close();
            }

    }
}
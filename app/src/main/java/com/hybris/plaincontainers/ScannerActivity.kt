package com.hybris.plaincontainers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode

class ScannerActivity : AppCompatActivity() {

    private lateinit var cameraPreviewView: PreviewView;
    private val barcodeScanner by lazy { initBarcodeScanner() };


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("Created ScannerActivity")

        setContentView(R.layout.activity_scanner);

        cameraPreviewView = findViewById(R.id.cameraPreviewView);
        startCamera();
    }

    override fun onDestroy() {
        super.onDestroy();
    }

    private fun initBarcodeScanner(): BarcodeScanner {
        return BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build());
    }

    private fun startCamera() {
        val camProviderFuture = ProcessCameraProvider.getInstance(this);

        camProviderFuture.addListener({
            val camProvider = camProviderFuture.get();

            val preview = androidx.camera.core.Preview.Builder().build().also {
                it.setSurfaceProvider(cameraPreviewView.surfaceProvider);
            }

            val analysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

            analysis.setAnalyzer(
                ContextCompat.getMainExecutor(this),
                BarcodeAnalyzer(barcodeScanner) { result ->
                    returnScanResult(result);
                }
            );

            camProvider.unbindAll();
            camProvider.bindToLifecycle(
                this,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                analysis
            );

        }, ContextCompat.getMainExecutor(this));
    }

    private fun returnScanResult(text: String) {
        val intent = Intent().apply {
            putExtra("barcode", text);
        }

        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
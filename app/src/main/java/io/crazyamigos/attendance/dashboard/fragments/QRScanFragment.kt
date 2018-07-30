package io.crazyamigos.attendance.dashboard.fragments

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import io.crazyamigos.attendance.R
import kotlinx.android.synthetic.main.qr_scan_fragment.*

class QRScanFragment : Fragment() {

    var qrResult: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.qr_scan_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleCameraPermission()

        setupQR()

    }

    fun setupQR() {
        qrCodeView.setQRDecodingEnabled(true)
        qrCodeView.setOnQRCodeReadListener { text, points ->
            if (text != qrResult) {

                // DO API CALLS HERE WITH vARIABLE 'qrResult'



                qrResult = text
            }
        }
    }


    fun handleCameraPermission() {
        Dexter.withActivity(context as AppCompatActivity)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        setupQR()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {


                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    }
                }).check()
    }

}
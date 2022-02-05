package com.example.shibarichat


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.R
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
class CameraActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {
    private lateinit var zbView: ZBarScannerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zbView = ZBarScannerView(this)
        setContentView(zbView)

    }

    override fun onResume() {
        super.onResume()
        zbView.setResultHandler(this)
        zbView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zbView.stopCamera()
    }

    override fun onStart() {
        super.onStart()
        zbView.setResultHandler { this }

    }

    override fun handleResult(result: Result?) {
//        Toast.makeText(this, "${result?.contents}", Toast.LENGTH_SHORT).show()

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
            .setMessage("${result?.contents}")
            .setPositiveButton("Выполнять!"
            ) { dialog, id -> startActivity(Intent(this, MainActivity::class.java)) }
        val alert = builder.create()
        alert.show()
    }
}
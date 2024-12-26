package com.akshay.callrecord

import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import android.Manifest
import androidx.core.app.ActivityCompat

class MainActivity: FlutterActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        // Request permissions at runtime
        val permissions = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        ActivityCompat.requestPermissions(this, permissions, 100)
        super.onCreate(savedInstanceState)
    }
}

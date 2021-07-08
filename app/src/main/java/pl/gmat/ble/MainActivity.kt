package pl.gmat.ble

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher = registerForActivityResult(RequestPermission()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.startScanButton).setOnClickListener {
            startScan()
        }
        findViewById<Button>(R.id.stopScanButton).setOnClickListener {
            stopScan()
        }
        checkPermissions()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}
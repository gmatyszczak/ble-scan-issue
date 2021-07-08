package pl.gmat.ble

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.bluetooth.le.BluetoothLeScanner
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

@RequiresApi(Build.VERSION_CODES.O)
class BleCallbackService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground()
        stopScan()
        val callbackType = intent!!.getIntExtra(BluetoothLeScanner.EXTRA_CALLBACK_TYPE, -1)
        val errorCode = intent.getIntExtra(BluetoothLeScanner.EXTRA_ERROR_CODE, -1)
        val results = intent.getSerializableExtra(BluetoothLeScanner.EXTRA_LIST_SCAN_RESULT)
        Log.d("BleScanIssue", "onStartCommand: callbackType = $callbackType, errorCode = $errorCode, results = $results")
        stopForeground(true)
        stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForeground() {
        NotificationManagerCompat.from(this)
            .createNotificationChannel(
                NotificationChannel(
                    "ble_scan_result",
                    "Ble Scan Result",
                    NotificationManager.IMPORTANCE_LOW
                )
            )
        val notification = NotificationCompat.Builder(this, "ble_scan_result")
            .setContentTitle("Scan Result Received")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(100, notification)
    }

}
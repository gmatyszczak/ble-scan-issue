package pl.gmat.ble

import android.app.PendingIntent
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService


@RequiresApi(Build.VERSION_CODES.O)
fun Context.startScan() {
    val scanFilter = ScanFilter.Builder()
        .setManufacturerData(0x1111, byteArrayOf(0x00))
        .build()
    val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
        .setCallbackType(ScanSettings.CALLBACK_TYPE_FIRST_MATCH)
        .build()
    val code = getBluetoothLeScanner().startScan(
        listOf(scanFilter),
        scanSettings,
        buildPendingIntent()
    )
    Log.d("BleScanIssue", "startScan: code = $code")
}

@RequiresApi(Build.VERSION_CODES.O)
fun Context.stopScan() {
    getBluetoothLeScanner().stopScan(buildPendingIntent())
}

private fun Context.getBluetoothLeScanner() =
    getSystemService<BluetoothManager>()!!.adapter.bluetoothLeScanner

@RequiresApi(Build.VERSION_CODES.O)
private fun Context.buildPendingIntent(): PendingIntent {
    val intent = Intent(this, BleCallbackService::class.java)
    return PendingIntent.getForegroundService(
        this,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
}

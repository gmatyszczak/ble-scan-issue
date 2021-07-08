# Android BLE scan issue

Simple app to demonstrate issue with BLE background scanner available for Android 8+ when using CALLBACK_TYPE_FIRST_MATCH.

## Problem

When there are 2 apps starting background scanner with PendingIntent callback for the same BLE device with CALLBACK_TYPE_FIRST_MATCH, filtered by manufacturer data, then only the second app that started scanner will receive callback twice.

## Prerequisites 

To reproduce that you will need two devices, one with apps installed and second acting as a BLE advertiser. For the BLE advertising you can install e.g. nRF Connect app and set up Advertiser there with Advertising Data:

Manufacturer Data: <br>
Company ID: 0x1111 <br>
Data: 0x00 <br>

[screen]

## Steps to reproduce
- Install 2 variants of the app on one device (e.g. debug and alpha)
- Press Start Background Scan first in debug app, and then in alpha app - it will start background scanner for the same BLE device
- On your second device start BLE Advertiser
- Observe that on your first device, alpha app received the scan callback twice, debug didn’t get any callbacks (you can filter by `BleScanIssue` tag in Logcat)



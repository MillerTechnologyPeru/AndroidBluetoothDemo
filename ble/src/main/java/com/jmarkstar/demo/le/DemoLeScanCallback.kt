package com.jmarkstar.demo.le

import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log
import com.jmarkstar.demo.LeDevicesActivity

class DemoLeScanCallback(activity: LeDevicesActivity): ScanCallback() {

    override fun onBatchScanResults(results: MutableList<ScanResult>?) {
        super.onBatchScanResults(results)


    }

    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        super.onScanResult(callbackType, result)

        Log.i("DemoLeScanCallback", "${result?.device} - ${result?.rssi}")
    }

    override fun onScanFailed(errorCode: Int) {
        super.onScanFailed(errorCode)

        Log.e("DemoLeScanCallback", "$errorCode = error scanning")
    }
}
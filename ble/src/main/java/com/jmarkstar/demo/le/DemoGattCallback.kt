package com.jmarkstar.demo.le

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.widget.Toast
import com.jmarkstar.demo.LeDeviceDetailActivity

class DemoGattCallback(private val activity: LeDeviceDetailActivity): BluetoothGattCallback() {

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)

        if(status != BluetoothGatt.GATT_SUCCESS){
            Toast.makeText(activity, "Gatt connection error: $status", Toast.LENGTH_SHORT).show()
            activity.disconnected()
            return
        }

        if(newState == BluetoothProfile.STATE_CONNECTED){
            activity.gatt = gatt
            activity.connected()
        } else if(newState == BluetoothProfile.STATE_DISCONNECTED){
            activity.disconnected()
        }
    }
}
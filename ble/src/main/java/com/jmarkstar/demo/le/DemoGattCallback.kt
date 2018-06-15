package com.jmarkstar.demo.le

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.os.Handler
import com.jmarkstar.demo.LeDeviceDetailActivity

class DemoGattCallback(private val activity: LeDeviceDetailActivity): BluetoothGattCallback() {

    private val handler = Handler(activity.mainLooper)

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)

        if(status != BluetoothGatt.GATT_SUCCESS){
            handler.post{
                activity.disconnected()
                activity.connectionFailure(status)
            }
            return
        }

        if(newState == BluetoothProfile.STATE_CONNECTED){
            handler.post{
                activity.gatt = gatt
                activity.connected()
            }
        } else if(newState == BluetoothProfile.STATE_DISCONNECTED){
            handler.post{
                activity.disconnected()
            }
        }
    }
}
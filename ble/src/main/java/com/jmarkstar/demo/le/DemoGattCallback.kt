package com.jmarkstar.demo.le

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.os.Handler
import android.util.Log
import com.jmarkstar.demo.LeDeviceDetailActivity

class DemoGattCallback(private val activity: LeDeviceDetailActivity): BluetoothGattCallback() {

    private val handler = Handler(activity.mainLooper)

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)

        if(!handleError(status)){
            return
        }

        if(newState == BluetoothProfile.STATE_CONNECTED){
            handler.post{
                activity.gatt = gatt
                activity.connected()

                gatt?.discoverServices()
            }
        } else if(newState == BluetoothProfile.STATE_DISCONNECTED){
            handler.post{
                activity.disconnected()
            }
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        super.onServicesDiscovered(gatt, status)

        if(!handleError(status)){
            return
        }

        gatt?.services?.forEachIndexed { index, service ->
            Log.v("gatt service $index", "${DemoLeUtils.showServiceType(service.type)} - ${service.uuid}")
            service.characteristics.forEachIndexed { index, characteristic ->
                Log.v("gatt characteristic $index", characteristic.uuid.toString())
            }
        }
    }

    private fun handleError(status: Int): Boolean{
        if(status != BluetoothGatt.GATT_SUCCESS){
            handler.post{
                activity.disconnected()
                activity.connectionFailure(status)
            }
            return false
        }
        return true
    }
}
package com.jmarkstar.bluetoothdemo

import android.bluetooth.BluetoothDevice
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class DeviceDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_detail)

        val device = intent.getParcelableExtra<BluetoothDevice>("device")

        Log.i("DeviceDetailActivity", "Bluetooth Device: ${device.name}, ${device.address}, ${device.bondState}, ${device.type}")

        if(device.uuids != null)
            for(uuid in device.uuids){
                Log.i("DeviceDetailActivity","Bluetooth Device: ${device.name} - uuid: $uuid")
            }

        Log.i("DeviceDetailActivity", showBondState(device.bondState))
        Log.i("DeviceDetailActivity", showDeviceType(device.type))
    }

    private fun showBondState(state: Int): String{
        return when(state){
            10 -> "BOND_NONE"
            11 -> "BOND_BONDING"
            12 -> "BOND_BONDED"
            else -> ""
        }
    }

    private fun showDeviceType(type: Int): String{
        return when(type){
            0 -> "DEVICE_TYPE_UNKNOWN"
            1 -> "DEVICE_TYPE_CLASSIC"
            2 -> "DEVICE_TYPE_LE"
            3 -> "DEVICE_TYPE_DUAL"
            else -> ""
        }
    }
}

package com.jmarkstar.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jmarkstar.demo.le.DemoLeDevice

class LeDeviceDetailActivity : AppCompatActivity() {

    companion object {
        const val DEVICE_PARAM = "intent_device_param"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_le_device_detail)

        val demoLeDevice = intent.getParcelableExtra<DemoLeDevice>(DEVICE_PARAM)

        Log.i("LeDeviceDetail", "name = ${demoLeDevice.data.name}")
        Log.i("LeDeviceDetail", "address = ${demoLeDevice.data.address}")
        Log.i("LeDeviceDetail", "type = ${showDeviceType(demoLeDevice.data.type)}")
        Log.i("LeDeviceDetail", "bond state = ${showBondState(demoLeDevice.data.bondState)}")
        Log.i("LeDeviceDetail", "device class number = ${demoLeDevice.data.bluetoothClass.deviceClass}")
        Log.i("LeDeviceDetail", "Major device class = ${showDeviceClass(demoLeDevice.data.bluetoothClass.majorDeviceClass)}")
        //Log.i("LeDeviceDetail", "name = ${demoLeDevice.data.bluetoothClass.}")
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

    private fun showDeviceClass(type: Int): String{
        return when(type){
            1024 -> "AUDIO_VIDEO"
            256 -> "COMPUTER"
            2304 -> "HEALTH"
            1536 -> "IMAGING"
            0 -> "MISC"
            768 -> "NETWORKING"
            1280 -> "PERIPHERAL"
            512 -> "PHONE"
            2048 -> "TOY"
            7936 -> "UNCATEGORIZED"
            1792 -> "WEARABLE"
            else -> ""
        }
    }
}

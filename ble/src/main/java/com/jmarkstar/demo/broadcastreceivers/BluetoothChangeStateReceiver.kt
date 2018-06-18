package com.jmarkstar.demo.broadcastreceivers

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jmarkstar.demo.view.devices.LeDevicesActivity

class BluetoothChangeStateReceiver(private val activity: LeDevicesActivity): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val state = intent?.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
        val previousState = intent?.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, -1)

        Log.v("BroadcastReceiver", "$previousState - $state")

        if(state == BluetoothAdapter.STATE_ON){
            activity.verifyGpsPermission()
        }
    }
}
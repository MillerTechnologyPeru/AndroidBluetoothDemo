package com.jmarkstar.ble

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jmarkstar.ble.broadcastreceivers.BluetoothChangeStateReceiver
import kotlinx.android.synthetic.main.activity_le_devices.*

class LeDevicesActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_ENABLE_BT = 1000
        const val REQUEST_PERMISSION_GPS = 1200
    }

    private var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private var bluetoothChangeStateReceiver : BluetoothChangeStateReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_le_devices)

        btnScanLeDevices.setOnClickListener {
            onScanLeDevices()
        }
    }

    override fun onResume() {
        super.onResume()
        //Enable
        bluetoothChangeStateReceiver = BluetoothChangeStateReceiver(this)
        val intentFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(bluetoothChangeStateReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(bluetoothChangeStateReceiver)
    }

    // #1 VERIFY IF BLUETOOTH IS ENABLED
    private fun onScanLeDevices(){
        Log.i("MainActivity","starting discovery")

        if(!bluetoothAdapter.isEnabled){
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT)
            return
        }

        verifyGpsPermission()
    }

    // #2 VERIFY IF THE GPS PERMISSION IS ALLOWED
    internal fun verifyGpsPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1200)
        }else{
            startDiscovery()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_PERMISSION_GPS){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startDiscovery()
            }else{
                Toast.makeText(this, "GPS Permission is required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //#3 SCAN THE DEVICES
    private fun startDiscovery() {

        pgScanning.visibility = View.VISIBLE

        //bluetoothAdapter.bluetoothLeScanner.startScan()

        //bluetoothAdapter.bluetoothLeScanner.stopScan()

        Toast.makeText(this, "Ready for scanning", Toast.LENGTH_SHORT).show()
    }
}

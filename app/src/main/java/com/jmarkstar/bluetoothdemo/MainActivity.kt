package com.jmarkstar.bluetoothdemo

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_ENABLE_BT = 1000
        const val REQUEST_PERMISSION_GPS = 1200
    }

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private var isDiscovering = false

    // BluetoothAdapter.ACTION_STATE_CHANGED
    private val blueoothChangeStateReceiver = object: BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent) {
            val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
            val previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, -1)

            Log.v("BroadcastReceiver", "$previousState - $state")
            handleStates(state)
        }
    }

    // BluetoothAdapter.ACTION_DISCOVERY_STARTED
    private val bluetoothDiscoveryStarted = object: BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent) {
            tvDiscoveryMessage.text = "Discovery have started"
            tvDiscoveryMessage.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.green))
        }
    }

    // BluetoothAdapter.ACTION_DISCOVERY_FINISHED
    private val bluetoothDiscoveryFinished = object: BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent?) {
            tvDiscoveryMessage.text = "Discovery have finished"
            tvDiscoveryMessage.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.red))
        }
    }

    // BluetoothDevice.ACTION_FOUND
    private val bluetoothDiscoveredDevices = object: BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent) {

            // Get the bluetoothDevice object from the Intent
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)

            // Get the "RSSI" to get the signal strength as integer,
            // but should be displayed in "dBm" units
            val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, java.lang.Short.MIN_VALUE)

            deviceAdapter.addDevice(device, rssi)

            Log.i("MainActivity", "Bluetooth Device: ${device.name}, ${device.address}, ${device.bondState}, ${device.type}, $rssi ")
            if(device.uuids != null)
                for(uuid in device.uuids){
                    Log.i("MainActivity","Bluetooth Device: ${device.name} - uuid: $uuid")
                }
        }
    }

    private val deviceAdapter = BluetoothDeviceAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvDevices.adapter = deviceAdapter
        rvDevices.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        btnEnable.setOnClickListener {
            isDiscovering = false
            turnOnBluettothByUser()
        }

        handleStates()

        btnBluetoothState.setOnClickListener {
            when(bluetoothAdapter.state){
                BluetoothAdapter.STATE_ON -> bluetoothAdapter.disable()
                BluetoothAdapter.STATE_OFF -> bluetoothAdapter.enable()
            }
        }

        btnStartDiscovery.setOnClickListener {
            Log.i("MainActivity","starting discovery")

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    startDiscovery()
                } else {
                    requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1200)
                }
            }else{
                startDiscovery()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        //Enable
        val intentFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(blueoothChangeStateReceiver, intentFilter)

        //Discovery
        val intentDiscoverFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(bluetoothDiscoveredDevices, intentDiscoverFilter)

        val intentDiscoveryStartedFilter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        registerReceiver(bluetoothDiscoveryStarted, intentDiscoveryStartedFilter)

        val intentDiscoveryFinishedFilter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(bluetoothDiscoveryFinished, intentDiscoveryFinishedFilter)
    }

    override fun onPause() {
        super.onPause()

        //Enable
        unregisterReceiver(blueoothChangeStateReceiver)

        //Discovery
        unregisterReceiver(bluetoothDiscoveredDevices)
        unregisterReceiver(bluetoothDiscoveryStarted)
        unregisterReceiver(bluetoothDiscoveryFinished)

        if(bluetoothAdapter.isDiscovering)
            bluetoothAdapter.cancelDiscovery()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_ENABLE_BT){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "Bluetooth is allowed now", Toast.LENGTH_SHORT).show()
                handleStates()

                if(isDiscovering)
                    startDiscovery()
            }else{
                Toast.makeText(this, "Bluetooth was not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startDiscovery(){
        deviceAdapter.refresh()
        if(bluetoothAdapter.isDiscovering)
            bluetoothAdapter.cancelDiscovery()
        val state = bluetoothAdapter.startDiscovery()
        if(!state){
            isDiscovering = true
            turnOnBluettothByUser()
        }
    }

    private fun turnOnBluettothByUser(){
        if(!bluetoothAdapter.isEnabled){
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT)
        }
    }

    private fun handleStates(state: Int = bluetoothAdapter.state){

        var stateString = when(state){
            BluetoothAdapter.STATE_OFF -> {
                tvBluetoothStateMessage.setTextColor(ContextCompat.getColor(this, R.color.green))
                tvBluetoothStateMessage.text = getString(R.string.message_bt_turn_on)
                "STATE_OFF"
            }
            BluetoothAdapter.STATE_ON -> {
                tvBluetoothStateMessage.setTextColor(ContextCompat.getColor(this, R.color.red))
                tvBluetoothStateMessage.text = getString(R.string.message_bt_turn_off)
                "STATE_ON"
            }
            BluetoothAdapter.STATE_TURNING_OFF ->
                "STATE_TURNING_OFF"
            BluetoothAdapter.STATE_TURNING_ON ->
                "STATE_TURNING_ON"
            else -> "ERROR"
        }

        btnBluetoothState.text = stateString
    }
}

package com.jmarkstar.bluetoothdemo

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_ENABLE_BT = 1000
    }

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private val blueoothChangeStateReceiver = object: BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent?) {
            val state = intent?.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
            val previousState = intent?.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, -1)

            Log.v("BroadcastReceiver", "$previousState - $state")
            handleStates(state!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEnable.setOnClickListener {
            if(bluetoothAdapter.isEnabled){
                Toast.makeText(this, "Bluetooth is enabled", Toast.LENGTH_SHORT).show()
            }else{
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT)
            }
        }

        handleStates()
        btnBluetoothState.setOnClickListener {
            when(bluetoothAdapter.state){
                BluetoothAdapter.STATE_ON -> bluetoothAdapter.disable()
                BluetoothAdapter.STATE_OFF -> bluetoothAdapter.enable()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(blueoothChangeStateReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(blueoothChangeStateReceiver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_ENABLE_BT){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "Bluetooth is allowed now", Toast.LENGTH_SHORT).show()
                handleStates()
            }else{
                Toast.makeText(this, "Bluetooth was not allowed", Toast.LENGTH_SHORT).show()
            }
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

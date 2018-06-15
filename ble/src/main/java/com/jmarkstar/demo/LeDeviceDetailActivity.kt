package com.jmarkstar.demo

import android.bluetooth.BluetoothGatt
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jmarkstar.demo.le.DemoGattCallback
import com.jmarkstar.demo.le.DemoLeDevice
import com.jmarkstar.demo.le.DemoLeUtils
import kotlinx.android.synthetic.main.activity_le_device_detail.*

class LeDeviceDetailActivity : AppCompatActivity() {

    companion object {
        const val DEVICE_PARAM = "intent_device_param"
    }

    private var gattCallback: DemoGattCallback? = null
    private var demoLeDevice: DemoLeDevice? = null

    var isConnected = false
    var gatt: BluetoothGatt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_le_device_detail)

        gattCallback = DemoGattCallback(this)

        demoLeDevice = intent.getParcelableExtra<DemoLeDevice>(DEVICE_PARAM)

        tvDeviceName.text = String.format(getString(R.string.device_name), demoLeDevice?.data?.name ?: "No Name")
        tvDeviceAddress.text = String.format(getString(R.string.device_address), demoLeDevice?.data?.address)
        tvDeviceType.text = String.format(getString(R.string.device_type), DemoLeUtils.showDeviceType(demoLeDevice?.data?.type!!))
        tvDeviceClassNumber.text = String.format(getString(R.string.device_class_number), demoLeDevice?.data?.bluetoothClass?.deviceClass)
        tvMajorDeviceClass.text = String.format(getString(R.string.major_device_class), DemoLeUtils.showDeviceClass(demoLeDevice?.data?.bluetoothClass?.majorDeviceClass!!))
        tvDeviceBondState.text = String.format(getString(R.string.bond_state), DemoLeUtils.showBondState(demoLeDevice?.data?.bondState!!))

        btnConnectGatt.setOnClickListener {
            if(isConnected){
                disconnectGatt()
            } else {
                connectGatt()
            }
        }
    }

    private fun connectGatt(){

        demoLeDevice?.data?.connectGatt(this, false, gattCallback)

        btnConnectGatt.visibility = View.GONE
        pgConnecting.visibility = View.VISIBLE


    }

    private fun disconnectGatt(){
        if(gatt!=null){
            gatt?.disconnect()
            gatt?.close()
            isConnected = false
        }
    }

    fun connected(){
        btnConnectGatt.post {
            btnConnectGatt.text = getString(R.string.gatt_disconnect)
            btnConnectGatt.visibility = View.VISIBLE
            pgConnecting.visibility = View.GONE
            isConnected = true
        }
    }

    fun disconnected(){
        btnConnectGatt.post {
            btnConnectGatt.text = getString(R.string.gatt_connect)
            btnConnectGatt.visibility = View.VISIBLE
            pgConnecting.visibility = View.GONE
            isConnected = false
        }
    }
}
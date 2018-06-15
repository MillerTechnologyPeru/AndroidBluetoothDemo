package com.jmarkstar.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jmarkstar.demo.le.DemoLeDevice
import com.jmarkstar.demo.le.DemoLeUtils
import kotlinx.android.synthetic.main.activity_le_device_detail.*

class LeDeviceDetailActivity : AppCompatActivity() {

    companion object {
        const val DEVICE_PARAM = "intent_device_param"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_le_device_detail)

        val demoLeDevice = intent.getParcelableExtra<DemoLeDevice>(DEVICE_PARAM)

        tvDeviceName.text = String.format(getString(R.string.device_name), demoLeDevice.data.name ?: "No Name")
        tvDeviceAddress.text = String.format(getString(R.string.device_address), demoLeDevice.data.address)
        tvDeviceType.text = String.format(getString(R.string.device_type), DemoLeUtils.showDeviceType(demoLeDevice.data.type))
        tvDeviceClassNumber.text = String.format(getString(R.string.device_class_number), demoLeDevice.data.bluetoothClass.deviceClass)
        tvMajorDeviceClass.text = String.format(getString(R.string.major_device_class), DemoLeUtils.showDeviceClass(demoLeDevice.data.bluetoothClass.majorDeviceClass))
        tvDeviceBondState.text = String.format(getString(R.string.bond_state), DemoLeUtils.showBondState(demoLeDevice.data.bondState))

        btnConnectGatt.setOnClickListener {

        }
    }
}

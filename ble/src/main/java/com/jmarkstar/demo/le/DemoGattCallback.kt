package com.jmarkstar.demo.le

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
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

        Log.i("gatt ", gatt?.device?.name)

        gatt?.services?.forEachIndexed { serviceIndex, service ->

            Log.v("gatt service $serviceIndex", "${DemoLeUtils.showServiceType(service.type)} - ${service.uuid}")
            service.characteristics.forEachIndexed { characIndex, characteristic ->

                gatt.readCharacteristic(characteristic)
                Log.v("onServicesDiscovered", characteristic?.uuid.toString())
                characteristic.descriptors.forEachIndexed { index, bluetoothGattDescriptor ->
                    Log.v("gatt descriptor $index", "${DemoLeUtils.showCharacteristicPermission(bluetoothGattDescriptor.permissions)}}")
                }
            }
        }
    }

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {

        Log.i("onCharacteristicRead", "status = ${DemoLeUtils.showGattStatus(status)}")

        Log.v("onCharacteristicRead", "${characteristic?.uuid} - " +
                "${DemoLeUtils.showCharacteristicPropery(characteristic?.properties!!)} - " +
                "${DemoLeUtils.showCharacteristicPermission(characteristic.permissions)} " +
                "${characteristic.value} - ")
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
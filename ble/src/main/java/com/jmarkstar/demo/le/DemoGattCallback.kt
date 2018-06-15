package com.jmarkstar.demo.le

import android.bluetooth.*
import android.os.Handler
import android.util.Log
import com.jmarkstar.demo.view.detail.GattCharacteristic
import com.jmarkstar.demo.view.detail.GattInfoItem
import com.jmarkstar.demo.view.detail.GattService
import com.jmarkstar.demo.view.detail.LeDeviceDetailActivity

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

        Log.i("onServicesDiscovered", "status = ${DemoLeUtils.showGattStatus(status)}")
        if(!handleError(status)){
            return
        }

        val gattInfoList = ArrayList<GattInfoItem>()

        gatt?.services?.forEachIndexed { serviceIndex, service ->

            gattInfoList.add(GattService(service.uuid.toString(), DemoLeUtils.showServiceType(service.type)))

            Log.v("gatt service $serviceIndex", "${DemoLeUtils.showServiceType(service.type)} - ${service.uuid}")

            service.characteristics.forEachIndexed { characIndex, characteristic ->

                gattInfoList.add(GattCharacteristic(characteristic?.uuid.toString(), DemoLeUtils.showCharacteristicPropery(characteristic?.properties!!), DemoLeUtils.showCharacteristicPermission(characteristic.permissions)))

                Log.v("onCharacteristicRead", "${characteristic?.uuid} - " +
                        "${DemoLeUtils.showCharacteristicPropery(characteristic?.properties!!)} - " +
                        "${DemoLeUtils.showCharacteristicPermission(characteristic.permissions)} ")
                //gatt.readCharacteristic(characteristic)
            }
        }

        handler.post{
            activity.showGattInfo(gattInfoList)
        }
    }

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
        super.onCharacteristicRead(gatt, characteristic, status)

        Log.i("onCharacteristicRead", "status = ${DemoLeUtils.showGattStatus(status)}")
        if(!handleError(status)){
            return
        }

        Log.v("onCharacteristicRead", "${characteristic?.uuid} - " +
                "${DemoLeUtils.showCharacteristicPropery(characteristic?.properties!!)} - " +
                "${DemoLeUtils.showCharacteristicPermission(characteristic.permissions)} ")

        var bytes = ""
        for( byte in characteristic.value) {
            bytes += "$byte "
        }

        Log.v("onCharacteristicRead", bytes)

        characteristic.descriptors.forEachIndexed { index, bluetoothGattDescriptor ->

            gatt?.readDescriptor(bluetoothGattDescriptor)
        }
    }

    override fun onDescriptorRead(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
        super.onDescriptorRead(gatt, descriptor, status)

        Log.i("onDescriptorRead", "status = ${DemoLeUtils.showGattStatus(status)}")
        if(!handleError(status)){
            return
        }

        Log.v("onDescriptorRead", "Permission = ${DemoLeUtils.showCharacteristicPermission(descriptor?.permissions!!)}}")
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
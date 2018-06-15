package com.jmarkstar.demo.view.detail

class GattCharacteristic(val uuid: String, val propery: String, val permission: String): GattItemType {

    override fun getGattItemType(): Int {
        return GATT_CHARACTERISTIC
    }
}
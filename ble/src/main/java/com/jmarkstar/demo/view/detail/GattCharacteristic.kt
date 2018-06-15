package com.jmarkstar.demo.view.detail

class GattCharacteristic(val uuid: String, val propery: String, val permission: String): GattInfoItem {

    override fun getGattItemType(): Int {
        return GattInfoItem.GATT_CHARACTERISTIC
    }
}
package com.jmarkstar.demo.view.detail

class GattService(val uuid: String, val serviceType: String): GattInfoItem {

    override fun getGattItemType(): Int {
        return GattInfoItem.GATT_SERVICE
    }
}
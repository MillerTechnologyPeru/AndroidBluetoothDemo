package com.jmarkstar.demo.view.detail

class GattService(val uuid: String, val serviceType: String): GattItemType {

    override fun getGattItemType(): Int {
        return GATT_SERVICE
    }
}
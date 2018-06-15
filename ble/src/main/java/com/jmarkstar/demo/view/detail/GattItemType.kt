package com.jmarkstar.demo.view.detail

interface GattItemType {
    val GATT_SERVICE: Int
        get() = 1

    val GATT_CHARACTERISTIC: Int
        get() = 2

    fun getGattItemType(): Int
}
package com.jmarkstar.demo.le

import android.bluetooth.BluetoothDevice
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DemoLeDevice(val data: BluetoothDevice, var rssi: Int): Parcelable
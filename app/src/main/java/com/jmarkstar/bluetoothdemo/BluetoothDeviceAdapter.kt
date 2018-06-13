package com.jmarkstar.bluetoothdemo

import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.device_item.view.*

class BluetoothDeviceAdapter: RecyclerView.Adapter<BluetoothDeviceAdapter.DeviceVH>() {

    private val devices = ArrayList<BluetoothDevice>()
    private val rssiList = ArrayList<Short>()

    fun addDevice(newDevice: BluetoothDevice, rssi: Short){

        var alreadyExists = false

        devices.forEachIndexed { index, bluetoothDevice ->
            if(bluetoothDevice.address == newDevice.address){
                alreadyExists = true
                rssiList[index] = rssi
                notifyItemChanged(index)
            }
        }

        if(!alreadyExists){
            devices.add(newDevice)
            rssiList.add(rssi)
            notifyItemInserted(devices.size-1)
        }
    }

    fun refresh(){
        devices.clear()
        rssiList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.device_item, parent, false)
        return DeviceVH(view)
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: DeviceVH, position: Int) {
        val device = devices[position]
        holder.tvName.text = device.name ?: "No Name"
        holder.tvAddress.text = device.address
        holder.tvRssi.text = "${rssiList[position]}"
    }

    class DeviceVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.tvName
        val tvAddress = itemView.tvAddress
        val tvRssi = itemView.tvRssi
    }
}
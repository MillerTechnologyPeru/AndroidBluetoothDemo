package com.jmarkstar.demo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmarkstar.demo.le.DemoBleDevice
import kotlinx.android.synthetic.main.activity_devices_item.view.*

class BleDeviceAdapter(var onDeviceClick: ((DemoBleDevice) -> Unit)? = null): RecyclerView.Adapter<BleDeviceAdapter.DeviceVH>() {

    private val devices = ArrayList<DemoBleDevice>()

    fun addDevice(newDevice: DemoBleDevice){

        var alreadyExists = false

        devices.forEachIndexed { index, bluetoothDevice ->
            if(bluetoothDevice.device.address == newDevice.device.address){
                alreadyExists = true
                bluetoothDevice.rssi = newDevice.rssi
                notifyItemChanged(index)
            }
        }

        if(!alreadyExists){
            devices.add(newDevice)
            notifyItemInserted(devices.size-1)
        }
    }

    fun refresh(){
        devices.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_devices_item, parent, false)
        return DeviceVH(view)
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: DeviceVH, position: Int) {
        val demoDevice = devices[position]
        holder.tvName.text = demoDevice.device.name ?: "No Name"
        holder.tvAddress.text = demoDevice.device.address
        holder.tvRssi.text = "${demoDevice.rssi}"

        holder.itemView.setOnClickListener {
            if(onDeviceClick!=null){
                onDeviceClick?.invoke(demoDevice)
            }
        }
    }

    class DeviceVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivLogo = itemView.ivLogo
        val tvName = itemView.tvName
        val tvAddress = itemView.tvAddress
        val tvRssi = itemView.tvRssi
    }
}
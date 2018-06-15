package com.jmarkstar.demo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmarkstar.demo.le.DemoLeDevice
import kotlinx.android.synthetic.main.activity_devices_item.view.*

class BleDeviceAdapter(var onDeviceClick: ((DemoLeDevice) -> Unit)? = null): RecyclerView.Adapter<BleDeviceAdapter.DeviceVH>() {

    private val devices = ArrayList<DemoLeDevice>()

    fun addDevice(newDemoDevice: DemoLeDevice){

        var alreadyExists = false

        devices.forEachIndexed { index, demoDevice ->
            if(demoDevice.data.address == newDemoDevice.data.address){
                alreadyExists = true
                demoDevice.rssi = newDemoDevice.rssi
                notifyItemChanged(index)
            }
        }

        if(!alreadyExists){
            devices.add(newDemoDevice)
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
        holder.tvName.text = demoDevice.data.name ?: "No Name"
        holder.tvAddress.text = demoDevice.data.address
        holder.tvRssi.text = "${demoDevice.rssi}"

        holder.itemView.setOnClickListener {
            if(onDeviceClick!=null){
                onDeviceClick?.invoke(demoDevice)
            }
        }
    }

    class DeviceVH(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tvName = itemView.tvName
        val tvAddress = itemView.tvAddress
        val tvRssi = itemView.tvRssi
    }
}
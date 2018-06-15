package com.jmarkstar.demo.view.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmarkstar.demo.R
import kotlinx.android.synthetic.main.activity_le_device_detail_characteristic.view.*
import kotlinx.android.synthetic.main.activity_le_device_detail_service.view.*

class GattInfoAdapter(private val gattInfoList: ArrayList<GattItemType>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    
    override fun getItemViewType(position: Int): Int {
        return gattInfoList[position].getGattItemType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == GattItemType.GATT_SERVICE){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_le_device_detail_service, parent, false)
            GattServiceVH(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_le_device_detail_characteristic, parent, false)
            GattCharacteristicVH(view)
        }
    }

    override fun getItemCount(): Int {
        return gattInfoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val gattItem = gattInfoList[position]

        if(gattItem.getGattItemType() == GattItemType.GATT_SERVICE){
            val gattService = gattItem as GattService
            val serviceHolder = holder as GattServiceVH

            serviceHolder.tvUuid.text = gattService.uuid
            serviceHolder.tvServiceType.text = gattService.serviceType
        } else {
            val gattCharacteristic = gattItem as GattCharacteristic
            val gattCharacteristicHolder = holder as GattCharacteristicVH

            gattCharacteristicHolder.tvUuid.text = gattCharacteristic.uuid
            gattCharacteristicHolder.tvProperty.text = gattCharacteristic.propery
            gattCharacteristicHolder.tvPermission.text = gattCharacteristic.permission
        }
    }

    class GattServiceVH(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvUuid = itemView.tvServiceUuid
        val tvServiceType = itemView.tvServiceType
    }

    class GattCharacteristicVH(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvUuid = itemView.tvUuid
        val tvProperty = itemView.tvProperty
        val tvPermission = itemView.tvPermission
    }
}
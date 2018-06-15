package com.jmarkstar.demo.view.detail

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_le_device_detail_service.view.*

class GattInfoAdapter {

    class GattServiceVH(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvUuid = itemView.tvUuid
        val tvServiceType = itemView.tvServiceType
    }

    class GattCharacteristicVH(itemView: View): RecyclerView.ViewHolder(itemView){
        
    }
}
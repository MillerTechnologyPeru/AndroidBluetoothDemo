package com.jmarkstar.demo.le

class DemoLeUtils {
    companion object {
        internal fun showBondState(state: Int): String{
            return when(state){
                10 -> "NONE"
                11 -> "BONDING"
                12 -> "BONDED"
                else -> ""
            }
        }

        internal fun showDeviceType(type: Int): String{
            return when(type){
                0 -> "UNKNOWN"
                1 -> "CLASSIC"
                2 -> "LE"
                3 -> "DUAL"
                else -> ""
            }
        }

        internal fun showDeviceClass(type: Int): String{
            return when(type){
                1024 -> "AUDIO_VIDEO"
                256 -> "COMPUTER"
                2304 -> "HEALTH"
                1536 -> "IMAGING"
                0 -> "MISC"
                768 -> "NETWORKING"
                1280 -> "PERIPHERAL"
                512 -> "PHONE"
                2048 -> "TOY"
                7936 -> "UNCATEGORIZED"
                1792 -> "WEARABLE"
                else -> ""
            }
        }
    }
}
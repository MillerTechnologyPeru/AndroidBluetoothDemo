package com.jmarkstar.demo.le

class DemoLeUtils {
    companion object {
        internal fun showBondState(state: Int): String{
            return when(state){
                10 -> "NONE"
                11 -> "BONDING"
                12 -> "BONDED"
                else -> "UNKNOW($state)"
            }
        }

        internal fun showDeviceType(type: Int): String{
            return when(type){
                0 -> "UNKNOWN"
                1 -> "CLASSIC"
                2 -> "LE"
                3 -> "DUAL"
                else -> "UNKNOW($type)"
            }
        }

        internal fun showDeviceClass(deviceClass: Int): String{
            return when(deviceClass){
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
                else -> "UNKNOW($deviceClass)"
            }
        }

        internal fun showServiceType(type: Int): String{
            return when(type){
                0 -> "Primary"
                1 -> "Secondary"
                else -> "UNKNOW($type)"
            }
        }

        internal fun showCharacteristicPropery(property: Int): String{
            return when(property){
                1 -> "BROADCAST"
                128 -> "EXTENDED PROPS"
                32 -> "INDICATE"
                16 -> "NOTIFY"
                2 -> "READ"
                64 -> "SIGNED WRITE"
                8 -> "WRITE"
                4 -> "WRITE NO RESPONSE"
                else -> "UNKNOW($property)"
            }
        }

        internal fun showCharacteristicPermission(permission: Int): String{
            return when(permission){
                1 -> "READ"
                2 -> "READ ENCRYPTED"
                4 -> "READ ENCRYPTED MITM"
                16 -> "WRITE"
                32 -> "WRITE ENCRYPTED"
                64 -> "WRITE ENCRYPTED MITM"
                128 -> "WRITE SIGNED"
                256 -> "WRITE SIGNED MITM"
                else -> "UNKNOW($permission)"
            }
        }

        internal fun showGattStatus(status: Int): String{
            return when(status){
                143 -> "GATT_CONNECTION_CONGESTED"
                257 -> "GATT_FAILURE"
                5 -> "GATT_INSUFFICIENT_AUTHENTICATION"
                15 -> "GATT_INSUFFICIENT_ENCRYPTION"
                13 -> "GATT_INVALID_ATTRIBUTE_LENGTH"
                7 -> "GATT_INVALID_OFFSET"
                2 -> "GATT_READ_NOT_PERMITTED"
                6 -> "GATT_REQUEST_NOT_SUPPORTED"
                0 -> "GATT_SUCCESS"
                3 -> "GATT_WRITE_NOT_PERMITTED"
                else -> "UNKNOW($status)"
            }
        }
    }
}
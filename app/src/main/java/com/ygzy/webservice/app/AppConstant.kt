package com.ygzy.webservice.app

/**
 * @author : taomf
 * Date    : 2022/12/6/9:29
 * Desc    : 常量
 */
interface AppConstant {

    companion object {

        /** 页面退出时间 **/
        const val ACTIVITY_EXIT_TIME = 600L
        /** 页面loading时间 **/
        const val ACTIVITY_LOADING_TIME = 2000L



        /** 网络状态 **/
        const val NETWORK = "network"
        const val NETWORK_ON = "net_on"
        const val NETWORK_LOST = "net_lost"
        const val NETWORK_WIFI = "wifi"
        const val NETWORK_CELLULAR = "cellular" //蜂窝网络



    }
}
package com.ygzy.webservice.receiver

import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.ThreadUtils
import com.ygzy.webservice.app.AppConstant
import com.ygzy.lib_base.utils.LiveDataBus
import com.ygzy.lib_base.utils.MyLogUtils

/**
 * @author : taomf
 * Date   : 2021/4/13 9:38
 * Desc   : 网络监听
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class NetworkCallbackImpl : NetworkCallback() {
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        MyLogUtils.d(MyLogUtils.NET_TAG, "onAvailable: 网络已连接")
        ThreadUtils.runOnUiThread {
            LiveDataBus.get().with(AppConstant.NETWORK).value = AppConstant.NETWORK_ON
        }
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        MyLogUtils.e(MyLogUtils.NET_TAG, "onLost: 网络已断开")

        ThreadUtils.runOnUiThread {
            LiveDataBus.get().with(AppConstant.NETWORK).value = AppConstant.NETWORK_LOST
        }
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                }
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                }
                else -> {
                }
            }
        }
    }
}
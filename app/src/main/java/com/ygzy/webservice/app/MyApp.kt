package com.ygzy.webservice.app

import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.blankj.utilcode.util.NetworkUtils
import com.ygzy.webservice.receiver.NetworkCallbackImpl
import com.ygzy.lib_base.base.BaseApp
import com.ygzy.lib_base.utils.AppFrontBackHelper
import com.ygzy.lib_base.utils.MyLogUtils


class MyApp : BaseApp() {

    companion object {
        lateinit var appViewModel: AppViewModel
    }

    private val listener by lazy {
        object : NetworkUtils.OnNetworkStatusChangedListener {
            override fun onDisconnected() {
                appViewModel.netWorkStatus.value = AppConstant.NETWORK_LOST
            }

            override fun onConnected(networkType: NetworkUtils.NetworkType?) {
                when (networkType) {
                    NetworkUtils.NetworkType.NETWORK_2G,
                    NetworkUtils.NetworkType.NETWORK_3G,
                    NetworkUtils.NetworkType.NETWORK_4G,
                    NetworkUtils.NetworkType.NETWORK_5G -> {
                        appViewModel.netWorkStatus.value = AppConstant.NETWORK_CELLULAR
                    }
                    NetworkUtils.NetworkType.NETWORK_WIFI -> {
                        appViewModel.netWorkStatus.value = AppConstant.NETWORK_WIFI
                    }
                }
            }
        }
    }


    override fun onCreate() {
        super.onCreate()
        appViewModel = getAppViewModelProvider().get(AppViewModel::class.java)

        initSDK()
    }


    private fun initSDK() {

        val helper = AppFrontBackHelper()
        helper.register(this, object : AppFrontBackHelper.OnAppStatusListener {
            //应用切到前台处理
            override fun onFront() {
                MyLogUtils.d(MyLogUtils.TAG, "onFront: 在前台")
            }

            //应用切到后台处理
            override fun onBack() {
                MyLogUtils.d(MyLogUtils.TAG, "onBack: 在后台")
            }
        })

        //网络状态监听
        val networkCallback = NetworkCallbackImpl()
        val builder = NetworkRequest.Builder()
        val request = builder.build()
        val connMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connMgr.registerNetworkCallback(request, networkCallback)

        NetworkUtils.registerNetworkStatusChangedListener(listener)
    }
}
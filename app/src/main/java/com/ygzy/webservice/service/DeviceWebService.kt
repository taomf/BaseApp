package com.ygzy.webservice.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.yanzhenjie.andserver.AndServer
import com.yanzhenjie.andserver.Server
import java.util.concurrent.TimeUnit

/**
 * @author : taomf
 * Date    : 2023/10/7/14:17
 * Desc    : 设备的webService
 */
class DeviceWebService : Service() {
    private var server: Server? = null
    private val port = 8333
    private val timeOut = 10

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        init()
        return START_NOT_STICKY
    }

    private fun init() {
        server = AndServer.webServer(this).port(port).timeout(timeOut, TimeUnit.SECONDS).build()
        server?.startup()
    }

    override fun onDestroy() {
        super.onDestroy()
        server?.shutdown()
    }
}
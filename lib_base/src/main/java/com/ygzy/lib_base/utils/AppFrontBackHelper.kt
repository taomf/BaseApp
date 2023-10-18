package com.ygzy.lib_base.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * 应用前后台状态监听帮助类，仅在Application中使用
 *
 * @author taomf
 */
class AppFrontBackHelper {
    private var mOnAppStatusListener: OnAppStatusListener? = null

    /**
     * 注册状态监听，仅在Application中使用
     * @param application
     * @param listener
     */
    fun register(application: Application, listener: OnAppStatusListener?) {
        mOnAppStatusListener = listener
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    fun unRegister(application: Application) {
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    private val activityLifecycleCallbacks: Application.ActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        //打开的Activity数量统计
        private var activityStartCount = 0
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
        override fun onActivityStarted(activity: Activity) {
            activityStartCount++
            //数值从0变到1说明是从后台切到前台
            if (activityStartCount == 1) {
                //从后台切到前台
                if (mOnAppStatusListener != null) {
                    mOnAppStatusListener!!.onFront()
                }
            }
        }

        override fun onActivityResumed(activity: Activity) {}
        override fun onActivityPaused(activity: Activity) {}
        override fun onActivityStopped(activity: Activity) {
            activityStartCount--
            //数值从1到0说明是从前台切到后台
            if (activityStartCount == 0) {
                //从前台切到后台
                if (mOnAppStatusListener != null) {
                    mOnAppStatusListener!!.onBack()
                }
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {}
    }

    interface OnAppStatusListener {
        fun onFront()
        fun onBack()
    }
}
package com.ygzy.lib_base.http.coroutine.interceptor

import com.ygzy.lib_base.base.BaseApp
import com.ygzy.lib_base.utils.MyLogUtils
import okhttp3.logging.HttpLoggingInterceptor

/**
 * okhttp 日志拦截器
 * @author taomf  2022/3/21
 */
val logInterceptor = HttpLoggingInterceptor { message -> // 使用自己的日志工具接管
    MyLogUtils.d(MyLogUtils.HTTP_TAG,message)
}.setLevel(if (BaseApp.ISDEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC)
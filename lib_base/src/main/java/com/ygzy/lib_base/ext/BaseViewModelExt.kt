package com.ygzy.lib_base.ext

import androidx.lifecycle.viewModelScope
import com.ygzy.lib_base.R
import com.ygzy.lib_base.base.BaseVMViewModel
import com.ygzy.lib_base.data.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * BaseViewModel的一些扩展方法
 *
 * @author LTP  2022/3/22
 */

/**
 * 启动协程，封装了viewModelScope.launch
 *
 * @param tryBlock try语句运行的函数
 * @param catchBlock catch语句运行的函数，可以用来做一些网络异常等的处理，默认空实现
 * @param finallyBlock finally语句运行的函数，可以用来做一些资源回收等，默认空实现
 */
fun BaseVMViewModel.launch(
    tryBlock: suspend CoroutineScope.() -> Unit,
    catchBlock: suspend CoroutineScope.() -> Unit = {},
    finallyBlock: suspend CoroutineScope.() -> Unit = {}
) {
    // 默认是执行在主线程，相当于launch(Dispatchers.Main)
    viewModelScope.launch {
        try {
            tryBlock()
        } catch (e: Exception) {
            exception.value = e
            catchBlock()
        } finally {
            finallyBlock()
        }
    }
}


fun handleException(e: Exception){
    when (e) {
        is SocketTimeoutException -> showToast(R.string.request_time_out)
        is ConnectException, is UnknownHostException -> showToast(R.string.network_error)
        else -> showToast(R.string.response_error)
    }
}

/**
 * 请求结果处理
 *
 * @param response ApiResponse
 * @param successBlock 服务器请求成功返回成功码的执行回调，默认空实现
 * @param errorBlock 服务器请求成功返回错误码的执行回调，默认返回false的空实现，函数返回值true:拦截统一错误处理，false:不拦截
 */
suspend fun <T> BaseVMViewModel.handleRequest(
    response: ApiResponse<T>,
    successBlock: suspend CoroutineScope.(response: ApiResponse<T>) -> Unit = {},
    errorBlock: suspend CoroutineScope.(response: ApiResponse<T>) -> Boolean = { false }
) {
    coroutineScope {
        when (response.msgCode) {
            200 -> successBlock(response) // 服务器返回请求成功码
            else -> { // 服务器返回的其他错误码
                if (!errorBlock(response)) {
                    // 只有errorBlock返回false不拦截处理时，才去统一提醒错误提示
                    errorResponse.value = response
                }
            }
        }
    }
}
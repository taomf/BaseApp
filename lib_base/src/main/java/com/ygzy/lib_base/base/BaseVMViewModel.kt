package com.ygzy.lib_base.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ygzy.lib_base.data.ApiResponse

/**
 *    @author : taomf
 *    Date    : 2022/10/11/10:14:24
 *    Desc    : ViewModel 基类
 */
abstract class BaseVMViewModel : ViewModel() {
    /** 请求异常（服务器请求失败，譬如：服务器连接超时等） */
    val exception = MutableLiveData<Exception>()

    /** 请求服务器返回错误（服务器请求成功但status错误，譬如：登录过期等） */
    val errorResponse = MutableLiveData<ApiResponse<*>?>()

    /** 界面启动时要进行的初始化逻辑，如网络请求,数据初始化等 */
    open fun start(){}
}
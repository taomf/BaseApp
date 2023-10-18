package com.ygzy.lib_base.base

import androidx.lifecycle.ViewModel

/**
 *    @author : taomf
 *    Date    : 2022/10/11/10:14:24
 *    Desc    : ViewModel 基类
 */
abstract class BaseVMViewModel : ViewModel() {
    /** 界面启动时要进行的初始化逻辑，如网络请求,数据初始化等 */
    open fun start(){}
}
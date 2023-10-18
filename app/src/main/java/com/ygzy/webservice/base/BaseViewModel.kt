package com.ygzy.webservice.base

import androidx.lifecycle.MutableLiveData
import com.ygzy.lib_base.base.BaseVMViewModel

/**
 *    @author : taomf
 *    Date    : 2023/3/22/16:42
 *    Desc    : viewModel基类
 */
abstract class BaseViewModel : BaseVMViewModel() {

    val status by lazy { MutableLiveData<Boolean>() }
    val requestComplete by lazy { MutableLiveData<Boolean>() }



}
package com.ygzy.webservice.app

import androidx.lifecycle.MutableLiveData
import com.ygzy.webservice.base.BaseViewModel

/**
 * App全局ViewModel可直接替代EventBus
 * @author taomf
 */
class AppViewModel : BaseViewModel() {
    val netWorkStatus by lazy { MutableLiveData<String>() }

    override fun start() {}

}
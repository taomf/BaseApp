package com.ygzy.webservice.viewModel

import androidx.lifecycle.MutableLiveData
import com.ygzy.webservice.api.Api
import com.ygzy.lib_base.base.BaseObserver
import com.ygzy.webservice.base.BaseViewModel
import com.ygzy.lib_base.ext.*
import com.ygzy.lib_base.http.RxSchedulers
import com.ygzy.lib_base.http.ServiceGenerator
import io.reactivex.schedulers.Schedulers


/**
 *    @author : taomf
 *    Date    : 2022/10/10/16:45
 *    Desc    : 登录ViewModel
 */
class LoginViewModel : BaseViewModel() {
    //登录模式
    val loginModel by lazy { MutableLiveData(false) }
    val loginStatus by lazy { MutableLiveData(false) }


    val userName by lazy { MutableLiveData<String>().apply {
        this.value = USER_NAME.getSp()
    } }

    val userPwd by lazy { MutableLiveData<String>().apply {
        this.value = PASSWORD.getSp()
    } }
    val userPhone by lazy { MutableLiveData<String>().apply {
        this.value = USER_PHONE.getSp()
    } }
    val yzm by lazy { MutableLiveData<String>() }


    fun login(name :String, paw :String,yzm :String,phone :String){

        val dataMap = when(loginModel.value){
            true -> HashMap<String,String>().apply {
                this["client_id"] = "ygzy"
                this["client_secret"] = "123456"
                this["grant_type"] = "password"
                this["scope"] = "server"
                this["username"] = name
                this["password"] = paw
            }
            else -> HashMap<String,String>().apply {
                this["client_id"] = "ygzy"
                this["client_secret"] = "123456"
                this["grant_type"] = "app"
                this["mobile"] = phone
                this["scope"] = "server"
                this["code"] = yzm
                this["type"] = "1"
            }
        }

        ServiceGenerator.getService(Api::class.java).login(dataMap).compose(RxSchedulers.compose()).subscribeOn(Schedulers.io()).subscribe(object : BaseObserver<Any>(){
            override fun onSuccess(result : Any?) {


            }
        })
    }
}
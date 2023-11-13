package com.ygzy.webservice.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ygzy.lib_base.ext.PASSWORD
import com.ygzy.lib_base.ext.USER_NAME
import com.ygzy.lib_base.ext.USER_PHONE
import com.ygzy.lib_base.ext.getSp
import com.ygzy.lib_base.utils.MyLogUtils
import com.ygzy.webservice.base.BaseViewModel
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


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


    }


    fun test(){
        MyLogUtils.d(MyLogUtils.TAG, "111")


        viewModelScope.launch {
            MyLogUtils.d(MyLogUtils.TAG, "222")

            delay(2000)



            launch {
                MyLogUtils.d(MyLogUtils.TAG, "my job:")
                delay(1000)
                MyLogUtils.d(MyLogUtils.TAG, "my job: 1")
            }

            MyLogUtils.d(MyLogUtils.TAG, "person")

            coroutineScope {
                MyLogUtils.d(MyLogUtils.TAG, "taomf1")


                launch {
                    MyLogUtils.d(MyLogUtils.TAG, "taomf2")


                    delay(1000)

                    MyLogUtils.d(MyLogUtils.TAG, "my job: 2")

                }
                delay(2000)
                MyLogUtils.d(MyLogUtils.TAG, "hello word")

                test2()

            }

            coroutineScope{
                MyLogUtils.d(MyLogUtils.TAG, "另一个协程")

            }


            MyLogUtils.d(MyLogUtils.TAG, "welcome")
        }

        viewModelScope.launch {
            MyLogUtils.d(MyLogUtils.TAG, "另一个协程2")

        }

        MyLogUtils.d(MyLogUtils.TAG, "我要出来")

    }


    private suspend fun test2(){
        withContext(Dispatchers.IO){
            MyLogUtils.d(MyLogUtils.TAG, "协程1中的方法")
        }
    }

    private suspend fun funtion(){

      val time =   measureTimeMillis {
            val fun2 = fun2()
            val fun3 = fun3()

            MyLogUtils.d(MyLogUtils.TAG, fun2 + fun3 )
        }

        MyLogUtils.d(MyLogUtils.TAG, time )

    }

    private suspend fun fun2() : Int{
        delay(2000L)
        return 1
    }

    private suspend fun fun3(): Int{
        delay(2000L)
        return 2
    }

     fun test3(){
        val job = viewModelScope.launch {
            launch {
                MyLogUtils.d(MyLogUtils.TAG, "job 1 start" )
                delay(1000)
                MyLogUtils.d(MyLogUtils.TAG, "job 1 end" )
            }

            launch {
                MyLogUtils.d(MyLogUtils.TAG, "job 2 start" )
                delay(1000)
                MyLogUtils.d(MyLogUtils.TAG, "job 2 end" )

            }
        }

        job.cancel()
    }
}
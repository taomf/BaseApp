package com.ygzy.lib_base.base

import com.alibaba.fastjson.JSONObject
import com.ygzy.lib_base.ext.showToast
import com.ygzy.lib_base.http.BaseModel
import com.ygzy.lib_base.utils.MyLogUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author taomf
 * 备注：网络请求接收基类
 */
abstract class BaseObserver<T>(private var errMsg :String = "")  : Observer<BaseModel<T>> {
    private val success = 200
    private val tokenFailure = 40007
    private val failCode = 20000

    override fun onSubscribe(d: Disposable) {

    }
    override fun onNext(value: BaseModel<T>) {
        when (value.msgCode) {
            success ->  onSuccess(value.result)
            tokenFailure -> {
                showToast("登录失效，请重新登录")
            }
            failCode ->{
                if(value.msgInfo == "令牌过期"){
                }else {
                    showToast(value.msgInfo)
                }
            }
            else -> onFail(value)
        }
    }

    /**
     * 网络请求完成
     */
    override fun onComplete() {

    }

    /**
     * 网络请求错误
     * @param e 错误数据
     */
    override fun onError(e: Throwable) {
        onFailCode(666)

        when (e) {
            is HttpException -> {
                //Response{protocol=http/1.1, code=401, message=, url=http://192.168.10.61:8086/login}
                val response = e.response().toString()
                //{"code":401,"message":"用户名或密码错误"}
                var errorBody = "null"
                var parseObject :JSONObject? = null
                try {
                    errorBody = e.response()!!.errorBody()!!.string()

                     parseObject = JSONObject.parseObject(errorBody)


                } catch (exception: Exception) {

                }


                if (parseObject!= null && parseObject.containsKey("msgCode") && parseObject.containsKey("msgInfo")){
                    if (parseObject["msgCode"] == 20000 && parseObject["msgInfo"] == "令牌过期"){

                        showToast("登录过期，请重新登录!")
                    }
                }else {
                    showToast(errorBody)
                }
                MyLogUtils.e(MyLogUtils.HTTP_TAG, response)
                MyLogUtils.e(MyLogUtils.HTTP_TAG, errorBody)
            }
            is ConnectException, is UnknownHostException -> showToast("网络连接失败，请稍后重试")

            is SocketTimeoutException -> showToast("网络请求超时，请稍后重试")

            else -> showToast(e.message ?: "网络请求错误，请稍后重试")
        }
    }

    /**
     * 请求成功
     * @param result 返回的数据
     */
    protected abstract fun onSuccess(result: T?)

    /**
     * 请求失败
     * @param failCode 返回的失败code
     */
    open fun onFailCode(failCode: Int) {}

    /**
     * 请求失败
     * @param model 数据
     */
    private fun onFail(model: BaseModel<*>) {

        onFailCode(model.msgCode)
        MyLogUtils.e(MyLogUtils.HTTP_TAG, "onException:$model")
        showToast(errMsg.ifBlank { model.msgInfo })
    }


}
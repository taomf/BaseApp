package com.ygzy.webservice.api

import com.ygzy.lib_base.http.BaseModel
import com.ygzy.webservice.app.MyHttpUrl
import io.reactivex.Observable
import retrofit2.http.*


/**
 *    @author : taomf
 *    Date    : 2022/5/30/10:03
 *    Desc    : 登录相关
 */
interface Api {
    /**
     * @param fields 请求数据
     * @return 登录
     */
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(MyHttpUrl.LOGIN)
    fun login(@FieldMap fields :Map<String,String>): Observable<BaseModel<Any>>

    /** 登录 */
    @GET("getPlatformAddress")
    fun getPlatformAddress(): Observable<BaseModel<String>>
}
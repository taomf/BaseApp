package com.ygzy.webservice.data

import com.ygzy.webservice.data.http.Api
import com.ygzy.lib_base.data.ApiResponse
import com.ygzy.lib_base.http.coroutine.BaseRepository
import com.ygzy.lib_base.http.coroutine.RetrofitManager

/**
 * 数据仓库
 *
 * @author LTP  2022/3/23
 */
object DataRepository : BaseRepository(), Api {

    private val service by lazy { RetrofitManager.getService(Api::class.java) }

    override suspend fun login(username: String, pwd: String): ApiResponse<Any> {

        return apiCall { service.login(username, pwd) }
    }
}
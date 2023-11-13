package com.ygzy.lib_base.http.coroutine

import com.ygzy.lib_base.data.ApiResponse
import com.ygzy.lib_base.http.BaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository数据仓库基类，主要用于协程的调用
 *
 * @author taomf  2022/3/23
 */
open class BaseRepository {

    suspend fun <T> apiCall(api: suspend () -> ApiResponse<T>): ApiResponse<T> {
        return withContext(Dispatchers.IO) { api.invoke() }
    }
}
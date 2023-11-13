package com.ygzy.lib_base.data

/**
 * 接口返回外层封装实体
 */
data class ApiResponse<T>(
    val data: T,
    val msgCode: Int,
    val msgInfo: String
)
package com.ygzy.lib_base.http

/**
 * 备注：HTTP请求返回Model
 * @author taomf
 * @param <T> 返回的数据
*/
open class BaseModel<T> {
    /**0成功   1失败   -1异常 */
    var msgCode = 0
    var msgInfo: String? = null
    var result: T? = null

    override fun toString(): String {
        return "BaseModel{" +
                "code=" + msgCode +
                ", msg='" + msgInfo + '\'' +
                ", data=" + result +
                '}'
    }
}
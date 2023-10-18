package com.ygzy.lib_base.ext

import com.tencent.mmkv.MMKV

/**
 *    @author : 全局sp方法
 *    Date    : 2022/6/6/10:05
 *    Desc    : 数据保存
 */
private val mmkv: MMKV by lazy { MMKV.defaultMMKV() }

const val TOKEN = "token"
const val TOKEN_TYPE = "token_type"
const val LOGIN_STATUS = "login_status"
const val USER_NAME = "username"
const val USER_PHONE = "userPhone"
const val PASSWORD = "password"
const val ORGNAME = "orgName"
const val ORGID = "orgId"
const val USERREALNAME = "userRealName"



fun String.saveSp(value:String?){
    mmkv.encode(this, value)
}

fun String.getSp() :String{
    return mmkv.decodeString(this, "") ?: ""
}

fun String.removeValueForKey(){
    return mmkv.removeValueForKey(this)
}


fun saveLoginStatus(loginStatus: Boolean = true){
    mmkv.encode(LOGIN_STATUS, loginStatus)
}

fun getLoginStatus() : Boolean{
    return mmkv.decodeBool(LOGIN_STATUS,false)
}





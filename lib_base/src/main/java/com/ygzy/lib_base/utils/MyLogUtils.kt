package com.ygzy.lib_base.utils

import android.text.TextUtils
import android.util.Log
import com.ygzy.lib_base.base.BaseApp
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * 说明：日志工具类，【DEBUG为true输出日志，为false不输出日志】
 */
object MyLogUtils {
    private var IS_SHOW_LOG = BaseApp.ISDEBUG
    const val TAG = "taomf"
    const val PARAMS = "PARAMS"
    const val NET_TAG = "NET_TAG"
    const val HTTP_TAG = "HTTP_TAG"
    const val SCAN_DEVICE = "SCAN_DEVICE"
    const val CALL_TAG = "CALL_TAG"
    const val MQTT_TAG = "MQTT_TAG"
    const val SOCKET_TAG = "SOCKET_TAG"
    const val PHONE_INFO_TAG = "PHONE_INFO_TAG"
    const val ERROR_INFO = "ERROR_INFO"
    private const val DEFAULT_MESSAGE = "execute"
    private val LINE_SEPARATOR = System.getProperty("line.separator")
    private const val JSON_INDENT = 4
    private const val V = 0x1
    private const val D = 0x2
    private const val I = 0x3
    private const val W = 0x4
    private const val E = 0x5
    private const val A = 0x6
    private const val JSON = 0x7
    fun init(isShowLog: Boolean) {
        IS_SHOW_LOG = isShowLog
    }

    fun v() {
        printLog(V, null, DEFAULT_MESSAGE)
    }

    fun v(msg: Any?) {
        printLog(V, null, msg)
    }

    fun v(tag: String?, msg: String?) {
        printLog(V, tag, msg)
    }

    fun d() {
        printLog(D, null, DEFAULT_MESSAGE)
    }

    fun d(msg: Any?) {
        printLog(D, null, msg)
    }

    fun d(tag: String?, msg: Any?) {
        printLog(D, tag, msg)
    }

    fun i() {
        printLog(I, null, DEFAULT_MESSAGE)
    }

    fun i(msg: Any?) {
        printLog(I, null, msg)
    }

    fun i(tag: String?, msg: Any?) {
        printLog(I, tag, msg)
    }

    fun w() {
        printLog(W, null, DEFAULT_MESSAGE)
    }

    fun w(msg: Any?) {
        printLog(W, null, msg)
    }

    fun w(tag: String?, msg: Any?) {
        printLog(W, tag, msg)
    }

    fun e() {
        printLog(E, null, DEFAULT_MESSAGE)
    }

    fun e(msg: Any?) {
        printLog(E, null, msg)
    }

    fun e(tag: String?, msg: Any?) {
        printLog(E, tag, msg)
    }

    fun a() {
        printLog(A, null, DEFAULT_MESSAGE)
    }

    fun a(msg: Any?) {
        printLog(A, null, msg)
    }

    fun a(tag: String?, msg: Any?) {
        printLog(A, tag, msg)
    }

    fun json(jsonFormat: String?) {
        printLog(JSON, null, jsonFormat)
    }

    fun json(tag: String?, jsonFormat: String?) {
        printLog(JSON, tag, jsonFormat)
    }

    private fun printLog(type: Int, tagStr: String?, objectMsg: Any?) {
        if (!IS_SHOW_LOG) {
            return
        }
        val stackTrace = Thread.currentThread().stackTrace
        val index = 4
        val className = stackTrace[index].fileName
        var methodName = stackTrace[index].methodName
        val lineNumber = stackTrace[index].lineNumber
        val tag = tagStr ?: className
        methodName = methodName.substring(0, 1).uppercase(Locale.ROOT) + methodName.substring(1)
        val stringBuilder = StringBuilder()
        stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(" ] ")
        val msg: String = objectMsg?.toString() ?: "Log with null Object"
        if (type != JSON) {
            stringBuilder.append(msg)
        }
        val logStr = stringBuilder.toString()
        when (type) {
            V -> printString(logStr){ Log.v(tag, it) }
            D -> printString(logStr){ Log.d(tag, it) }
            I -> printString(logStr){ Log.i(tag, it) }
            W -> printString(logStr){ Log.w(tag, it) }
            E -> printString(logStr){ Log.e(tag, it) }
            A -> printString(logStr){ Log.wtf(tag, it) }
            JSON -> {
                if (TextUtils.isEmpty(msg)) {
                    Log.d(tag, "Empty or Null json content")
                    return
                }
                var message: String? = null
                try {
                    if (msg.startsWith("{")) {
                        val jsonObject = JSONObject(msg)
                        message = jsonObject.toString(JSON_INDENT)
                    } else if (msg.startsWith("[")) {
                        val jsonArray = JSONArray(msg)
                        message = jsonArray.toString(JSON_INDENT)
                    }
                } catch (e: JSONException) {
                    e(
                        tag, """
     ${e.cause!!.message}
     $msg
     """.trimIndent()
                    )
                    return
                }
                printLine(tag, true)
                message = logStr + LINE_SEPARATOR + message
                val lines = message.split(LINE_SEPARATOR as String).toTypedArray()
                val jsonContent = StringBuilder()
                for (line in lines) {
//                    jsonContent.append("║ ").append(line).append(LINE_SEPARATOR)
                    jsonContent.append(line).append(LINE_SEPARATOR)
                }
                Log.d(tag, jsonContent.toString())
                printLine(tag, false)
            }
            else -> {}
        }
    }

    private fun printString(data:String,logPrint :(String)->Unit){
        if (data.length > 3600){
            data.chunked(3600).forEach {
                logPrint(it)
            }
        }else {
            logPrint(data)
        }
    }


    private fun printLine(tag: String, isTop: Boolean) {
//        if (isTop) {
//            Log.d(tag, "╔════════════════════════════════════")
//        } else {
//            Log.d(tag, "╚════════════════════════════════════")
//        }
    }
}
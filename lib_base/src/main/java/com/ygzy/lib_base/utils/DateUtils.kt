package com.ygzy.lib_base.utils

import android.os.SystemClock
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 说明：日期工具类
 */
object DateUtils {
    /**
     * 格式：【yyyy】
     */
    const val FORMAT_YYYY_1 = "yyyy"

    /**
     * 格式：【yyyy年】
     */
    const val FORMAT_YYYY_2 = "yyyy年"

    /**
     * 格式：【MM】
     */
    const val FORMAT_MM_1 = "MM"

    /**
     * 格式：【MM月】
     */
    const val FORMAT_MM_2 = "MM月"

    /**
     * 格式：【dd】
     */
    const val FORMAT_DD_1 = "dd"

    /**
     * 格式：【dd日】
     */
    const val FORMAT_DD_2 = "dd日"

    /**
     * 格式：【HH】
     */
    const val FORMAT_HH_1 = "HH"

    /**
     * 格式：【HH时】
     */
    const val FORMAT_HH_2 = "HH时"

    /**
     * 格式：【mm】
     */
    const val FORMAT_MM_3 = "mm"

    /**
     * 格式：【mm分】
     */
    const val FORMAT_MM_4 = "mm分"

    /**
     * 格式：【ss】
     */
    const val FORMAT_SS_1 = "ss"

    /**
     * 格式：【ss秒】
     */
    const val FORMAT_SS_2 = "ss秒"

    /**
     * 格式：【HH:mm】
     */
    const val FORMAT_HH_MM_1 = "HH:mm"

    /**
     * 格式：【HH时mm分】
     */
    const val FORMAT_HH_MM_2 = "HH时mm分"

    /**
     * 格式：【HH:mm:ss】
     */
    const val FORMAT_HH_MM_SS_1 = "HH:mm:ss"

    /**
     * 格式：【HH时mm分ss秒】
     */
    const val FORMAT_HH_MM_SS_2 = "HH时mm分ss秒"

    /**
     * 格式：【yyyy-MM-dd】
     */
    const val FORMAT_YYYY_MM_DD_1 = "yyyy-MM-dd"

    /**
     * 格式：【yyyy/MM/dd】
     */
    const val FORMAT_YYYY_MM_DD_2 = "yyyy/MM/dd"

    /**
     * 格式：【yyyy年MM月dd日】
     */
    const val FORMAT_YYYY_MM_DD_3 = "yyyy年MM月dd日"

    /**
     * 格式：【yyyy-MM-dd HH:mm:ss】
     */
    const val FORMAT_YYYY_MM_DD_HH_MM_SS_1 = "yyyy-MM-dd HH:mm:ss"

    /**
     * 格式：【yyyy/MM/dd HH:mm:ss】
     */
    const val FORMAT_YYYY_MM_DD_HH_MM_SS_2 = "yyyy/MM/dd HH:mm:ss"

    /**
     * 格式：【yyyy年MM月dd日 HH时mm分ss秒】
     */
    const val FORMAT_YYYY_MM_DD_HH_MM_SS_3 = "yyyy年MM月dd日 HH时mm分ss秒"

    /**
     * 格式：【yyyyMMddHHmmss】
     */
    const val FORMAT_YYYY_MM_DD_HH_MM_SS_4 = "yyyyMMddHHmmss"

    /**
     * 格式：【yyyyMMddHHmmss】
     */
    val WEEKDAYS = arrayOf("", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
    private fun getSDF(format: String?): SimpleDateFormat {
        return SimpleDateFormat(format, Locale.getDefault())
    }

    private val calendar: Calendar
        get() = Calendar.getInstance()

    /**
     * 说明：当前时间转数字
     *
     * @param format
     * 时间格式
     * @return
     */
    fun getNowTimeNum(format: String?): Long {
        try {
            return getNowTime(format).toLong()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return -1
    }

    /**
     * 说明：获取Date对象
     * @return Date
     */
    val nowTimeDate: Date
        get() = Date()

    /**
     * 说明：获取当前时间（格式毫秒）
     * @return
     */
    val millisecond: Long
        get() = nowTimeDate.time

    /**
     * 说明：现在时间格式化后的形式
     *
     * @param format
     * 时间格式
     * @return 格式化后的时间
     */
    fun getNowTime(format: String?): String {
        val sdf = getSDF(format)
        return sdf.format(Date())
    }

    /**
     * 说明：获取Unix时间戳（10位）1970年1月1日-当前时间 经过的秒数
     * @return
     */
    val unixTime: Long
        get() = SystemClock.uptimeMillis() / 1000

    /**
     * 说明：字符串转Date
     *
     * @param oldDate
     * 要转的时间
     * @param format
     * 时间格式
     * @return
     */
    fun getStrToDate(oldDate: String?, format: String?): Date? {
        val sdf = getSDF(format)
        var date: Date? = null
        try {
            date = sdf.parse(oldDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    /**
     * 说明：一种格式时间转另一种格式时间
     *
     * @param oldDate
     * 要转的时间
     * @param oldFormat
     * 旧时间格式
     * @param newFormat
     * 新时间格式
     * @return
     */
    fun getStrToStr(
        oldDate: String?, oldFormat: String?,
        newFormat: String?
    ): String {
        val date = getStrToDate(oldDate, oldFormat)
        val sdf = getSDF(newFormat)
        return sdf.format(date)
    }

    /**
     * 说明：将字符串时间转Long类型
     *
     * @param date
     * 要转的时间
     * @param format
     * 时间格式
     * @return
     */
    fun getStrToLong(date: String?, format: String?): Long {
        return getStrToDate(date, format)!!.time
    }

    /**
     * 说明：获取当前时间，默认时间格式【yyyy-MM-dd HH:mm:ss】
     *
     * @return
     */
    val nowTime: String
        get() = getNowTime(FORMAT_YYYY_MM_DD_HH_MM_SS_1)

    /**
     * 说明：获取当前时间，默认时间格式【yyyyMMddHHmmss】
     *
     * @return
     */
    val nowTimeSend: String
        get() = getNowTime(FORMAT_YYYY_MM_DD_HH_MM_SS_4)

    /**
     * 说明：获取星期【星期一】
     * @return
     */
    val weekDay: Int
        get() {
            val calendar = calendar
            calendar.time = nowTimeDate
            return calendar[Calendar.DAY_OF_WEEK]
        }
    val weekDayS: String
        get() {
            val weekDay = weekDay
            var s = "星期"
            when (weekDay) {
                1 -> s += "日"
                2 -> s += "一"
                3 -> s += "二"
                4 -> s += "三"
                5 -> s += "四"
                6 -> s += "五"
                7 -> s += "六"
            }
            return s
        }

    /**
     * 说明：获取年份
     *
     * @return 例如【2015】
     */
    val year: String
        get() = getNowTime(FORMAT_YYYY_1)

    /**
     * 说明：获取月份
     *
     * @return 例如【02】
     */
    val month: String
        get() = getNowTime(FORMAT_MM_1)

    /**
     * 说明：获取第几日
     *
     * @return 例如【11】
     */
    val day: String
        get() = getNowTime(FORMAT_DD_1)

    /**
     * 说明：获取两个时间间隔(单位：天)
     *
     * @param firstDate
     * 第一个时间
     * @param secondDate
     * 第一个时间
     * @param format
     * 对应时间格式
     * @return 返回时间间隔的天数
     */
    fun getDaySpace(
        firstDate: String?, secondDate: String?,
        format: String?
    ): Long {
        return getSecondSpace(firstDate, secondDate, format) / (24 * 60 * 60)
    }

    /**
     * 说明：获取两个时间间隔(单位：小时)
     *
     * @param firstDate
     * 第一个时间
     * @param secondDate
     * 第一个时间
     * @param format
     * 对应时间格式
     * @return 返回时间间隔的小时数
     */
    fun getHourSpace(
        firstDate: String?, secondDate: String?,
        format: String?
    ): Long {
        return getSecondSpace(firstDate, secondDate, format) / (60 * 60)
    }

    /**
     * 说明：获取两个时间间隔（单位：秒）
     *
     * @param firstDate
     * 第一个时间
     * @param secondDate
     * 第一个时间
     * @param format
     * 对应时间格式
     * @return 返回时间间隔的秒数
     */
    fun getSecondSpace(
        firstDate: String?,
        secondDate: String?, format: String?
    ): Long {
        val sdf = getSDF(format)
        var day: Long = 0
        try {
            val date1 = sdf.parse(firstDate)
            val date2 = sdf.parse(secondDate)
            day = (date2.time - date1.time) / 1000
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return day
    }

    /**
     * 说明：判断年份是否为闰年
     *
     * @param year
     * @return
     */
    fun isLeapYear(year: Int): Boolean {
        var isLeapYear = false
        isLeapYear = if (year <= 0) {
            false
        } else {
            (year % 100 != 0 && year % 4 == 0
                    || year % 100 == 0 && year % 400 == 0)
        }
        return isLeapYear
    }

    /**
     * 说明：将long类型转为时间字符串
     *
     * @param l
     * 类型时间
     * @param format
     * 时间格式
     * @return
     */
    fun getLongToStr(l: Long, format: String?): String {
        val date = Date(l)
        val sdf = getSDF(format)
        return sdf.format(date)
    }

    fun currentTimeMillis(): String {
        return System.currentTimeMillis().toString() + ""
    }

    /**
     * 说明：根据不同时区，转换时间 2015年7月31日
     */
    fun transformTime(
        date: Date?, oldZone: TimeZone,
        newZone: TimeZone
    ): Date? {
        var finalDate: Date? = null
        if (date != null) {
            val timeOffset = (oldZone.getOffset(date.time)
                    - newZone.getOffset(date.time))
            finalDate = Date(date.time - timeOffset)
        }
        return finalDate
    }

    /**
     * 说明：判断用户的设备时区是否为东八区（中国） 2015年7月31日
     * @return
     */
    val isInEasternEightZones: Boolean
        get() {
            var defaultVaule = true
            defaultVaule = if (TimeZone.getDefault() === TimeZone.getTimeZone("GMT+08")) {
                true
            } else {
                false
            }
            return defaultVaule
        }

    /**
     * 说明： 根据日历的规则，为给定的日历字段添加或减去指定的时间量
     * @param date 指定时间
     * @param field 日历字段（年或月或日）
     * @param amount 时间量
     * @param format 返回时间格式
     * @return 返回指定的时间格式的字符串
     */
    fun add(date: Date?, field: Int, amount: Int, format: String?): String {
        val calendar = calendar
        val sdf = getSDF(format)
        calendar.time = date
        calendar.add(field, amount)
        return sdf.format(calendar.time)
    }

    /**
     * 说明：判断src时间是否在start-end区间
     * @param src  指定时间
     * @param start  前范围
     * @param end  后范围
     * @param format  时间格式要统一
     * @return【true：在区间，false：不在区间】
     */
    fun between(src: String?, start: String?, end: String?, format: String?): Boolean {
        val srcCal = calendar
        val startCal = calendar
        val endCal = calendar
        srcCal.time = getStrToDate(src, format)
        startCal.time = getStrToDate(start, format)
        endCal.time = getStrToDate(end, format)
        return if (srcCal.after(startCal) && srcCal.before(endCal)) {
            true
        } else {
            false
        }
    }

    /**
     * 说明：d1与d2比较
     * @param d1 时间1
     * @param d2 时间2
     * @param format 时间格式
     * @return 【当d1小于d2 返回小于0，当d2大于d1 返回大于0，当d1等于d2 返回0】
     */
    fun compareTo(d1: String?, d2: String?, format: String?): Int {
        val d1Cal = calendar
        val d2Cal = calendar
        d1Cal.time = getStrToDate(d1, format)
        d2Cal.time = getStrToDate(d2, format)
        return d1Cal.compareTo(d2Cal)
    }
}
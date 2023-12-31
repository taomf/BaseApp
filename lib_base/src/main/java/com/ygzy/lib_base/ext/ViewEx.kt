@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package com.ygzy.lib_base.ext

import android.view.View
import android.view.ViewGroup

/**
 * description: View 操作相关扩展
 *
 * @author Db_z
 * @Date 2021/09/26 17:28
 */

/**
 * 设置view显示
 */
@kotlin.internal.InlineOnly
inline fun View?.visible() {
    this ?: return
    visibility = View.VISIBLE
}

/**
 * 设置view占位隐藏
 */
@kotlin.internal.InlineOnly
inline fun View?.invisible() {
    this ?: return
    visibility = View.INVISIBLE
}

/**
 * 设置view消失
 */
@kotlin.internal.InlineOnly
inline fun View?.gone() {
    this ?: return
    visibility = View.GONE
}

/**
 * 根据条件设置view显示隐藏
 * @param flag 为true 显示，为false 隐藏
 */
@kotlin.internal.InlineOnly
inline fun View?.visibleOrGone(flag: Boolean) {
    this ?: return
    visibility = if (flag) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

/**
 * 根据条件设置view显示隐藏
 * @param flag 为true 显示，为false 隐藏
 */
@kotlin.internal.InlineOnly
inline fun View?.visibleOrInvisible(flag: Boolean) {
    this ?: return
    visibility = if (flag) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

var lastClickTime = 0L
/**
 * 防抖处理，防止重复点击事件，默认0.5秒内不可重复点击
 * @param interval 时间间隔，默认0.5秒
 * @param action 执行方法
 */
@kotlin.internal.InlineOnly
inline fun View.onClick(interval: Long = 500, crossinline action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        val flag: Boolean = !(lastClickTime != 0L && (currentTime - lastClickTime < interval))
        lastClickTime = currentTime
        if (flag) action(it)
    }
}

/**
 * 防连点
 */
var lastClick= 0L
fun <T> T.singleClick(interval: Long = 500)  : T?{
    val currentTime = System.currentTimeMillis()
    val flag: Boolean = !(lastClick != 0L && (currentTime - lastClick < interval))
    lastClick = currentTime
    if (flag) {
        return this
    }
    return null
}



/**
 * View长按点击事件
 */
@kotlin.internal.InlineOnly
inline fun View.onLongClick(crossinline block: (View) -> Unit) {
    setOnLongClickListener {
        block(it)
        true
    }
}

/**
 * 更新View
 */
@kotlin.internal.InlineOnly
inline fun View.resize(block: ViewGroup.LayoutParams.() -> Boolean) {
    if (layoutParams.block()) requestLayout()
}

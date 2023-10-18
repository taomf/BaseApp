package com.ygzy.lib_base.ext

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import androidx.annotation.DrawableRes
import com.blankj.utilcode.util.ResourceUtils
import com.hjq.toast.Toaster

/**
 *    @author : taomf
 *    Date    : 2022/5/30/10:16
 *    Desc    : 全局方法
 */


fun showToast(string: String?) {
    if (!string.isNullOrBlank()) {
        Toaster.setGravity(Gravity.CENTER)
        Toaster.showShort(string)
    }
}
fun showLongToast(string: String?) {
    if (!string.isNullOrBlank()) {
        Toaster.setGravity(Gravity.CENTER)
        Toaster.showShort(string)
    }
}

fun getDrawableKt(@DrawableRes id :Int ) : Drawable {
   return ResourceUtils.getDrawable(id)
}


/**
 * 改变当前window的透明度
 * @param alpha
 * @param context
 */
fun changeWindowAlpha(alpha: Float, context: Context) {
    val params = (context as Activity).window.attributes
    params.alpha = alpha
    context.window.attributes = params
    // 此方法用来设置浮动层，防止部分手机变暗无效
    //((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
}

/**
 * 防连点
 */
var last= 0L
fun single(interval: Long = 500) : Boolean{
    val currentTime = System.currentTimeMillis()
    val flag: Boolean = !(last != 0L && (currentTime - last < interval))
    last = currentTime
    return flag
}






package com.ygzy.webservice.base

import android.app.Activity
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.ActivityUtils
import com.ygzy.lib_base.base.BaseVMFragment

/**
 * 继承自BaseVMFragment
 * 在这里只做了统一处理跳转登录页面的逻辑
 * @author taomf
 */
abstract class BaseFragment<B : ViewDataBinding>(contentViewResId: Int) :
    BaseVMFragment<B>(contentViewResId) {

    open fun startActivity(clz: Class<out Activity?>) {
        ActivityUtils.startActivity(clz)
    }


}
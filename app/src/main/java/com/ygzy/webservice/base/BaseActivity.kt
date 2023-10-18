package com.ygzy.webservice.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.NetworkUtils
import com.ygzy.webservice.R
import com.ygzy.webservice.app.AppConstant
import com.ygzy.webservice.app.MyApp
import com.ygzy.lib_base.base.BaseDialog
import com.ygzy.lib_base.base.BaseVMActivity
import com.ygzy.lib_base.dialog.WaitDialog
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.math.pow

/**
 *    @author : taomf
 *    Date    : 2022/10/11/15:13
 *    Desc    : activity基类
 */
abstract class BaseActivity<VM : BaseViewModel, B : ViewDataBinding>(contentViewResId: Int) :
    BaseVMActivity<VM, B>(contentViewResId) {

    private val waitDialog: BaseDialog by lazy { WaitDialog.Builder(this).setMessage(setDiaLogMessage()).create() }


    /** Activity 回调集合 */
    private val activityCallbacks: SparseArray<OnActivityCallback?> by lazy { SparseArray(1) }
    private val noNetWork: View by lazy { LayoutInflater.from(mBinding.root.context).inflate(R.layout.item_no_network, null) }

    override fun initOnCreate(savedInstanceState: Bundle?) {
        super.initOnCreate(savedInstanceState)

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        noNetWork.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        if(mBinding.root is ViewGroup){
            (mBinding.root as ViewGroup).addView(noNetWork)
        }

        MyApp.appViewModel.netWorkStatus.observe(this){
            when(it){
                AppConstant.NETWORK_LOST ->{
                    noNetWork.visibility = View.VISIBLE
                }
                AppConstant.NETWORK_WIFI ->{
                    noNetWork.visibility = View.INVISIBLE
                }
                AppConstant.NETWORK_CELLULAR ->{
                    noNetWork.visibility = View.INVISIBLE
                }
            }
        }

        mViewModel.requestComplete.observe(this){
            waitDialog.apply {
                if (isShowing){
                    Handler(Looper.getMainLooper()).postDelayed({
                        dismiss()
                    },1000)
                }
            }
        }

        if (NetworkUtils.isConnected()) noNetWork.visibility = View.INVISIBLE else noNetWork.visibility = View.VISIBLE

    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }

    open fun setDiaLogMessage():String = getString(R.string.common_loading)

    open fun startActivity(clz: Class<out Activity?>) {
        ActivityUtils.startActivity(clz)
    }

    /**
     * startActivityForResult 方法优化
     */
    open fun startActivityForResult(clazz: Class<out Activity>, callback: OnActivityCallback?) {
        startActivityForResult(Intent(this, clazz), null, callback)
    }

    open fun startActivityForResult(intent: Intent, callback: OnActivityCallback?) {
        startActivityForResult(intent, null, callback)
    }

    @Suppress("deprecation")
    open fun startActivityForResult(intent: Intent, options: Bundle?, callback: OnActivityCallback?) {
        // 请求码必须在 2 的 16 次方以内
        val requestCode: Int = Random().nextInt(2.0.pow(16.0).toInt())
        activityCallbacks.put(requestCode, callback)
        startActivityForResult(intent, requestCode, options)
    }

    @Deprecated("Deprecated in Java")
    @Suppress("deprecation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var callback: OnActivityCallback?
        if ((activityCallbacks.get(requestCode).also { callback = it }) != null) {
            callback?.onActivityResult(resultCode, data)
            activityCallbacks.remove(requestCode)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    interface OnActivityCallback {
        /**
         * 结果回调
         *
         * @param resultCode        结果码
         * @param data              数据
         */
        fun onActivityResult(resultCode: Int, data: Intent?)
    }


}
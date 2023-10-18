package com.ygzy.lib_base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar
import com.ygzy.lib_base.BR
import java.lang.reflect.ParameterizedType

/**
 *    @author : taomf
 *    Date    : 2022/10/10/13:28
 *    Desc    : activity基类
 */
abstract class BaseVMActivity<VM : BaseVMViewModel, B : ViewDataBinding>(private val contentViewResId: Int) :AppCompatActivity() {


    lateinit var mViewModel: VM
    lateinit var mBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).init()

        initViewModel()
        initDataBinding()
        initOnCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        initOnResume()
    }


    /** ViewModel初始化 */
    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {
        // 这里利用反射获取泛型中第一个参数ViewModel
        val type: Class<VM> = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this).get(type)
        mViewModel.start()
    }

    /** DataBinding初始化 */
    private fun initDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, contentViewResId)
        mBinding.apply {
            // 需绑定lifecycleOwner到activity,xml绑定的数据才会随着liveData数据源的改变而改变
            lifecycleOwner = this@BaseVMActivity
            setVariable(BR.viewModel,mViewModel)
        }
    }
    open fun initOnCreate(savedInstanceState: Bundle?){}
    open fun initOnResume(){}

    /**
     * 设置状态栏字体是深色
     */
    fun setBarDark(){
        ImmersionBar.with(this).statusBarDarkFont(true).init()
    }

}
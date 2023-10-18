package com.ygzy.lib_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

/**
 * 封装了ViewModel和DataBinding的Fragment基类
 *
 * @author LTP  2021/11/23
 */
abstract class BaseVMFragment<B : ViewDataBinding>(private val contentViewResId: Int) :
    Fragment() {

    /** 是否第一次加载 */
    private var mIsFirstLoading = true

//    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, contentViewResId, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsFirstLoading = true
//        initViewModel()
        initView()
        setupDataBinding()
    }

//    /** ViewModel初始化 */
//    @Suppress("UNCHECKED_CAST")
//    private fun initViewModel() {
//        // 这里利用反射获取泛型中第一个参数ViewModel
//        val type: Class<VM> =
//            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
//        mViewModel = ViewModelProvider(this).get(type)
//        mViewModel.start()
//    }

    /** DataBinding相关设置 */
    private fun setupDataBinding() {
        mBinding.apply {
            // 需绑定lifecycleOwner到Fragment,xml绑定的数据才会随着liveData数据源的改变而改变
            lifecycleOwner = viewLifecycleOwner
//            setVariable(BR.com.ygzy.devicewebservice.viewModel, mViewModel)
        }
    }

    /** View相关初始化 */
    abstract fun initView()

    override fun onResume() {
        super.onResume()
        if (lifecycle.currentState == Lifecycle.State.STARTED && mIsFirstLoading) {
            lazyLoadData()
            mIsFirstLoading = false
        }
    }

    /** 数据懒加载 */
    open fun lazyLoadData() {}


}
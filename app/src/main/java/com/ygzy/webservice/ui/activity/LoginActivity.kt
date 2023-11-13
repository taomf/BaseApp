package com.ygzy.webservice.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import com.blankj.utilcode.util.ActivityUtils
import com.ygzy.lib_base.ext.onClick
import com.ygzy.lib_base.ext.showToast
import com.ygzy.webservice.R
import com.ygzy.webservice.base.BaseActivity
import com.ygzy.webservice.databinding.ActivityLoginBinding
import com.ygzy.webservice.viewModel.LoginViewModel


class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>(R.layout.activity_login) {
    private val exitInterval = 2000L
    private var firstTime: Long = 0

    override fun initOnCreate(savedInstanceState: Bundle?) {
        mBinding.clickEvent = ClickEvent()
        setBarDark()

        mViewModel.loginStatus.observe(this){
            if(it == true){

                finish()
            }
        }

    }

    override  fun onResume() {
        super.onResume()
        mViewModel.test3()
        mBinding.tv.onClick {
            mViewModel.login()

        }

        mBinding.tv2.onClick {
            mViewModel.login2()

        }


    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val secondTime = System.currentTimeMillis()
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < exitInterval) {
                ActivityUtils.finishAllActivities()
            } else {
                showToast(getString(R.string.app_exit))
                firstTime = System.currentTimeMillis()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    inner class ClickEvent {

        fun login() {
            mViewModel.login(

                )
        }

    }
}
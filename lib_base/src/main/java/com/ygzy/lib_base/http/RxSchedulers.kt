package com.ygzy.lib_base.http

import com.blankj.utilcode.util.NetworkUtils
import com.ygzy.lib_base.ext.showToast
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 备注：转换
 * @author taomf
 */
object RxSchedulers {
    fun <T> compose(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable: Observable<T> ->
            observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { disposable: Disposable ->
                    //TO-DO 请求发送之前做的事情
                    if (!NetworkUtils.isAvailable()) {
                        showToast("网络错误！")
                        disposable.dispose()
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}
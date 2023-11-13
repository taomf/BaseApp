package com.ygzy.lib_base.http

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ygzy.lib_base.ext.TOKEN
import com.ygzy.lib_base.ext.TOKEN_TYPE
import com.ygzy.lib_base.ext.getSp
import com.ygzy.lib_base.utils.MyLogUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 备注：Retrofit生产者
 * @author taomf
 */
object ServiceGenerator {
    /** 测试 **/
//    private const val BASEURL = "http://192.168.10.112/"
    /** uat **/
    private const val BASEURL = "http://192.168.11.92:8089/"

    private const val TIMEOUT = 10L

    fun <T> getService(serviceClass: Class<T>): T {
        return getCustomService(BASEURL, serviceClass)
    }

    private fun <T> getCustomService(domain: String, serviceClass: Class<T>): T {
        synchronized(ServiceGenerator::class.java) {
            return createServiceFrom(getRetrofit(domain), serviceClass)
        }
    }

    private fun <T> createServiceFrom(retrofit: Retrofit, serviceClass: Class<T>): T = retrofit.create(serviceClass)


    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttp) //请求Call的转换器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //返回内容的转换器
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke()) //返回内容的转换器
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }// 以拦截到的请求为基础创建一个新的请求对象，然后插入Header

    //日志拦截器
    private val okHttp:OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)

            //日志拦截器
            val logging = HttpLoggingInterceptor { message: String -> MyLogUtils.d(MyLogUtils.HTTP_TAG, message) }

            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)

            val token = TOKEN.getSp()
            val tokenType = TOKEN_TYPE.getSp()

            builder.addInterceptor { chain: Interceptor.Chain ->
                // 以拦截到的请求为基础创建一个新的请求对象，然后插入Header
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "$tokenType $token")
//                    .addHeader("sourceCode", "2")
                    .build()
                chain.proceed(request)
            }
            return builder.build()
        }
}
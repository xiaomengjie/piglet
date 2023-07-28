package com.example.xiao.piglet.network

import android.os.Handler
import android.os.Looper
import com.example.xiao.piglet.ui.dialog.LoadingDialog
import okhttp3.Interceptor
import okhttp3.Response

class LoadingInterceptor(private val dialog: LoadingDialog): Interceptor {

    private val handler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        handler.post{
            dialog.show()
        }
        val response = chain.proceed(chain.request())
        handler.post{
            dialog.dismiss()
        }
        return response
    }
}
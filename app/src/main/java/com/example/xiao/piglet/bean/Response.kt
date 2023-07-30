package com.example.xiao.piglet.bean

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.xiao.piglet.base.MessageEvent
import com.google.gson.annotations.SerializedName
import org.greenrobot.eventbus.EventBus

data class Response<T>(
    @SerializedName("code")
    val code: Int = -1,
    @SerializedName("msg")
    val msg: String = "",
    @SerializedName("result")
    val data: T? = null
){
    fun <T> refreshNotification(lifecycleOwner: LifecycleOwner, type: String, data: T? = null, position: Int = -1){
        if (this.code == 200){
            EventBus.getDefault().post(MessageEvent(type, data, position))
            when(lifecycleOwner){
                is Activity -> lifecycleOwner.finish()
                is Fragment -> lifecycleOwner.findNavController().popBackStack()
            }
        }
    }
}

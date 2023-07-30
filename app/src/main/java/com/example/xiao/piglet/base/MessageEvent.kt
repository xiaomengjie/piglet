package com.example.xiao.piglet.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.greenrobot.eventbus.EventBus

data class MessageEvent<T>(
    //类型选择
    val type: String,
    //数据
    val data: T? = null,
    //更新位置
    val position: Int = -1
)

class MessageEventObserver: DefaultLifecycleObserver{

    override fun onCreate(owner: LifecycleOwner) {
        EventBus.getDefault().register(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        EventBus.getDefault().unregister(owner)
    }
}

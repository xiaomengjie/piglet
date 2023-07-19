package com.example.www.saveps.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.www.saveps.R
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity<T: ViewBinding>: AppCompatActivity() {

    //初始化ViewBinding
    protected val viewBinding: T by lazy {
        initViewBinding()
    }
    abstract fun initViewBinding(): T

    //EventBus控制开启关闭
    protected open val useEventBus: Boolean = false

    //EventBus刷新方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun <T> refreshDisplay(messageEvent: MessageEvent<T>){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        if (useEventBus) lifecycle.addObserver(MessageEventObserver())
        window?.statusBarColor = resources.getColor(R.color.purple_500)
//        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
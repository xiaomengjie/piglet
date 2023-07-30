package com.example.xiao.piglet.base

import android.content.res.Configuration
import android.content.res.TypedArray
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.xiao.piglet.R
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
        window?.statusBarColor = getColorPrimary()
//        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun getColorPrimary(): Int {
        // 获取当前Activity所使用的Theme中的colorPrimary的属性ID
        val attrs = intArrayOf(android.R.attr.colorPrimary)
        val typedArray: TypedArray = obtainStyledAttributes(attrs)
        // 从TypedArray中获取colorPrimary的值
        val colorPrimary = typedArray.getColor(0, 0)
        // 记得要回收TypedArray
        typedArray.recycle()
        return colorPrimary
    }
}
package com.example.xiao.piglet.base

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseFragment<VB: ViewBinding>: Fragment(), MenuProvider {

    //ViewBinding
    protected lateinit var viewBinding: VB

    abstract fun initViewBinding(inflater: LayoutInflater): VB

    //Menu是否含有
    protected open val inflateMenu: Boolean = false

    //EventBus控制开启关闭
    protected open val useEventBus: Boolean = false

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        TODO("Not yet implemented")
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (useEventBus) lifecycle.addObserver(MessageEventObserver())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (inflateMenu) activity?.addMenuProvider(this)
        return initViewBinding(inflater).apply {
            viewBinding = this
        }.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (inflateMenu) activity?.removeMenuProvider(this)
    }

    //EventBus刷新方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun <T> refreshDisplay(messageEvent: MessageEvent<T>){}
}
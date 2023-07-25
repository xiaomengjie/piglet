package com.example.xiao.piglet.ui.dialog

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.animation.LinearInterpolator
import com.example.xiao.piglet.R
import com.example.xiao.piglet.databinding.DialogLoadingBinding


class LoadingDialog(context: Context): Dialog(context) {

    private val viewBinding by lazy {
        DialogLoadingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawableResource(R.color.transparent)
        setContentView(viewBinding.root)
        val animator = ObjectAnimator.ofFloat(
            viewBinding.image,
            "rotation",
            0f, 360f
        )
        animator.duration = 1_000
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = ObjectAnimator.INFINITE
        animator.start()
        setOnDismissListener {
            animator.cancel()
        }
        setOnShowListener {
            if (!animator.isRunning) animator.start()
        }
    }
}
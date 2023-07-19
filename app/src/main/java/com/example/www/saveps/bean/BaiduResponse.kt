package com.example.www.saveps.bean

data class BaiduTranslateBean(
    val from: String = "",
    val to: String = "",
    val trans_result: List<BaiduTranslateResult> = emptyList(),
)

data class BaiduTranslateResult(
    val src: String,
    val dst: String
)
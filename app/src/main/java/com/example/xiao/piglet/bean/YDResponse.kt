package com.example.xiao.piglet.bean
import com.google.gson.annotations.SerializedName

/*
* {
  "errorCode":"0",
  "query":"good", //查询正确时，一定存在
  "isDomainSupport":"true", //翻译结果是否为领域翻译(仅开通领域翻译时存在)
  "translation": [ //查询正确时一定存在
      "好"
  ],
  "basic":{ // 有道词典-基本词典,查词时才有
      "phonetic":"gʊd",
      "uk-phonetic":"gʊd", //英式音标
      "us-phonetic":"ɡʊd", //美式音标
      "uk-speech": "XXXX",//英式发音
      "us-speech": "XXXX",//美式发音
      "explains":[
          "好处",
          "好的",
          "好",
      ]
  },
  "web":[ // 有道词典-网络释义，该结果不一定存在
      {
          "key":"good",
          "value":["良好","善","美好"]
      },
      {...}
  ],
  "dict":{
      "url":"yddict://m.youdao.com/dict?le=eng&q=good"
  },
  "webdict":{
      "url":"http://m.youdao.com/dict?le=eng&q=good"
  },
  "l":"EN2zh-CHS",
  "tSpeakUrl":"XXX",//翻译后的发音地址
  "speakUrl": "XXX" //查询文本的发音地址
}
* */
data class YDResponse(
    @SerializedName("basic")
    val basic: Basic = Basic(),
    @SerializedName("dict")
    val dict: Dict = Dict(),
    @SerializedName("errorCode")
    val errorCode: String = "",
    @SerializedName("isDomainSupport")
    val isDomainSupport: String = "",
    @SerializedName("l")
    val l: String = "",
    @SerializedName("query")
    val query: String = "",
    @SerializedName("speakUrl")
    val speakUrl: String = "",
    @SerializedName("tSpeakUrl")
    val tSpeakUrl: String = "",
    @SerializedName("translation")
    val translation: List<String> = listOf(),
    @SerializedName("web")
    val web: List<Web> = listOf(),
    @SerializedName("webdict")
    val webdict: Webdict = Webdict()
)

data class Basic(
    @SerializedName("explains")
    val explains: List<String> = listOf(),
    @SerializedName("phonetic")
    val phonetic: String = "",
    @SerializedName("uk-phonetic")
    val ukPhonetic: String = "",
    @SerializedName("uk-speech")
    val ukSpeech: String = "",
    @SerializedName("us-phonetic")
    val usPhonetic: String = "",
    @SerializedName("us-speech")
    val usSpeech: String = ""
)

data class Dict(
    @SerializedName("url")
    val url: String = ""
)

data class Web(
    @SerializedName("key")
    val key: String = "",
    @SerializedName("value")
    val value: List<String> = listOf()
)

data class Webdict(
    @SerializedName("url")
    val url: String = ""
)
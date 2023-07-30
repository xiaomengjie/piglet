package com.example.xiao.piglet.tool

import com.example.xiao.piglet.network.NetworkClient
import com.example.xiao.piglet.network.api.HandshakeAPI
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.security.MessageDigest
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

object SSL {
    suspend inline fun sslRequest(function: (String) -> Unit){
        //获取服务器返回的证书
        val certificate = NetworkClient.create<HandshakeAPI>().hello().bytes()
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val generateCertificate =
            certificateFactory.generateCertificate(certificate.inputStream()) as X509Certificate
        //解析证书获取到公钥
        val publicKey = generateCertificate.publicKey
        //随机生成16个字节的AES密钥
        val aesKey = (1 .. 16).map {
            Random.nextInt(0, 26)
        }.map {
            ('a' .. 'z').toList()[it]
        }.joinToString("")
        //对AES密钥用公钥加密（获取Cipher对象时，由于两端的默认填充方式可能不一样，需要指定填充方式，不然解密会失败）
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptedKey = cipher.doFinal(aesKey.toByteArray())
        //将加密后的AES密钥传送到服务器
        NetworkClient.create<HandshakeAPI>().key(encryptedKey.toRequestBody("application/octet-stream".toMediaType()))
        function(aesKey)
        NetworkClient.create<HandshakeAPI>().keyClean()
    }

    fun genericRandom(): Int{
        // 获取熵源数据
        fun getEntropy(): ByteArray {
            // 此处可以从不同的熵源收集数据，例如鼠标移动、键盘输入、网络延迟等
            // 这里为了简单起见，使用随机字节作为熵源数据
            return Random.nextBytes(32)
        }
        // 压缩熵源数据
        fun compressEntropy(entropy: ByteArray): ByteArray {
            // 使用 SHA-256 哈希算法来压缩熵源数据
            val md = MessageDigest.getInstance("SHA-256")
            return md.digest(entropy)
        }
        // 生成种子
        fun generateSeed(compressedEntropy: ByteArray): Long {
            // 将压缩后的熵源数据转换为一个 long 类型种子
            var seed: Long = 0
            for (i in 0 until 8) {
                seed = (seed shl 8) or (compressedEntropy[i].toLong() and 0xFF)
            }
            return seed
        }
        // 生成随机数
        fun generateRandomNumber(seed: Long): Int {
            // 使用伪随机数生成器，以 seed 为种子生成随机数
            val random = Random(seed)
            return random.nextInt()
        }
        // 获取熵源数据
        val entropy = getEntropy()
        // 压缩熵源数据
        val compressedEntropy = compressEntropy(entropy)
        // 生成种子
        val seed = generateSeed(compressedEntropy)
        // 生成随机数
        return generateRandomNumber(seed)
    }

    fun hmacSHA256(key: ByteArray, data: ByteArray): ByteArray {
        val hmacKey = SecretKeySpec(key, "HmacSHA256")
        val hmac = Mac.getInstance("HmacSHA256")
        hmac.init(hmacKey)
        return hmac.doFinal(data)
    }
}
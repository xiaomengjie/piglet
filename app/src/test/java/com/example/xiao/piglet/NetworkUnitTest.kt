package com.example.xiao.piglet

import com.example.xiao.piglet.bean.Password
import com.example.xiao.piglet.network.NetworkClient
import com.example.xiao.piglet.network.api.HandshakeAPI
import com.example.xiao.piglet.network.api.PasswordAPI
import com.example.xiao.piglet.tool.aesEncrypt
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Test
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.crypto.Cipher
import kotlin.random.Random

class NetworkUnitTest {
    @Test
    fun networkIncreasePassword(){
        runBlocking {
            println(
                NetworkClient.create<PasswordAPI>().insertPassword(
                    Password("facebook", "afafaedsaf")
                ))
        }
    }

    @Test
    fun networkSearchPassword(){
        runBlocking {
            println(NetworkClient.create<PasswordAPI>().searchPassword("facebook"))
        }
    }

    @Test
    fun networkQueryAllPassword(){
        runBlocking {
            println(NetworkClient.create<PasswordAPI>().queryAllPassword())
        }
    }

    @Test
    fun networkDeleteAllPassword(){
        runBlocking {
            println(NetworkClient.create<PasswordAPI>().deleteAllPassword())
        }
    }

    @Test
    fun ssl(){
        runBlocking {
            val certificate = NetworkClient.create<HandshakeAPI>().hello().bytes()
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val generateCertificate =
                certificateFactory.generateCertificate(certificate.inputStream()) as X509Certificate
            val publicKey = generateCertificate.publicKey
            val aesKey = (1 .. 16).map {
                Random.nextInt(0, 26)
            }.map {
                ('a' .. 'z').toList()[it]
            }.joinToString("")
            println("aesKey = $aesKey")
            val cipher = Cipher.getInstance("RSA")
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            val encryptedKey = cipher.doFinal(aesKey.toByteArray())
            NetworkClient.create<HandshakeAPI>().key(encryptedKey.toRequestBody("application/octet-stream".toMediaType()))

            NetworkClient.create<PasswordAPI>().insertPassword(
                Password("AL", "123456789".aesEncrypt(aesKey))
            )
        }
    }
}



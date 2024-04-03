package com.weredev.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weredev.app.databinding.ActivityMainBinding
import com.weredev.app.utils.NetworkPreferences
import com.weredev.app.utils.SSLUtils
import com.weredev.binding_ui.viewBinding
import com.weredev.ktorUtils.KtorUtils
import com.weredev.ktorUtils.exception.NetworkException
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

/**
 * This example demonstrates the use of ktorUtils to acquire json.
 */
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnFakeFloating.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val client = createClient()
                //init client
                KtorUtils.initClient(client)

                KtorUtils.executeCallLight(HttpMethod.Get, endpoint = "https://jsonplaceholder.typicode.com/todos/1")

                val responseBody = try {
                    KtorUtils.executeCall<String>(HttpMethod.Get, endpoint = "https://jsonplaceholder.typicode.com/todos/1")
                } catch (e: NetworkException){
                    e.message
                }

                CoroutineScope(Dispatchers.Main).launch {
                    binding.txtExample.text = responseBody
                }
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun createClient(): HttpClient {
        val networksPref = NetworkPreferences()
        return HttpClient(configureEngineForPlatform(networksPref.disableSSLCheks)) { // CIO error in ios device tls sessions are not supported on native platform
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = true
                    isLenient = true
                    prettyPrint = true
                    explicitNulls = false
                    encodeDefaults = true
                })
            }
            install(createClientPlugin("fix") {
                on(Send) { request ->
                    request.headers.remove("Accept-Charset")
                    this.proceed(request)
                }
            })
            install(Logging){
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.i("Ktor: $message")
                    }
                }
                level = networksPref.logLevel.toLogLevel()
            }.also { Napier.base(DebugAntilog()) }
        }
    }

    private fun configureEngineForPlatform(disableSSLChecks: Boolean): HttpClientEngine {
        val sSLUtils = SSLUtils()
        return Android.create {
            if(disableSSLChecks) {
                sslManager = { httpsURLConnection ->
                    sSLUtils.setHttpClientSSL(httpsURLConnection)
                }
            }
        }
    }
}
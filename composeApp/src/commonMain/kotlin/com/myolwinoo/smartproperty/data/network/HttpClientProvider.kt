package com.myolwinoo.smartproperty.data.network

import com.myolwinoo.smartproperty.data.AccountManager
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientProvider(
    private val accountManager: AccountManager
) {

    fun get() = HttpClient {

        expectSuccess = true

        defaultRequest {
            url("http://10.0.2.2:8000/")
            contentType(ContentType.Application.Json)
            headers {
                accountManager.getToken()?.let {
                    append(HttpHeaders.Authorization, "Bearer $it")
                }
            }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    isLenient = true
                },
                contentType = ContentType.Application.Json
            )
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.i { message }
                }
            }
            level = LogLevel.ALL
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }.also { Napier.base(DebugAntilog()) }
    }
}
package com.github.jetbrains.rssreader

import com.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.logging.*
import java.util.concurrent.TimeUnit

internal fun AndroidHttpClient(withLog: Boolean) = HttpClient(OkHttp) {
    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(5, TimeUnit.SECONDS)
        }
    }
    install(Logging) {
        level = LogLevel.HEADERS
        logger = object : Logger {
            override fun log(message: String) {
                Napier.v(tag = "AndroidHttpClient", message = message)
            }
        }
    }
}
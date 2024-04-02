package com.weredev.app.utils


import io.ktor.client.plugins.logging.LogLevel

enum class LogLevelRequest {
    ALL,
    HEADERS,
    BODY,
    INFO,
    NONE;

    fun toLogLevel(): LogLevel {
        return when(this) {
            ALL -> LogLevel.ALL
            HEADERS -> LogLevel.HEADERS
            BODY -> LogLevel.BODY
            INFO -> LogLevel.INFO
            NONE -> LogLevel.NONE
        }
    }
}

data class NetworkPreferences(
    val logLevel: LogLevelRequest = LogLevelRequest.ALL,
    val disableSSLCheks: Boolean = false
)
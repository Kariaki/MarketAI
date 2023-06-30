package com.marketai

import com.marketai.di.markettAiModule
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.marketai.plugins.*
import io.ktor.client.plugins.websocket.*
import io.ktor.server.websocket.WebSockets
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    EngineMain.main(args)
}


fun Application.module() {
    install(Koin) {
        modules(markettAiModule)
    }
    install(WebSockets) {
        pingPeriodMillis = 15000
        timeoutMillis = 15000
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    configureHTTP()
    configureSerialization()
    configureSecurity()
    configureRouting()


}

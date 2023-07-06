package com.marketai

import com.marketai.di.markettAiModule
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import com.marketai.plugins.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.websocket.*
import io.ktor.server.application.*
import io.ktor.server.websocket.WebSockets
import io.ktor.websocket.*
import kotlinx.coroutines.selects.select
import org.koin.ktor.plugin.Koin
import java.awt.Frame

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
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

        client.webSocket("/chat") {
            select<Unit> {
                incoming.onReceiveCatching {
                    if (it.isClosed) {
                        val frame = it.getOrNull() as io.ktor.websocket.Frame.Text
                        assertEquals("socket connected", frame.readText())

                    }
                }
            }
        }

        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}

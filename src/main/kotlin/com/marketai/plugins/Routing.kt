package com.marketai.plugins

import com.marketai.controller.MarketAiChatController
import com.marketai.exceptions.OpenAiException
import com.marketai.session.GuestSession
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.selects.select
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val controller: MarketAiChatController by inject()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        webSocket("/chat") {
            val session = call.sessions.get<GuestSession>() ?: kotlin.run {
                close()
                return@webSocket
            }
            try {
                for (frame in incoming) {
                    val result = controller.handlePrompt(frame)
                    this.send(result)
                    // val actionResult = Json.decodeFromString<SocketAction>(receivedText)
                }
                /*

                     select<Unit> {
                         incoming.onReceiveCatching {
                             if (it.isClosed) {
                                 controller.disconnectUser(session)
                             }
                         }
                     }
                 */
            } catch (e: OpenAiException) {
                e.printStackTrace()
                this.send(e.message)
            } catch (e: Exception) {
                e.printStackTrace()
                this.send(e.localizedMessage)
            }
        }

    }
}

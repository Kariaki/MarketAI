package com.marketai.plugins

import com.marketai.controller.MarketAiController
import com.marketai.domain.entities.toFrameResponseError
import com.marketai.domain.entities.toJson
import com.marketai.domain.toJson
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

    val controller: MarketAiController by inject()

    routing {

        get("/") {
            call.respondText("Hello World!")
        }

        webSocket("/chat") {
            val session = call.sessions.get<GuestSession>() ?: kotlin.run {
                close()
                return@webSocket
            }
            controller.connectToSocket(session, this)
            try {
                for (frame in incoming) {
                    val text = frame as Frame.Text
                    val input = text.readText()
                    controller.handlePrompt(input, session)
                }
                select {
                    incoming.onReceiveCatching {
                        if (it.isClosed) {
                            controller.disconnectUser(session)
                        }
                    }
                }

            } catch (e: OpenAiException) {
                e.printStackTrace()
                this.send(e.message.toFrameResponseError())
            } catch (e: Exception) {
                e.printStackTrace()
                this.send(e.localizedMessage.toFrameResponseError())
            }
        }

    }
}

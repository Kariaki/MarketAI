package com.marketai.data.model

import com.marketai.client.openai.model.ClientMessage
import io.ktor.websocket.*

data class MarkettConnections(
    val sessionId: String,
    val session: WebSocketSession,
    val message: MutableList<ClientMessage> = mutableListOf()
)

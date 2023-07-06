package com.marketai.controller

import com.marketai.client.openai.model.ClientMessage
import com.marketai.client.openai.model.OpenAiPromptBody
import com.marketai.client.openai.repository.OpenAiPromptRepository
import com.marketai.core.Constants
import com.marketai.core.PromptCategory
import com.marketai.core.Role
import com.marketai.data.model.MarkettConnections
import com.marketai.domain.FrameBaseResponse
import com.marketai.domain.entities.FrameResponse
import com.marketai.domain.entities.FrameResponseData
import com.marketai.domain.entities.toClientMessage
import com.marketai.domain.entities.toFrameRequest
import com.marketai.domain.toJson
import com.marketai.exceptions.ImproperInputException
import com.marketai.session.GuestSession
import io.ktor.websocket.*
import java.util.concurrent.ConcurrentHashMap


class MarketAiController(
    private val repository: OpenAiPromptRepository
) {

    companion object {
        val connections: ConcurrentHashMap<String, MarkettConnections> = ConcurrentHashMap()
    }

    fun connectToSocket(session: GuestSession, socket: WebSocketSession) {
        val connection = connections[session.sessionId] ?: MarkettConnections(session.sessionId, socket)
        connections[session.sessionId] = connection

    }


    suspend fun handlePrompt(frame: String, session: GuestSession) {
        val clientMessage = frame.toFrameRequest()
            .toClientMessage()
        if (clientMessage.content.isEmpty()) throw ImproperInputException()
        val connect = connections[session.sessionId] ?: return
        connect.message.add(clientMessage)
        val body = OpenAiPromptBody(connect.message, Constants.model)

        try {
            val result = repository.promptOpenAi(body)
            val output = result.choices.first().message.content
            val assistantResponse = ClientMessage(Role.ASSISTANT.name.lowercase(), output)
            connect.message.add(assistantResponse)
            val frameResponse = FrameResponse(
                PromptCategory.Text, FrameResponseData(
                    output, listOf()
                )
            )
            val baseResponse = FrameBaseResponse(
                error = null, result = listOf(frameResponse)
            )
            val json = baseResponse.toJson()
            connect.session.send(json) // send a frame out
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun gptPrompt(text: String): String {

        return ""
    }

    fun disconnectUser(session: GuestSession) {
        connections.remove(session.sessionId)
    }

}
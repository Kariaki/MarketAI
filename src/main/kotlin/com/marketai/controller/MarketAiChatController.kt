package com.marketai.controller

import com.marketai.client.openai.model.ClientMessage
import com.marketai.client.openai.model.OpenAiPromptBody
import com.marketai.client.openai.repository.OpenAiPromptRepository
import com.marketai.core.Constants
import com.marketai.core.Role
import com.marketai.session.GuestSession
import io.ktor.websocket.*

class MarketAiChatController(
    private val repository: OpenAiPromptRepository
) {

    private val message: MutableList<ClientMessage> = mutableListOf()
    suspend fun handlePrompt(frame: String,session:GuestSession): String {
        message.add(ClientMessage(content = frame, role = Role.USER.name.lowercase()))
        val body = OpenAiPromptBody(message, Constants.model)
        try {

            val result = repository.promptOpenAi(body)
            val output = result.choices.first().message.content
            val assistantResponse = ClientMessage(Role.ASSISTANT.name.lowercase(), output)
            message.add(assistantResponse)
            return output
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun marketAiSubPrompt(text: String): String {

        return ""
    }

}
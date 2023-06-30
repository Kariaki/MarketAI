package com.marketai.controller

import com.marketai.client.openai.model.ClientMessage
import com.marketai.client.openai.model.OpenAiPromptBody
import com.marketai.client.openai.repository.OpenAiPromptRepository
import io.ktor.websocket.*

class MarketAiChatController(
    private val repository: OpenAiPromptRepository
) {

    private val message: MutableList<ClientMessage> = mutableListOf()
    suspend fun handlePrompt(frame: Frame): String {
        val newFrame = frame as Frame.Text
        message.add(ClientMessage(content = newFrame.readText(), role = "user"))
        val body = OpenAiPromptBody(message, "gpt-3.5-turbo")
        try {

            val result = repository.promptOpenAi(body)
            val output = result.choices.first().message.content
            val assistantResponse = ClientMessage("assistant", output)
            message.add(assistantResponse)
            return output
        } catch (e: Exception) {
            throw e
        }
    }

}
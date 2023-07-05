package com.marketai.controller

import com.marketai.client.openai.model.ClientMessage
import com.marketai.client.openai.model.OpenAiPromptBody
import com.marketai.client.openai.repository.OpenAiPromptRepository
import com.marketai.core.Constants
import com.marketai.core.PromptCategory
import com.marketai.core.Role
import com.marketai.domain.FrameBaseResponse
import com.marketai.domain.entities.FrameResponse
import com.marketai.domain.entities.FrameResponseData
import com.marketai.domain.entities.toClientMessage
import com.marketai.domain.entities.toFrameRequest
import com.marketai.session.GuestSession
import io.ktor.websocket.*

class MarketAiChatController(
    private val repository: OpenAiPromptRepository
) {

    private val message: MutableList<ClientMessage> = mutableListOf()
    suspend fun handlePrompt(frame: String,session:GuestSession): FrameBaseResponse {
        val clientMessage= frame.toFrameRequest().toClientMessage()
        message.add(clientMessage)
        val body = OpenAiPromptBody(message, Constants.model)
        try {
            val result = repository.promptOpenAi(body)
            val output = result.choices.first().message.content
            val assistantResponse = ClientMessage(Role.ASSISTANT.name.lowercase(), output)
            message.add(assistantResponse)

            val frameResponse =  FrameResponse(
                PromptCategory.Text, FrameResponseData(
                    output, listOf()
                )
            )
            return FrameBaseResponse(
                error=null, result = listOf(frameResponse)
            )
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun gptPrompt(text: String): String {

        return ""
    }

}
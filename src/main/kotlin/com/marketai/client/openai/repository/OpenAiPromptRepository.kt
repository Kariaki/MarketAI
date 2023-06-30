package com.marketai.client.openai.repository
import com.marketai.client.openai.model.OpenAiPromptBody
import com.marketai.client.openai.model.OpenAiPromptResponse
interface OpenAiPromptRepository {
    suspend fun promptOpenAi(body:OpenAiPromptBody):OpenAiPromptResponse
}
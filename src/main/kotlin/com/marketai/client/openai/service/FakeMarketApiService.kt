package com.marketai.client.openai.service

import com.marketai.client.openai.model.OpenAiPromptBody
import com.marketai.client.openai.model.OpenAiPromptResponse
import com.marketai.client.openai.repository.OpenAiPromptRepository

class FakeMarketApiService():OpenAiPromptRepository {
    override suspend fun promptOpenAi(body: OpenAiPromptBody): OpenAiPromptResponse {
        TODO("Not yet implemented")
    }

}
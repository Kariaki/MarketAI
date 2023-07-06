package com.marketai.client.openai.service

import com.google.gson.Gson
import com.marketai.client.openai.model.OpenAiPromptBody
import com.marketai.client.openai.model.OpenAiPromptResponse
import com.marketai.client.openai.repository.OpenAiPromptRepository
import com.marketai.core.Constants
import com.marketai.exceptions.OpenAiException
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ChatgptService(
    private val client: HttpClient,
) : OpenAiPromptRepository {

    override suspend fun promptOpenAi(body: OpenAiPromptBody): OpenAiPromptResponse = try {
        val response = client.post {
            timeout {  }
            url("${Constants.openAiBaseUrl}/v1/chat/completions")
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${Constants.openAiApiKey}")
            setBody(body)
        }
        val responseBody = response.bodyAsText()
        Gson().fromJson(responseBody, OpenAiPromptResponse::class.java)

    } catch (e: Exception) {
        throw e
    }

}
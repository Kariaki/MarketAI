package com.marketai.client.openai.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PromptResponseChoice(
    val index: Int,
    @SerialName("finish_reason")
    val finishReason:String,
    val message: OpenAiPromptResponseChoiceMessage
)

@Serializable
data class OpenAiPromptResponseChoiceMessage(
    val role: String,
    val content: String
)

package com.marketai.client.openai.model

import kotlinx.serialization.Serializable

@Serializable
data class OpenAiPromptBody(
    val messages:List<ClientMessage>,
    val model:String
)

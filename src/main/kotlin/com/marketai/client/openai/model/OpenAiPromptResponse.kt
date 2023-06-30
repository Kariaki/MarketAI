package com.marketai.client.openai.model

import kotlinx.serialization.Serializable

@Serializable
data class OpenAiPromptResponse(
    val id:String,
    val created:Long,
    val model:String,
    val choices:List<PromptResponseChoice>
)

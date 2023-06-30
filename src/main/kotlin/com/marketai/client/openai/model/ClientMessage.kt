package com.marketai.client.openai.model

import kotlinx.serialization.Serializable

@Serializable
data class ClientMessage(
    val role:String,
    val content:String
)

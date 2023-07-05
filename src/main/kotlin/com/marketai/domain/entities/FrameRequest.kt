package com.marketai.domain.entities

import com.google.gson.Gson
import com.marketai.client.openai.model.ClientMessage
import com.marketai.core.MarkettRoles
import com.marketai.core.Role
import kotlinx.serialization.Serializable

@Serializable
data class FrameRequest(
    val role: MarkettRoles,
    val content: String
)

fun FrameRequest.toClientMessage(): ClientMessage {
    return ClientMessage(content = content, role = Role.USER.name.lowercase())
}

fun String.toFrameRequest(): FrameRequest {
    val gson = Gson()
    return gson.fromJson(this, FrameRequest::class.java)
}
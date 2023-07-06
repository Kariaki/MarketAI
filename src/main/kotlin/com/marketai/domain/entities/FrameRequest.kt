package com.marketai.domain.entities

import com.google.gson.Gson
import com.marketai.client.openai.model.ClientMessage
import com.marketai.core.MarkettRoles
import com.marketai.core.PromptCategory
import com.marketai.core.Prompts
import com.marketai.core.Role
import kotlinx.serialization.Serializable

@Serializable
data class FrameRequest(
    val role: MarkettRoles,
    val action: String
)

fun FrameRequest.toClientMessage(): ClientMessage {
    val newContent = if (role == MarkettRoles.markett) {
        val category = PromptCategory.valueOf(action)
        val result = Prompts.prompts[category]
        result ?: ""
    } else action
    return ClientMessage(content = newContent, role = Role.USER.name.lowercase())
}

fun String.toFrameRequest(): FrameRequest {
    val gson = Gson()
    return gson.fromJson(this, FrameRequest::class.java)
}
package com.marketai.domain.entities

import com.google.gson.Gson
import com.marketai.core.PromptCategory
import com.marketai.domain.FrameBaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class FrameResponse(
    val category: PromptCategory,
    val data: FrameResponseData
)

fun FrameResponse.toJson(): String {
    val gson = Gson()
    return gson.toJson(this)
}

fun String.toFrameResponseError(): String {
    val response = FrameBaseResponse(error = this, result = listOf())
    return Gson().toJson(response)
}

@Serializable
data class FrameResponseData(
    val content: String,
    val url: List<String> = listOf()
)
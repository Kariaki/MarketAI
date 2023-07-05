package com.marketai.domain
import com.google.gson.Gson
import com.marketai.domain.entities.FrameResponse
import com.marketai.domain.entities.toJson
import io.ktor.server.http.*
import java.time.Instant

data class FrameBaseResponse(
    val result: List<FrameResponse>,
    val createdAt: String = Instant.now().toHttpDateString(),
    val error: String?
)

fun FrameBaseResponse.toJson():String{
    val gson = Gson()
    return gson.toJson(this)
}
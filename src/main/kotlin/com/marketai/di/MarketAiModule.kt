package com.marketai.di

import com.marketai.client.openai.repository.OpenAiPromptRepository
import com.marketai.client.openai.service.ChatgptService
import com.marketai.controller.MarketAiChatController
import com.marketai.controller.OpenAiController
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.json.*
import io.ktor.client.plugins.kotlinx.serializer.*
import org.koin.dsl.module

val markettAiModule = module {

    single {
        HttpClient(CIO) {
            install(JsonPlugin) {
                serializer = KotlinxSerializer()
            }

        }
    }

    single{
        MarketAiChatController(get())
    }

    single {
        OpenAiController(get())
    }

    single<OpenAiPromptRepository> {
        ChatgptService(get())
    }
}
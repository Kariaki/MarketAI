package com.marketai.di

import com.marketai.client.openai.repository.OpenAiPromptRepository
import com.marketai.client.openai.service.ChatgptService
import com.marketai.client.openai.service.FakeMarketApiService
import com.marketai.controller.MarketAiChatController
import com.marketai.controller.OpenAiController
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.json.*
import io.ktor.client.plugins.kotlinx.serializer.*
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module

val markettAiModule = module {

    single {
        HttpClient(CIO) {
            install(JsonPlugin) {
                serializer = KotlinxSerializer()
            }
            install(HttpTimeout){
                requestTimeoutMillis = 240000
            }

        }
    }

    single {
        Injector.inject
    }

    single {
        MarketAiChatController(get())
    }

    single {
        OpenAiController(get(named(get())))
    }



    single<OpenAiPromptRepository> {
        ChatgptService(get())
    }
    /*    single<OpenAiPromptRepository> {
            named("fake")
            FakeMarketApiService()
        }*/
}
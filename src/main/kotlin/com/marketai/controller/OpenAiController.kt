package com.marketai.controller

import com.marketai.client.openai.repository.OpenAiPromptRepository

class OpenAiController(
    val repository:OpenAiPromptRepository
)
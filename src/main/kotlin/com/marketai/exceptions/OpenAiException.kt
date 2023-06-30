package com.marketai.exceptions

class OpenAiException : Exception(){
    override val message: String
        get() = "Failed to get data from open ai"
}
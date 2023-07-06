package com.marketai.core

object Prompts {

    private const val faceBookAdPrompt = "Please generate a facebook ad for the business above"
    private const val blogPrompt = "Please generate a simple blog post for the business"
    private const val twitterAdPrompts =
        "Please generate a twitter ad content for the business and consider the twitter character limit"

    val prompts: Map<PromptCategory, String> = HashMap<PromptCategory, String>().apply {
        this[PromptCategory.Facebook] = faceBookAdPrompt
        this[PromptCategory.Twitter] = twitterAdPrompts
        this[PromptCategory.Blog] = blogPrompt
    }

}
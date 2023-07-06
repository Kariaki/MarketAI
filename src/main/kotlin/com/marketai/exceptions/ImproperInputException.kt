package com.marketai.exceptions

class ImproperInputException() : Exception() {
    override val message: String
        get() = "markett server error: Input may be incorrect, either an empty string an unidentified input"
}
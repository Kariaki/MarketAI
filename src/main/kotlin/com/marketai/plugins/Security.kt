package com.marketai.plugins

import com.marketai.session.GuestSession
import io.ktor.server.sessions.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import java.util.UUID

fun Application.configureSecurity() {
     install(Sessions) {
        cookie<GuestSession>("guest-session")
    }

    intercept(ApplicationCallPipeline.Features){
        if(call.sessions.get<GuestSession>()==null){
            val sessionId = generateSessionId()
            val session = GuestSession(sessionId)
            call.sessions.set(session)
        }
    }

}

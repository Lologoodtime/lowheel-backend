package com.lowheel

import com.lowheel.bdd.initDatabase
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureCORS()
    initDatabase()
    configureSerialization()
    configureRouting()
}

package com.lowheel

import com.lowheel.bdd.Reviews
import com.lowheel.champions.ChampionReview
import com.lowheel.champions.championRoleMap
import com.lowheel.enums.Role
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/api/champions") {
            val wantedRole = call.request.queryParameters["role"]

            if (wantedRole == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing query parameter: role"
                )
                return@get
            }

            val role = Role.getByName(wantedRole)

            val champions = championRoleMap
                .filterValues { it.contains(role) }
                .keys
                .toList()

            call.respond(champions)
        }

        post("/api/review") {
            // Lire le corps JSON et convertir en ChampionReview
            val review = call.receive<ChampionReview>()

            val id = transaction {
                Reviews.insert {
                    it[role] = review.role
                    it[champion] = review.champion
                    it[win] = review.win
                    it[note] = review.note
                } get Reviews.id
            }

            // Ici tu peux sauvegarder dans une base, map, liste, etc.
            // Pour l'instant on renvoie juste la même review
            call.respond(
                mapOf(
                    "message" to "Review reçue",
                    "review" to review,
                    "id" to id,
                )
            )
        }

        // Lister toutes les reviews
        get("/api/reviews") {
            val allReviews = transaction {
                Reviews.selectAll().map {
                    ChampionReview(
                        id = it[Reviews.id],
                        role = it[Reviews.role],
                        champion = it[Reviews.champion],
                        win = it[Reviews.win],
                        note = it[Reviews.note]
                    )
                }
            }
            call.respond(allReviews)
        }
    }
}

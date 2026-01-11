package com.lowheel.bdd

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun initDatabase() {
    val host = "db"
    val port = 5432
    val dbName = "lol_reviews"
    val user = "postgres"
    val password = "postgres"

    Database.connect(
        url = "jdbc:postgresql://$host:$port/$dbName",
        driver = "org.postgresql.Driver",
        user = user,
        password = password
    )

    // Crée la table si elle n'existe pas
    transaction {
        SchemaUtils.create(Reviews)
    }
}

package com.lowheel.bdd

import org.jetbrains.exposed.sql.Table

object Reviews : Table("reviews") {
    val id = integer("id").autoIncrement()
    val role = varchar("role", 50)
    val champion = varchar("champion", 50)
    val win = bool("win")
    val note = text("note")
    override val primaryKey = PrimaryKey(id)
}
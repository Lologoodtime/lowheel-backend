package com.lowheel.champions

import kotlinx.serialization.Serializable

@Serializable
data class ChampionReview(
    val id: Int? = null,
    val role: String,
    val champion: String,
    val win: Boolean,
    val note: String
)
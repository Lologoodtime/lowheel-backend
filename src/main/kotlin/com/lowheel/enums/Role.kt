package com.lowheel.enums

/**
 * Représente les rôles disponible dans LOL
 */
enum class Role {
    TOP,
    JUNGLE,
    MID,
    ADC,
    SUPPORT;

    companion object {
        fun getByName(name: String): Role =
            entries.first { it.name.equals(name, ignoreCase = true) }
    }
}
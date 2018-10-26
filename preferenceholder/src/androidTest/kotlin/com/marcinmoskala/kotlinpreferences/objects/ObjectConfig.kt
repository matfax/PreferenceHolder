package com.marcinmoskala.kotlinpreferences.objects

import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import kotlinx.serialization.Serializable

object ComplexTestPreferences: PreferenceHolder() {
    var character: Character? by bindToPreferenceFieldNullable()
    var savedGame: Game? by bindToPreferenceFieldNullable()
}

@Serializable
data class Character(
        val name: String,
        val race: String,
        val clazz: String
)

@Serializable
data class Game(
        val character: Character,
        val gameMode: GameMode,
        val level: Int
)

enum class GameMode {
    Easy, Normal, Hard
}

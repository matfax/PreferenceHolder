package com.marcinmoskala.testapp

import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import kotlinx.serialization.Serializable

@Serializable
object TestPreferences: PreferenceHolder() {
    var canEatPie: Boolean by bindToPreferenceField(true)
    var pieBaked: Int by bindToPreferenceField(0)
    var allPieInTheWorld: Long by bindToPreferenceField(0)
    var pieEaten: Float by bindToPreferenceField(0.0F)
    var bestPieName: String by bindToPreferenceField("Pie")

    var isMonsterKiller: Boolean? by bindToPreferenceFieldNullable()
    var monstersKilled: Int? by bindToPreferenceFieldNullable()
    var numberOfHahaInLough: Long? by bindToPreferenceFieldNullable()
    var experience: Float? by bindToPreferenceFieldNullable()
    var className: String? by bindToPreferenceFieldNullable()
}

@Serializable
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
class Game(
        val character: Character,
        val gameMode: GameMode,
        val level: Int
)

enum class GameMode {
    Easy, Normal, Hard
}

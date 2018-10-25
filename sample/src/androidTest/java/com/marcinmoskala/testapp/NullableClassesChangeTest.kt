package com.marcinmoskala.testapp

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.testapp.ComplexTestPreferences.character
import com.marcinmoskala.testapp.ComplexTestPreferences.savedGame
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NullableClassesChangeTest {

    init {
        PreferenceHolder.setContext(InstrumentationRegistry.getTargetContext())
        ComplexTestPreferences.clear()
    }

    @Test
    fun characterTest() {
        assertNull(character)
        character = Character("Marcin", "Human", "Wizard")
        assertEquals(Character("Marcin", "Human", "Wizard"), character!!)
        character = character!!.copy(race = "SuperHuman")
        assertEquals("SuperHuman", character!!.race)
        assertEquals(Character("Marcin", "SuperHuman", "Wizard"), character!!)
    }

    @Test
    fun bigObjectTest() {
        assertNull(savedGame)
        savedGame = Game(Character("Marcin", "Human", "Wizard"), GameMode.Hard, 100)
        assertEquals(Character("Marcin", "Human", "Wizard"), savedGame!!.character)
        assertEquals(GameMode.Hard, savedGame!!.gameMode)
        assertEquals(100, savedGame!!.level)
    }
}

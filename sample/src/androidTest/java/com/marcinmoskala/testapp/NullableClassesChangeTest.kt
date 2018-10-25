package com.marcinmoskala.testapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.testapp.ComplexTestPreferences.character
import com.marcinmoskala.testapp.ComplexTestPreferences.savedGame
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NullableClassesChangeTest : TestCase() {

    init {
        PreferenceHolder.setContext(InstrumentationRegistry.getInstrumentation().targetContext)
        ComplexTestPreferences.clear()
    }

    @Test
    fun testCharacter() {
        assertNull(character)
        character = Character("Marcin", "Human", "Wizard")
        assertEquals(Character("Marcin", "Human", "Wizard"), character!!)
        character = character!!.copy(race = "SuperHuman")
        assertEquals("SuperHuman", character!!.race)
        assertEquals(Character("Marcin", "SuperHuman", "Wizard"), character!!)
    }

    @Test
    fun testBigObject() {
        assertNull(savedGame)
        savedGame = Game(Character("Marcin", "Human", "Wizard"), GameMode.Hard, 100)
        assertEquals(Character("Marcin", "Human", "Wizard"), savedGame!!.character)
        assertEquals(GameMode.Hard, savedGame!!.gameMode)
        assertEquals(100, savedGame!!.level)
    }
}

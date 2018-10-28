package com.marcinmoskala.kotlinpreferences.objects

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marcinmoskala.kotlinpreferences.BaseTest
import com.marcinmoskala.kotlinpreferences.testValues
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ObjectsTest : BaseTest() {

    @Test
    fun characterTest() {
        val character1 = Character("Marcin", "Human", "Wizard")
        val character2 = Character("Marcin", "SuperHuman", "Wizard")
        testValues(ComplexTestPreferences::character, null, character1, character2)
    }

    @Test
    fun bigObjectTest() {
        val game1 = Game(
                Character("Marcin", "Human", "Wizard"),
                GameMode.Hard,
                100
        )
        testValues(ComplexTestPreferences::savedGame, null, game1)
    }
}

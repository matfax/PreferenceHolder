package com.marcinmoskala.kotlinpreferences

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreferenceFieldTest : BaseTest() {

    @Test
    fun booleanDefaultChangeTest() {
        testValues(ExampleConfig::canEatPie, true, false, true, false)
    }

    @Test
    fun intDefaultChangeTest() {
        testValues(ExampleConfig::pieBaked, 5, 10, 0, 10, -19)
    }

    @Test
    fun longDefaultChangeTest() {
        testValues(ExampleConfig::allPieInTheWorld, -1L, 10L, 0L, 100L)
    }

    @Test
    fun floatDefaultChangeTest() {
        testValues(ExampleConfig::pieEaten, 0.0F, 10F, 0.06F, 100F)
    }

    @Test
    fun stringDefaultChangeTest() {
        testValues(ExampleConfig::bestPieName, "Pie", "BlueberryPie", "SuperPie")
    }
}

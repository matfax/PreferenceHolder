package com.marcinmoskala.kotlinpreferences

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreferenceFieldNullableTest : BaseTest() {

    @Test
    fun booleanDefaultChangeTest() {
        testValues(ExampleConfig::isMonsterKiller, null, true, false, true, null)
    }

    @Test
    fun intDefaultChangeTest() {
        testValues(ExampleConfig::monstersKilled, null, 10, 0, 10, -19)
    }

    @Test
    fun longDefaultChangeTest() {
        testValues(ExampleConfig::numberOfHahaInLough, null, 10L, 0L, 100L)
    }

    @Test
    fun floatDefaultChangeTest() {
        testValues(ExampleConfig::experience, null, 10F, 0F, 100F)
    }

    @Test
    fun stringDefaultChangeTest() {
        testValues(ExampleConfig::className, null, "BlueberryPie", "SuperPie")
    }
}

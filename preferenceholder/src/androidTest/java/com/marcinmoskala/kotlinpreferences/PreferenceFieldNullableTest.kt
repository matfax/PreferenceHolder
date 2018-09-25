package com.marcinmoskala.kotlinpreferences

import androidx.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreferenceFieldNullableTest: BaseTest() {

    @Test
    fun booleanDefaultChangeTest() {
        testValues(TestPreferences::isMonsterKiller, null, true, false, true, null)
    }

    @Test
    fun intDefaultChangeTest() {
        testValues(TestPreferences::monstersKilled, null, 10, 0, 10, -19)
    }

    @Test
    fun longDefaultChangeTest() {
        testValues(TestPreferences::numberOfHahaInLough, null, 10L, 0L, 100L)
    }

    @Test
    fun floatDefaultChangeTest() {
        testValues(TestPreferences::experience, null, 10F, 0F, 100F)
    }

    @Test
    fun stringDefaultChangeTest() {
        testValues(TestPreferences::className, null, "BlueberryPie", "SuperPie")
    }
}

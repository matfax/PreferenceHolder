package com.marcinmoskala.kotlinpreferences

import android.preference.PreferenceManager
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CustomPreferencesTest {

    init {
        val customPrefs = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getTargetContext())
        PreferenceHolder.setPreferences(customPrefs)
        PreferenceHolder.serializer = null
        TestPreferences.clear()
    }

    @Test
    fun afterCleaningPropertiesAreSetToDefaultTest() {
        TestPreferences.canEatPie = false
        TestPreferences.pieBaked = 1000
        TestPreferences.allPieInTheWorld = 9090
        TestPreferences.pieEaten = 190F
        TestPreferences.bestPieName = "Marcin"
        TestPreferences.clear()
        Assert.assertEquals(true, TestPreferences.canEatPie)
        Assert.assertEquals(5, TestPreferences.pieBaked)
        Assert.assertEquals(-1L, TestPreferences.allPieInTheWorld)
        Assert.assertEquals(0.0F, TestPreferences.pieEaten)
        Assert.assertEquals("Pie", TestPreferences.bestPieName)
    }

    @Test
    fun afterCleaningNullablePropertiesAreSetToNullTest() {
        TestPreferences.isMonsterKiller = false
        TestPreferences.monstersKilled = 1000
        TestPreferences.numberOfHahaInLough = 9090
        TestPreferences.experience = 190F
        TestPreferences.className = "Marcin"
        TestPreferences.clear()
        Assert.assertEquals(null, TestPreferences.isMonsterKiller)
        Assert.assertEquals(null, TestPreferences.monstersKilled)
        Assert.assertEquals(null, TestPreferences.numberOfHahaInLough)
        Assert.assertEquals(null, TestPreferences.experience)
        Assert.assertEquals(null, TestPreferences.className)
    }

    @Test
    fun cleaningIsOnlyAppliedToSinglePrefClass() {
        TestPreferences.isMonsterKiller = false
        TestPreferences.monstersKilled = 1000
        Assert.assertEquals(false, TestPreferences.isMonsterKiller)
        Assert.assertEquals(1000, TestPreferences.monstersKilled)
    }
}

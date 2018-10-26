package com.marcinmoskala.kotlinpreferences

import android.preference.PreferenceManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CustomPreferencesTest : TestCase() {

    init {
        val customPrefs = PreferenceManager.getDefaultSharedPreferences(
                InstrumentationRegistry.getInstrumentation().targetContext
        )
        PreferenceHolder.setPreferences(customPrefs)
        ExampleConfig.clear()
    }

    @Test
    fun afterCleaningPropertiesAreSetToDefaultTest() {
        ExampleConfig.canEatPie = false
        ExampleConfig.pieBaked = 1000
        ExampleConfig.allPieInTheWorld = 9090
        ExampleConfig.pieEaten = 190F
        ExampleConfig.bestPieName = "Marcin"
        ExampleConfig.clear()
        Assert.assertEquals(true, ExampleConfig.canEatPie)
        Assert.assertEquals(5, ExampleConfig.pieBaked)
        Assert.assertEquals(-1L, ExampleConfig.allPieInTheWorld)
        Assert.assertEquals(0.0F, ExampleConfig.pieEaten)
        Assert.assertEquals("Pie", ExampleConfig.bestPieName)
    }

    @Test
    fun afterCleaningNullablePropertiesAreSetToNullTest() {
        ExampleConfig.isMonsterKiller = false
        ExampleConfig.monstersKilled = 1000
        ExampleConfig.numberOfHahaInLough = 9090
        ExampleConfig.experience = 190F
        ExampleConfig.className = "Marcin"
        ExampleConfig.clear()
        Assert.assertEquals(null, ExampleConfig.isMonsterKiller)
        Assert.assertEquals(null, ExampleConfig.monstersKilled)
        Assert.assertEquals(null, ExampleConfig.numberOfHahaInLough)
        Assert.assertEquals(null, ExampleConfig.experience)
        Assert.assertEquals(null, ExampleConfig.className)
    }

    @Test
    fun cleaningIsOnlyAppliedToSinglePrefClass() {
        ExampleConfig.isMonsterKiller = false
        ExampleConfig.monstersKilled = 1000
        Assert.assertEquals(false, ExampleConfig.isMonsterKiller)
        Assert.assertEquals(1000, ExampleConfig.monstersKilled)
    }
}

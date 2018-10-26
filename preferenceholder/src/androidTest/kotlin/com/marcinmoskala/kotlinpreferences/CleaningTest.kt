package com.marcinmoskala.kotlinpreferences

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marcinmoskala.kotlinpreferences.ExampleConfig.className
import com.marcinmoskala.kotlinpreferences.ExampleConfig.experience
import com.marcinmoskala.kotlinpreferences.ExampleConfig.isMonsterKiller
import com.marcinmoskala.kotlinpreferences.ExampleConfig.monstersKilled
import com.marcinmoskala.kotlinpreferences.ExampleConfig.numberOfHahaInLough
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CleaningTest : BaseTest() {

    @Test
    fun afterCleaningPropertiesAreSetToDefaultTest() {
        ExampleConfig.canEatPie = false
        ExampleConfig.pieBaked = 1000
        ExampleConfig.allPieInTheWorld = 9090
        ExampleConfig.pieEaten = 190F
        ExampleConfig.bestPieName = "Marcin"
        ExampleConfig.clear()
        assertEquals(true, ExampleConfig.canEatPie)
        assertEquals(5, ExampleConfig.pieBaked)
        assertEquals(-1L, ExampleConfig.allPieInTheWorld)
        assertEquals(0.0F, ExampleConfig.pieEaten)
        assertEquals("Pie", ExampleConfig.bestPieName)
    }

    @Test
    fun afterCleaningNullablePropertiesAreSetToNullTest() {
        ExampleConfig.isMonsterKiller = false
        ExampleConfig.monstersKilled = 1000
        ExampleConfig.numberOfHahaInLough = 9090
        ExampleConfig.experience = 190F
        ExampleConfig.className = "Marcin"
        ExampleConfig.clear()
        assertEquals(null, isMonsterKiller)
        assertEquals(null, monstersKilled)
        assertEquals(null, numberOfHahaInLough)
        assertEquals(null, experience)
        assertEquals(null, className)
    }

    @Test
    fun cleaningIsOnlyAppliedToSinglePrefClass() {
        ExampleConfig.isMonsterKiller = false
        ExampleConfig.monstersKilled = 1000
        assertEquals(false, isMonsterKiller)
        assertEquals(1000, monstersKilled)
    }
}

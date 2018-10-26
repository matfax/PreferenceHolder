package com.marcinmoskala.testapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.testapp.TestPreferences.canEatPie
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.reflect.KMutableProperty0

@RunWith(AndroidJUnit4::class)
abstract class DefaultChangeTest : TestCase() {

    @Before
    fun before() {
        PreferenceHolder.setContext(InstrumentationRegistry.getInstrumentation().targetContext)
        TestPreferences.clear()
    }

    @After
    fun after() {
        TestPreferences.clear()
    }

    @Test
    fun testBooleanDefaultChange() {
        testBoolean(TestPreferences::canEatPie)
    }

    @Test
    fun testBoolean(property: KMutableProperty0<Boolean>) {
        assertTrue(property.get())
        property.set(false)
        canEatPie = false
        assertTrue(!canEatPie)
        canEatPie = true
        assertTrue(canEatPie)
    }

    @Test
    fun testIntDefaultChange() {
        assertEquals(0, TestPreferences.pieBaked)
        TestPreferences.pieBaked = 10
        assertEquals(10, TestPreferences.pieBaked)
        TestPreferences.pieBaked += 10
        assertEquals(20, TestPreferences.pieBaked)
    }

    @Test
    fun testLongDefaultChange() {
        assertEquals(0L, TestPreferences.allPieInTheWorld)
        TestPreferences.allPieInTheWorld = 10
        assertEquals(10L, TestPreferences.allPieInTheWorld)
        TestPreferences.allPieInTheWorld += 10
        assertEquals(20L, TestPreferences.allPieInTheWorld)
    }

    @Test
    fun testFloatDefaultChange() {
        assertEquals(0F, TestPreferences.pieEaten)
        for (i in 1..10) TestPreferences.pieEaten += 1F
        assertEquals(10F, TestPreferences.pieEaten)
        TestPreferences.pieEaten *= 2
        assertEquals(20F, TestPreferences.pieEaten)
    }

    @Test
    fun testStringDefaultChange() {
        assertEquals("Pie", TestPreferences.bestPieName)
        TestPreferences.bestPieName = "BlueberryPie"
        assertEquals("BlueberryPie", TestPreferences.bestPieName)
        TestPreferences.bestPieName = "SuperPie"
        assertEquals("SuperPie", TestPreferences.bestPieName)
    }
}

package com.marcinmoskala.kotlinpreferences

import androidx.test.platform.app.InstrumentationRegistry
import com.marcinmoskala.kotlinpreferences.objects.ComplexTestPreferences
import junit.framework.TestCase
import org.junit.After
import org.junit.Before

abstract class BaseTest : TestCase() {

    @Before
    fun before() {
        PreferenceHolder.setContext(InstrumentationRegistry.getInstrumentation().targetContext)
        ComplexTestPreferences.clear()
        ExampleConfig.clear()
    }

    @After
    fun after() {
        ComplexTestPreferences.clear()
        ExampleConfig.clear()
    }
}

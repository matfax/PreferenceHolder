package com.marcinmoskala.kotlinpreferences

import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import org.junit.After
import org.junit.Before

abstract class BaseTest : TestCase() {

    @Before
    fun before() {
        PreferenceHolder.setContext(InstrumentationRegistry.getInstrumentation().targetContext)
        ExampleConfig.clear()
    }

    @After
    fun after() {
        ExampleConfig.clear()
    }
}

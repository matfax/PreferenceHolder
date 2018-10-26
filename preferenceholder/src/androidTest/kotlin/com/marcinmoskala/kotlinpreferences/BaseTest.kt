package com.marcinmoskala.kotlinpreferences

import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase

abstract class BaseTest : TestCase() {

    init {
        PreferenceHolder.setContext(InstrumentationRegistry.getInstrumentation().targetContext)
        ExampleConfig.clear()
    }
}

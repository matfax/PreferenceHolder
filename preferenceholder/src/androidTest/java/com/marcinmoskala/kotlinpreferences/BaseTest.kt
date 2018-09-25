package com.marcinmoskala.kotlinpreferences

import androidx.test.InstrumentationRegistry

open class BaseTest {

    init {
        PreferenceHolder.setContext(InstrumentationRegistry.getTargetContext())
        PreferenceHolder.serializer = null
        TestPreferences.clear()
    }
}
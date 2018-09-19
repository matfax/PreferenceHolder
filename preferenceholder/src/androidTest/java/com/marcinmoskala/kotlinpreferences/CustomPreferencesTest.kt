package com.marcinmoskala.kotlinpreferences

import android.preference.PreferenceManager
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CustomPreferencesTest {

    init {
        val customPrefs = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getTargetContext())
        PreferenceHolder.setPreferences(customPrefs)
        PreferenceHolder.serializer = null
        TestPreferences.clear()
    }
}

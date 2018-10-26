package com.marcinmoskala.kotlinpreferences.issue2test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Issue2Test {

    data class User(var name: String, var age: Int = 0)

    object AppPreference : PreferenceHolder() {
        var users: List<User>? by bindToPreferenceFieldNullable()
    }

    init {
        PreferenceHolder.setContext(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    fun booleanDefaultChangeTest() {
        val users = List(6) { i -> User("Name$i", i) }
        AppPreference.users = users
        AppPreference.clearCache()
        val readUsers = AppPreference.users
        Assert.assertEquals(users, readUsers)
    }
}

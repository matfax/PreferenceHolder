package com.marcinmoskala.kotlinpreferences.bindings

import android.content.SharedPreferences
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KProperty

internal abstract class PreferenceField<T : Any>(
        private val key: String?,
        private val getKey: (key: String?, property: KProperty<*>) -> String
) : Clearable, CoroutineScope {

    override val coroutineContext = Dispatchers.Main

    protected val pref by lazy { PreferenceHolder.getPreferences() }

    protected open val default: T? = null

    @Volatile
    var field: Deferred<T?>? = null

    init {
        pref.registerOnSharedPreferenceChangeListener { changedPref, changedKey ->
            field = async {
                refreshField(changedPref, changedKey)
            }
        }
    }

    protected abstract fun refreshField(changedPref: SharedPreferences, changedKey: String): T?

    override fun clearCache() {
        field = null
    }

    protected abstract fun saveNewValue(property: KProperty<*>, value: T)

    protected fun readValue(property: KProperty<*>): T? {
        field = if (field == null) {
            async { getFromPreference(property) }
        } else {
            field
        }
        return runBlocking { field?.await() }
    }

    protected abstract fun getFromPreference(property: KProperty<*>): T?

}

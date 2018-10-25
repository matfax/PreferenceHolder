package com.marcinmoskala.kotlinpreferences.bindings

import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.kotlinpreferences.PreferenceHolder.Companion.getPreferences
import com.marcinmoskala.kotlinpreferences.PreferenceHolder.Companion.testingMode
import java.lang.reflect.Type
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

internal open class PreferenceFieldBinder<T : Any>(
        private val clazz: KClass<T>,
        private val type: Type,
        private val default: T,
        private val key: String?,
        private val getKey: (key: String?, property: KProperty<*>) -> String
) : ReadWriteProperty<PreferenceHolder, T>, Clearable {

    override fun clear(thisRef: PreferenceHolder, property: KProperty<*>) {
        setValue(thisRef, property, default)
    }

    override fun clearCache() {
    }

    var field: T? = null

    override operator fun getValue(thisRef: PreferenceHolder, property: KProperty<*>): T = when {
        testingMode -> field ?: default
        else -> readValue(property)
    }

    override fun setValue(thisRef: PreferenceHolder, property: KProperty<*>, value: T) {
        if (testingMode) {
            if (value == field) return
            field = value
        } else {
            saveNewValue(property, value)
        }
    }

    protected open fun saveNewValue(property: KProperty<*>, value: T) {
        val pref = getPreferences()
        pref.edit().apply { putValue(clazz, value, getKey(key, property)) }.apply()
    }

    protected open fun readValue(property: KProperty<*>): T {
        val key = getKey(key, property)
        val pref = getPreferences()
        return if (!pref.contains(key)) {
            saveNewValue(property, default)
            default
        } else {
            pref.getFromPreference(clazz, type, default, key) as T
        }
    }
}

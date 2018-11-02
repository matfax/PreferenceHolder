package com.marcinmoskala.kotlinpreferences.bindings

import android.content.SharedPreferences
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.kotlinpreferences.PreferenceHolder.Companion.getPreferences
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

internal class PreferenceFieldBinder<T : Any>(
        private val clazz: KClass<T>,
        override val default: T,
        private val key: String?,
        private val getKey: (key: String?, property: KProperty<*>) -> String
) : PreferenceField<T>(key, getKey), ReadWriteProperty<PreferenceHolder, T> {

    override fun refreshField(changedPref: SharedPreferences, changedKey: String): T? {
        return changedPref.getFromPreference(clazz, changedKey, default)
    }

    override operator fun getValue(thisRef: PreferenceHolder, property: KProperty<*>): T {
        return readValue(property) ?: default
    }

    override fun setValue(thisRef: PreferenceHolder, property: KProperty<*>, value: T) {
        field = async { value }
        launch {
            saveNewValue(property, value)
        }
    }

    override fun clear(thisRef: PreferenceHolder, property: KProperty<*>) {
        setValue(thisRef, property, default)
    }

    override fun saveNewValue(property: KProperty<*>, value: T) {
        val propertyKey = getKey(key, property)
        val pref = getPreferences()
        pref.edit().apply { putValue(clazz, value, propertyKey) }.apply()
    }

    override fun getFromPreference(property: KProperty<*>): T? {
        val propertyKey = getKey(key, property)
        return if (pref.contains(propertyKey)) {
            pref.getFromPreference(clazz, propertyKey, default) as T
        } else {
            saveNewValue(property, default)
            default
        }
    }

}

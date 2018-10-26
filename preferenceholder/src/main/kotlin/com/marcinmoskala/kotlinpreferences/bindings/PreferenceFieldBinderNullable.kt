package com.marcinmoskala.kotlinpreferences.bindings

import android.content.SharedPreferences
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

internal class PreferenceFieldBinderNullable<T : Any>(
        private val clazz: KClass<T>,
        private val key: String?,
        private val getKey: (key: String?, property: KProperty<*>) -> String
) : PreferenceField<T>(key, getKey), ReadWriteProperty<PreferenceHolder, T?> {

    override operator fun getValue(thisRef: PreferenceHolder, property: KProperty<*>): T? {
        return readValue(property)
    }

    override fun setValue(thisRef: PreferenceHolder, property: KProperty<*>, value: T?) {
        field = async { value }
        launch {
            if (value == null) {
                removeValue(property)
            } else {
                saveNewValue(property, value)
            }
        }
    }

    override fun clear(thisRef: PreferenceHolder, property: KProperty<*>) {
        setValue(thisRef, property, default)
    }

    override fun saveNewValue(property: KProperty<*>, value: T) {
        val pref = PreferenceHolder.getPreferences()
        pref.edit().apply { putValue(clazz, value, getKey(key, property)) }.apply()
    }

    override fun refreshField(changedPref: SharedPreferences, changedKey: String): T? {
        return if (changedPref.contains(changedKey)) {
            changedPref.getFromPreference(clazz, changedKey)
        } else {
            null
        }
    }

    override fun getFromPreference(property: KProperty<*>): T {
        val key = getKey(key, property)
        return pref.getFromPreference(clazz, key) as T
    }

    private fun removeValue(property: KProperty<*>) {
        val propertyKey = getKey(key, property)
        pref.edit().remove(propertyKey).apply()
    }

}

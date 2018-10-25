package com.marcinmoskala.kotlinpreferences.bindings

import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.kotlinpreferences.PreferenceHolder.Companion.testingMode
import java.lang.reflect.Type
import kotlin.concurrent.thread
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

internal class PreferenceFieldBinderNullableCaching<T : Any>(
        clazz: KClass<T>,
        type: Type,
        key: String?,
        getKey: (key: String?, property: KProperty<*>) -> String
) : PreferenceFieldBinderNullable<T>(clazz, type, key, getKey) {

    override fun setValue(thisRef: PreferenceHolder, property: KProperty<*>, value: T?) {
        propertySet = true
        if (value == field) return
        field = value

        if (!testingMode)
            saveNewValue(property, value)
    }

    override fun saveNewValue(property: KProperty<*>, value: T?) {
        thread {
            if (value == null) {
                removeValue(property)
            } else {
                saveValue(property, value)
            }
        }
    }
}

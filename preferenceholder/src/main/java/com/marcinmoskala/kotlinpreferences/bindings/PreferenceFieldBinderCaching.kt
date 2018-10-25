package com.marcinmoskala.kotlinpreferences.bindings

import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.kotlinpreferences.PreferenceHolder.Companion.testingMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import java.lang.reflect.Type
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

internal class PreferenceFieldBinderCaching<T : Any>(
        clazz: KClass<T>,
        type: Type,
        default: T,
        key: String?,
        getKey: (key: String?, property: KProperty<*>) -> String
) : PreferenceFieldBinder<T>(clazz, type, default, key, getKey), CoroutineScope {
    override val coroutineContext = newCoroutineContext(EmptyCoroutineContext)

    override fun clearCache() {
        field = null
    }

    override fun setValue(thisRef: PreferenceHolder, property: KProperty<*>, value: T) {
        if (value == field) return
        field = value
        if (!testingMode) {
            launch {
                saveNewValue(property, value)
            }
        }
    }

    override fun saveNewValue(property: KProperty<*>, value: T) {
        launch {
            super.saveNewValue(property, value)
        }
    }

}

package com.marcinmoskala.kotlinpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.marcinmoskala.kotlinpreferences.bindings.Clearable
import com.marcinmoskala.kotlinpreferences.bindings.PreferenceFieldBinder
import com.marcinmoskala.kotlinpreferences.bindings.PreferenceFieldBinderNullable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

abstract class PreferenceHolder {

    protected inline fun <reified T : Any> bindToPreferenceField(
            default: T,
            key: String? = null
    ): ReadWriteProperty<PreferenceHolder, T> = bindToPreferenceField(
            T::class,
            default,
            key
    )

    protected inline fun <reified T : Any> bindToPreferenceFieldNullable(
            key: String? = null
    ): ReadWriteProperty<PreferenceHolder, T?> = bindToPreferenceFieldNullable(
            T::class,
            key
    )

    protected fun <T : Any> bindToPreferenceField(
            clazz: KClass<T>,
            default: T,
            key: String?
    ): ReadWriteProperty<PreferenceHolder, T> {
        return PreferenceFieldBinder(
                clazz,
                default,
                key,
                ::getKeyFromProperty
        )
    }

    protected fun <T : Any> bindToPreferenceFieldNullable(
            clazz: KClass<T>,
            key: String?
    ): ReadWriteProperty<PreferenceHolder, T?> {
        return PreferenceFieldBinderNullable(
                clazz,
                key,
                ::getKeyFromProperty
        )
    }

    /**
     * Determines the key name of the preference property.
     * @param key the key provided in the bind methods, otherwise null
     * @param property the property holder for the preference to process
     */
    private fun getKeyFromProperty(key: String?, property: KProperty<*>): String {
        return key ?: getKey(property.name)
    }

    /**
     * Determines the key name of the preference property.
     * @param propertyName the name of the property holder for the preference to process
     */
    open fun getKey(propertyName: String?): String {
        return "${this::class.simpleName
                ?: this.javaClass.enclosingClass?.simpleName}${propertyName?.capitalize()}".toSnakeCase()
    }

    /**
     * Converts a camelCase string to snake_case.
     */
    private fun String.toSnakeCase(): String {
        var text = ""
        var isFirst = true
        this.forEach {
            if (it.isUpperCase()) {
                if (isFirst) isFirst = false
                else text += "_"
                text += it.toLowerCase()
            } else {
                text += it
            }
        }
        return text
    }


    /**
     *  Function used to clear all SharedPreference and PreferenceHolder data. Useful especially
     *  during tests or when implementing Logout functionality.
     */
    fun clear() {
        forEachDelegate { delegate, property ->
            delegate.clear(this, property)
        }
    }

    fun clearCache() {
        forEachDelegate { delegate, _ ->
            delegate.clearCache()
        }
    }

    private fun forEachDelegate(f: (Clearable, KProperty<*>) -> Unit) {
        if (!isInitialized()) {
            return
        }
        val properties = this::class.declaredMemberProperties
                .filterIsInstance<KProperty1<SharedPreferences, *>>()
        for (p in properties) {
            val prevAccessible = p.isAccessible
            if (!prevAccessible) p.isAccessible = true
            val delegate = p.getDelegate(preferences!!)
            if (delegate is Clearable) f(delegate, p)
            p.isAccessible = prevAccessible
        }
    }

    companion object {

        /**
         *  It should be used to set ApplicationContext on project Application class. Only case when
         *  it could be committed is when testingMode is turned on.
         */
        fun setContext(context: Context) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context)
        }

        /**
         *  Integrate your own custom-built SharedPreferences object into the PreferenceHolder.
         */
        fun setPreferences(shared: SharedPreferences) {
            preferences = shared
        }

        /**
         *  Get the SharedPreference singleton or throw an error if it is uninitialized.
         */
        @Throws(Exception::class)
        fun getPreferences(): SharedPreferences {
            if (!isInitialized()) {
                throw Exception(NOT_INITIALIZED)
            }
            return preferences!!
        }

        /**
         * Whether the wrapped SharedPreferences are initialized.
         */
        fun isInitialized(): Boolean {
            return preferences != null
        }

        private const val NOT_INITIALIZED = "No preferences in PreferenceHolder instance. Add in Application class PreferenceHolder.setContext(applicationContext) or make PreferenceHolderApplication to be your project application class (android:name field in AndroidManifest)."

        @Volatile
        private var preferences: SharedPreferences? = null
    }
}

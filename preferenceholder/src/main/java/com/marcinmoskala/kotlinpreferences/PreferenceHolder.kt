package com.marcinmoskala.kotlinpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.marcinmoskala.kotlinpreferences.bindings.Clearable
import com.marcinmoskala.kotlinpreferences.bindings.PreferenceFieldBinder
import com.marcinmoskala.kotlinpreferences.bindings.PreferenceFieldBinderCaching
import com.marcinmoskala.kotlinpreferences.bindings.PreferenceFieldBinderNullable
import com.marcinmoskala.kotlinpreferences.bindings.PreferenceFieldBinderNullableCaching
import java.lang.reflect.Type
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

abstract class PreferenceHolder {

    protected inline fun <reified T : Any> bindToPreferenceField(default: T, key: String? = null, caching: Boolean = true): ReadWriteProperty<PreferenceHolder, T>
            = bindToPreferenceField(T::class, object : TypeToken<T>() {}.type, default, key, caching)

    protected inline fun <reified T : Any> bindToPreferenceFieldNullable(key: String? = null, caching: Boolean = true): ReadWriteProperty<PreferenceHolder, T?>
            = bindToPreferenceFieldNullable(T::class, object : TypeToken<T>() {}.type, key, caching)

    protected fun <T : Any> bindToPreferenceField(clazz: KClass<T>, type: Type, default: T, key: String?, caching: Boolean = true): ReadWriteProperty<PreferenceHolder, T> = if (caching) PreferenceFieldBinderCaching(clazz, type, default, key, ::getKey) else PreferenceFieldBinder(clazz, type, default, key, ::getKey)

    protected fun <T : Any> bindToPreferenceFieldNullable(clazz: KClass<T>, type: Type, key: String?, caching: Boolean = true): ReadWriteProperty<PreferenceHolder, T?> = if (caching) PreferenceFieldBinderNullableCaching(clazz, type, key, ::getKey) else PreferenceFieldBinderNullable(clazz, type, key, ::getKey)

    /**
     * Determines the key name of the preference property.
     * @param key the key provided in the bind methods, otherwise null
     * @param property the property holder for the preference to process
     */
    open fun getKey(key: String?, property: KProperty<*>): String {
        return key ?: "${this::class.simpleName
                ?: this.javaClass.enclosingClass}${property.name.capitalize()}".toSnakeCase()
    }

    /**
     * Converts a camelCase string to snake_case.
     */
    fun String.toSnakeCase(): String {
        var text: String = ""
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
         *  This property should be used to set serializer to use types that are not supported by SharedPreference.
         */
        var serializer: Serializer? = null

        /**
         *  When testing mode is turned on, then there is no need to provide Context, and all bindings
         *  are acting just like standard Kotln fields. If we then need to mock some situation then all
         *  we need to do is to set values on Preferences. Example:
         *
         *  fun newUserCreationTest() {
         *      PreferenceHolder.testingMode = true
         *      UserPreferences.user = null
         *      val mockedView = object : UserView {
         *         fun getName() = "Marcin"
         *         gun getSurname() = "Moskala"
         *      }
         *      val presenter = UserPresenter(mockedView)
         *      presenter.createUser()
         *      assert(User("Marcin", "Moskala"), UserPreferences.user)
         *  }
         */
        var testingMode: Boolean = false

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

# async-preference-holder

Kotlin Android Library, that makes preference usage simple and fun.

[![](https://jitpack.io/v/matfax/async-preference-holder.svg)](https://jitpack.io/#matfax/async-preference-holder)
[![Build Status](https://travis-ci.com/matfax/async-preference-holder.svg?branch=master)](https://travis-ci.com/matfax/async-preference-holder)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/103fbbf09fc04511a12e237e3c51b1d4)](https://www.codacy.com/app/matfax/async-preference-holder?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=matfax/async-preference-holder&amp;utm_campaign=Badge_Grade)
![GitHub License](https://img.shields.io/github/license/matfax/async-preference-holder.svg)
![GitHub last commit](https://img.shields.io/github/last-commit/matfax/async-preference-holder.svg)
![Libraries.io for GitHub](https://img.shields.io/librariesio/github/matfax/async-preference-holder.svg)
[![Dependabot Status](https://api.dependabot.com/badges/status?host=github&repo=matfax/async-preference-holder)](https://dependabot.com)


This library is younger brother of [PreferenceHolder](https://github.com/MarcinMoskala/PreferenceHolder).

## Where is the difference to the upstream brother `PreferenceHolder`?

- This fork enables asynchronous access to the PreferenceHolder. Caching provides immediate access to the preferences.
- It uses kotlinx serialization instead of Gson since it has become more and more adult.
- It allows you to use your own `SharedPreferences` implemenatation, not just the default one provided by the given context.
- It uses Kotlin 1.3.
- The testing mode has been dropped.
- Caching has become obligatory.

## How to use

With async-preference-holder, you can define different preference fields this way:

```kotlin
object Pref: async-preference-holder() {
    var canEatPie: Boolean by bindToPreferenceField(true)
}
```

And use it this way:

```kotlin
if(Pref.canEatPie) //...
```

Here are other preference definition examples: (see [full example](https://github.com/MarcinMoskala/async-preference-holder/blob/master/kotlinpreferences-lib/src/androidTest/java/com/marcinmoskala/kotlinpreferences/ExampleConfig.kt) and [usage](https://github.com/MarcinMoskala/async-preference-holder/tree/master/kotlinpreferences-lib/src/androidTest/java/com/marcinmoskala/kotlinpreferences))

```kotlin
object UserPref: async-preference-holder() {
    var canEatPie: Boolean by bindToPreferenceField(true)
    var allPieInTheWorld: Long by bindToPreferenceField(0)

    var isMonsterKiller: Boolean? by bindToPreferenceFieldNullable()
    var monstersKilled: Int? by bindToPreferenceFieldNullable()
    
    // Property with backup is reading stored value in the first usage, 
    // and saving it, in background, each time it is changed.
    var experience: Float? by bindToPropertyWithBackup(-1.0F) 
    var className: String? by bindToPropertyWithBackupNullable()

    // Any type can used if serializer is set. See: Gson serialization
    var character: Character? by bindToPreferenceFieldNullable()
    var savedGame: Game? by bindToPreferenceFieldNullable()
}
```

There must be application Context added to async-preference-holder companion object. Example:

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        async-preference-holder.setContext(applicationContext)
    }
}
```

It it suggested to do it in project Application class. As an alternative, async-preference-holderApplication can also be added as a name of an application in AndroidManifest: ([example](https://github.com/MarcinMoskala/async-preference-holder/blob/master/sample/src/main/AndroidManifest.xml#L12))

```
android:name="com.marcinmoskala.kotlinpreferences.async-preference-holderApplication"
```

## Install

To add async-preference-holder to your project, add the dependency to your Gradle config.

```groovy
repositories {
    maven { url "https://kotlin.bintray.com/kotlinx" }
    maven { url 'https://jitpack.io' }
}
dependencies {
    implementation 'com.github.matfax:async-preference-holder:1.53'
}
```

## Kotlinx Serialization

To use the serializer, we need to enable the following plugin:

```groovy
buildscript {
    repositories {
        maven { url "https://kotlin.bintray.com/kotlinx" }
    }
}
apply plugin: 'kotlinx-serialization'
```

LIMITATION: Serialization is still limited as described in [kotlinx-serialization](https://github.com/Kotlin/kotlinx.serialization).

## Other libraries

If you like it, remember to leave the star and check out my other libraries:
 * [ActivityStarter](https://github.com/MarcinMoskala/ActivityStarter/blob/master/README.md) - Simple Android Library, that provides easy way to start and save state of Activities, Fragments, Services and Receivers with arguments.
 * [ArcSeekBar](https://github.com/MarcinMoskala/ArcSeekBar) - Good looking curved Android SeekBar
 * [VideoPlayView](https://github.com/MarcinMoskala/VideoPlayView) - Custom Android view with video player, loader and placeholder image
 * [KotlinAndroidViewBindings](https://github.com/MarcinMoskala/KotlinAndroidViewBindings) - Bindings for properties with simple Kotlin types (Boolean, String) to layout traits (visibility, text).

License
-------

    Copyright 2018 matfax
    Copyright 2017 Marcin Moskała

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


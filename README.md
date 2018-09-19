# PreferenceHolder
Kotlin Android Library, that makes preference usage simple and fun.

[![](https://jitpack.io/v/matfax/PreferenceHolder.svg)](https://jitpack.io/#matfax/PreferenceHolder)
[![Build Status](https://travis-ci.com/matfax/PreferenceHolder.svg?branch=master)](https://travis-ci.com/matfax/PreferenceHolder)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/103fbbf09fc04511a12e237e3c51b1d4)](https://www.codacy.com/app/matfax/PreferenceHolder?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=matfax/PreferenceHolder&amp;utm_campaign=Badge_Grade)
![GitHub License](https://img.shields.io/github/license/matfax/PreferenceHolder.svg)
![GitHub last commit](https://img.shields.io/github/last-commit/matfax/PreferenceHolder.svg)

To stay up-to-date with news about library [![Twitter URL](https://img.shields.io/twitter/url/https/twitter.com/fold_left.svg?style=social&label=Follow%20%40marcinmoskala)](https://twitter.com/marcinmoskala?ref_src=twsrc%5Etfw)

This library is younger brother of [KotlinPreferences](https://github.com/MarcinMoskala/KotlinPreferences).

With PreferenceHolder, you can define different preference fields this way:

```kotlin
object Pref: PreferenceHolder() {
    var canEatPie: Boolean by bindToPreferenceField(true)
}
```

And use it this way:

```kotlin
if(Pref.canEatPie) //...
```

Here are other preference definition examples: (see [full example](https://github.com/MarcinMoskala/PreferenceHolder/blob/master/kotlinpreferences-lib/src/androidTest/java/com/marcinmoskala/kotlinpreferences/ExampleConfig.kt) and [usage](https://github.com/MarcinMoskala/PreferenceHolder/tree/master/kotlinpreferences-lib/src/androidTest/java/com/marcinmoskala/kotlinpreferences))

```kotlin
object UserPref: PreferenceHolder() {
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

    // Single level collections are also supported if serializer is set. See: Gson serialization
    var longList: Map<Int, Long> by bindToPreferenceField(mapOf(0 to 12L, 10 to 143L))
    var propTest: List<Character>? by bindToPropertyWithBackupNullable()
    var elemTest: Set<Elems> by bindToPreferenceField(setOf(Elems.Elem1, Elems.Elem3))
}
```

There must be application Context added to PreferenceHolder companion object. Example:

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceHolder.setContext(applicationContext)
    }
}
```

It it suggested to do it in project Application class. As an alternative, PreferenceHolderApplication can also be added as a name of an application in AndroidManifest: ([example](https://github.com/MarcinMoskala/PreferenceHolder/blob/master/sample/src/main/AndroidManifest.xml#L12))

```
android:name="com.marcinmoskala.kotlinpreferences.PreferenceHolderApplication"
```

## Unit testing components

Library also include test mode:

```
PreferenceHolder.testingMode = true
```

When it is turned on, then all properties are acting just like normal properties without binding to preference field. This allows to make unit tests to presenters and to use cases that are using instance of PreferenceHolder.

## Install

To add PreferenceHolder to the project, add to build.gradle file:

```groovy
dependencies {
    compile "com.marcinmoskala.PreferenceHolder:preferenceholder:1.51"
}
```

While library is located on JitPack, remember to add to module build.gradle (unless you already have it):

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

## Gson serialization

To use Gson serializer, we need to add following dependency:

```groovy
dependencies {
    compile "com.marcinmoskala.PreferenceHolder:preferenceholder-gson-serializer:1.51"
}
```

And specify `GsonSerializer` as `PreferenceHolder` serializer: 

```kotlin
PreferenceHolder.serializer = GsonSerializer(Gson())
```

Since then, we can use all types, even one not supported by SharedPreference (like custom objects `Character` and `Game`, or collections)

## Other libraries

If you like it, remember to leave the star and check out my other libraries:
 * [ActivityStarter](https://github.com/MarcinMoskala/ActivityStarter/blob/master/README.md) - Simple Android Library, that provides easy way to start and save state of Activities, Fragments, Services and Receivers with arguments.
 * [ArcSeekBar](https://github.com/MarcinMoskala/ArcSeekBar) - Good looking curved Android SeekBar
 * [VideoPlayView](https://github.com/MarcinMoskala/VideoPlayView) - Custom Android view with video player, loader and placeholder image
 * [KotlinAndroidViewBindings](https://github.com/MarcinMoskala/KotlinAndroidViewBindings) - Bindings for properties with simple Kotlin types (Boolean, String) to layout traits (visibility, text).

License
-------

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


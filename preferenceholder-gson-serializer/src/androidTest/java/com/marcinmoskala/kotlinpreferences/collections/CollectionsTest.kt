package com.marcinmoskala.kotlinpreferences.collections

import androidx.test.runner.AndroidJUnit4
import com.marcinmoskala.kotlinpreferences.GsonBaseTest
import com.marcinmoskala.kotlinpreferences.objects.Character
import com.marcinmoskala.kotlinpreferences.testValues
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionsTest : GsonBaseTest() {

    @Test
    fun intListTest() {
        testValues(CollectionsConfig::intList, null, listOf(1, 2, 3, 4), listOf(4, 3, 2, 1), listOf())
    }

    @Test
    fun stringListTest() {
        testValues(
                CollectionsConfig::stringList,
                listOf(),
                listOf("Test1", "Test2", "Test3"),
                listOf(),
                listOf("Test3"),
                listOf("Test1", "Test2")
        )
    }

    @Test
    fun longMapTest() {
        testValues(
                CollectionsConfig::longList,
                mapOf(0 to 12L, 10 to 143L),
                mapOf(100 to 1000L),
                mapOf(),
                mapOf(0 to 1L, 1 to 2L, 2 to 3L)
        )
    }

    @Test
    fun complexListTest() {
        val character1 = Character("Marcin", "Human", "Wizard")
        val character2 = Character("SuperMarcin", "SuperHuman", "SuperWizard")
        testValues(CollectionsConfig::propTest, null, listOf(character1), listOf(character1, character2), listOf(character1))
    }

    @Test
    fun enumSetChangeTest() {
        testValues(
                CollectionsConfig::elemTest,
                setOf(
                        CollectionsConfig.Elems.Elem1,
                        CollectionsConfig.Elems.Elem3
                ),
                setOf(),
                setOf(CollectionsConfig.Elems.Elem4, CollectionsConfig.Elems.Elem3),
                setOf(
                        CollectionsConfig.Elems.Elem1,
                        CollectionsConfig.Elems.Elem2,
                        CollectionsConfig.Elems.Elem3,
                        CollectionsConfig.Elems.Elem4,
                        CollectionsConfig.Elems.Elem5
                ),
                setOf()
        )
    }
}

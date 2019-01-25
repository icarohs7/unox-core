package com.github.icarohs7.unoxandroid.delegates

import com.snakydesign.livedataextensions.liveDataOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
class LiveDataDelegateTest {
    @Test
    fun `should delegate get operations on livedata`() {
        runBlocking {
            //Given
            val d1 by Eg.ld1
            val d2 by Eg.ld2
            val d3 by Eg.ld3

            delay(1)

            //Then
            d1 shouldEqual 5
            d2 shouldEqual "hi"
            d3 shouldEqual mapOf("name" to "Icaro", "country" to "Brazil")
        }
    }

    @Test
    fun `should delegate set operations on livedata`() {
        runBlocking {
            //Given
            var d1: Int? by Eg.ld1
            var d2: String? by Eg.ld2
            var d3: Map<String, String>? by Eg.ld3

            //When
            d1 = 1532
            d2 = "omai wa mou shindeiru"
            d3 = mapOf("name" to "Bolsonaro")

            delay(1)

            //Then
            Eg.ld1.value shouldEqual d1
            Eg.ld1.value shouldEqual 1532
            Eg.ld2.value shouldEqual d2
            Eg.ld2.value shouldEqual "omai wa mou shindeiru"
            Eg.ld3.value shouldEqual d3
            Eg.ld3.value shouldEqual mapOf("name" to "Bolsonaro")
        }
    }

    @Test
    fun `should delegate get operations on livedata in a nullsafe way`() {
        runBlocking {
            //Given
            val d1 by Eg.ld1.nonNullDelegate(0)
            val d2 by Eg.ld2.nonNullDelegate("")
            val d3 by Eg.ld3.nonNullDelegate(emptyMap())

            //When
            Eg.ld1.value = null
            Eg.ld2.value = null
            Eg.ld3.value = null

            delay(1)

            //Then
            Eg.ld1.value shouldEqual null
            Eg.ld2.value shouldEqual null
            Eg.ld3.value shouldEqual null
            d1 shouldEqual 0
            d2 shouldEqual ""
            d3 shouldEqual emptyMap()
        }
    }

    @Test
    fun `should delegate set operations on livedata in a nullsafe way`() {
        runBlocking {
            //Given
            var d1: Int by Eg.ld1.nonNullDelegate(0)
            var d2: String by Eg.ld2.nonNullDelegate("")
            var d3: Map<String, String> by Eg.ld3.nonNullDelegate(emptyMap())

            //When
            d1 = 1532
            d2 = "omai wa mou shindeiru"
            d3 = mapOf("name" to "Bolsonaro")

            delay(1)

            //Then
            Eg.ld1.value shouldEqual d1
            Eg.ld1.value shouldEqual 1532
            Eg.ld2.value shouldEqual d2
            Eg.ld2.value shouldEqual "omai wa mou shindeiru"
            Eg.ld3.value shouldEqual d3
            Eg.ld3.value shouldEqual mapOf("name" to "Bolsonaro")
        }
    }

    @Before
    fun setUp() {
        Eg.ld1.value = 5
        Eg.ld2.value = "hi"
        Eg.ld3.value = mapOf("name" to "Icaro", "country" to "Brazil")
    }

    private object Eg {
        val ld1 = liveDataOf(5)
        val ld2 = liveDataOf("hi")
        val ld3 = liveDataOf(mapOf("name" to "Icaro", "country" to "Brazil"))
    }
}
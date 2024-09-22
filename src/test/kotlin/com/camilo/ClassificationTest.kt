package com.camilo

import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ClassificationTest {
    @Test
    fun `it should return a classification map`(): Unit = runBlocking {
        val input = mapOf("Descrição" to "Google *youtube Member")
        val output = Classification(mapOf("Descrição" to "My Description")).run(input)
        output.shouldBe(mapOf("My Description" to "Google *youtube Member"))
    }

    @Test
    fun `it should return a empty map when classification does not match`(): Unit {
        val input = mapOf("Descrição" to "Google *youtube Member")
        val output = Classification(mapOf("OTHER FIELD" to "My Description")).run(input)
        output.shouldBe(mapOf("" to ""))
    }
}
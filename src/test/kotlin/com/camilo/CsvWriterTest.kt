package com.camilo

import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.equals.shouldBeEqual
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class CsvWriterTest {

    @Test
    fun `it should write a file when receive a flow of list of map`(): Unit = runBlocking {
        val inputLinesFlow = flowOf(
            listOf(mapOf("Data Operation" to "23-09-2024"), mapOf("Descrição" to "Transferência para Nr. XXXXX2897")),
            listOf(mapOf("Data Operation" to "20-09-2024"), mapOf("Descrição" to "Google *youtube Member")),
        )
        val outputFilePath = CsvWriter().writeData("./src/test/resources/output.file.csv", inputLinesFlow)

        val lines = outputFilePath.shouldBeRight().readLines()
        lines.shouldNotBeEmpty()
        lines shouldBeEqual listOf(
            "Data Operation;Descrição",
            "23-09-2024;Transferência para Nr. XXXXX2897",
            "20-09-2024;Google *youtube Member",
        )

    }
}
package com.camilo

import com.camilo.CsvReaderError.FileNotFound
import com.camilo.CsvReaderError.FilePathIsEmpty
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.common.runBlocking
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Test


class CSVReaderTest {

    @Test
    fun `it should read a csv file based on path`(): Unit = runBlocking {
        val path = "input.transaction.csv".getPathAsString()
        val csvFile = CsvReader().read(path)
        val toList = csvFile.shouldBeRight().toList()

        toList
            .shouldHaveSize(3)
            .shouldBeInstanceOf<List<List<Map<String, String>>>>()

        csvFile.shouldBeRight().toList().apply {
            this shouldHaveSize 3
            this.shouldBeInstanceOf<List<List<Map<String, String>>>>()
        }
    }

    @Test
    fun `it should return an error when input path is empty`(): Unit = runBlocking {
        val path = ""
        val csvFile = CsvReader().read(path)
        csvFile.shouldBeLeft().shouldBeInstanceOf<FilePathIsEmpty>()
    }


    @Test
    fun `it should return an error when file does not exists `(): Unit = runBlocking {
        val path = "this.file.does.not.exists"
        val csvFile = CsvReader().read(path)
        csvFile.shouldBeLeft().shouldBeInstanceOf<FileNotFound>()
    }
}
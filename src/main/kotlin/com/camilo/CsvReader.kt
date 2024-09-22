package com.camilo

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.sequences.forEach


sealed class CsvReaderError(override val message: String) : RuntimeException(message) {

    object FilePathIsEmpty : CsvReaderError("FILE_PATH_COULD_NOT_BE_EMPTY") {
        private fun readResolve(): Any = FileNotFound
    }

    object FileNotFound : CsvReaderError("FILE_NOT_FOUND") {
        private fun readResolve(): Any = FileNotFound
    }
}

class CsvReader(private val delimiter: String = ";") : SourceDataGateway {

    private val log = LoggerFactory.getLogger(CsvReader::class.java)

    override suspend fun read(filePath: String): Either<CsvReaderError, Flow<List<Map<String, String>>>> = either {
        ensure(filePath.isNotBlank().or(filePath.isNotEmpty())) { CsvReaderError.FilePathIsEmpty }
        val file = File(filePath)
        val exists = file.exists()
        ensure(exists) { CsvReaderError.FileNotFound }

        val firstLine = file.bufferedReader().use { it.readLine() }
        val headers = firstLine.split(delimiter)
        val dropHeader = 1


        flow { file.bufferedReader().useLines { lines -> lines.forEach { emit(it) } } }
            .drop(dropHeader)
            .map { line -> listOf(headers.zip(line.split(delimiter)).toMap()) }
    }
        .onLeft { log.error("Something went wrong while reading file $filePath", it) }

}


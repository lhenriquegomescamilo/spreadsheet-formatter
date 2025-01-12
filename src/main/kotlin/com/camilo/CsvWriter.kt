package com.camilo

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.camilo.ProcessExpensesError.FailedWhileWriteFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import mu.KotlinLogging
import java.io.File


class CsvWriter(private val delimiter: String = ";") : OutputDataGateway<ProcessExpensesError, File> {

    private val log = KotlinLogging.logger { }

    override suspend fun writeData(
        filePath: String,
        dataFlow: Flow<List<Map<String, String>>>
    ): Either<ProcessExpensesError, File> {
        return try {
            val fileToWrite = File(filePath)
            fileToWrite.bufferedWriter().use { writer ->
                val header = dataFlow.first()
                    .flatMap { row -> row.entries.map { (key) -> key } }
                    .joinToString(delimiter)

                writer.write(header)
                dataFlow
                    .map { data ->
                        data.flatMap { row -> row.entries.map { (_, value) -> value } }.joinToString(delimiter)
                    }
                    .onEach { writer.newLine() }
                    .collect { line -> writer.write(line) }
            }
            fileToWrite.right()
        } catch (exception: Exception) {
            log.error(exception) { "While try to process file $filePath" }
            FailedWhileWriteFile.left()
        }

    }
}

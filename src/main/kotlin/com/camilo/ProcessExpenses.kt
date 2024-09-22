package com.camilo

import arrow.core.raise.either
import com.camilo.ProcessExpensesError.SourceDataFiled
import org.slf4j.LoggerFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File

sealed class ProcessExpensesError(message: String) : RuntimeException(message) {
    data object SourceDataFiled : ProcessExpensesError(message = "FAILED_TO_READ_SOURCE_DATA") {
        private fun readResolve(): Any = SourceDataFiled
    }

    data object FailedWhileWriteFile : ProcessExpensesError("FAILED_WHILE_TRY_WRITE_FILE") {
        private fun readResolve(): Any = FailedWhileWriteFile
    }
}

class ProcessExpenses(
    private val classification: ClassificationGateway,
    private val sourceData: SourceDataGateway,
    private val outputDataGateway: OutputDataGateway<ProcessExpensesError, File>
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    suspend fun execute(inputFilePath: String, outputFilePath: String) = either<ProcessExpensesError, File> {
        val rowAsFlow = sourceData.read(inputFilePath)
            .onLeft { log.error("Failed to source of data $inputFilePath", it) }
            .mapLeft { SourceDataFiled }
            .bind()

        val rowClassifiedAsFlow = rowAsFlow
            .map { line -> line.map { classification.run(it) } }

        outputDataGateway.writeData(outputFilePath, rowClassifiedAsFlow).bind()
    }
}
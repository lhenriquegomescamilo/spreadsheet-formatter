package com.camilo

import arrow.core.Either
import kotlinx.coroutines.flow.Flow

fun interface SourceDataGateway {
    suspend fun read(filePath: String): Either<CsvReaderError, Flow<List<Map<String, String>>>>
}
package com.camilo

import arrow.core.Either
import kotlinx.coroutines.flow.Flow
import java.io.File

fun interface OutputDataGateway<E, O>  {

    suspend fun writeData(filePath: String, dataFlow: Flow<List<Map<String, String>>>): Either<E, O>
}
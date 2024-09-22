package com.camilo

import com.camilo.CreateExpenseMapperUseCaseErrors.InputDataIsEmpty
import mu.KotlinLogging

sealed class CreateExpenseMapperUseCaseErrors(override val message: String) : RuntimeException(message) {
    data object InputDataIsEmpty : CreateExpenseMapperUseCaseErrors("Input Date is empty") {
        private fun readResolve(): Any = InputDataIsEmpty
    }
}


class Classification(private val mapper: Map<String, String>) : ClassificationGateway {

    private val logger = KotlinLogging.logger { }

    init {
        logger.info { "Initializing classification with mapper $mapper" }
    }

    override fun run(cell: Map<String, String>): Map<String, String> {
        return mapper.map { (key, value) ->
            if (cell.containsKey(key)) value to cell.getValue(key)
            else {
                logger.warn { "The key $key does not exist in cell $cell" }
                "" to ""
            }
        }.toMap()

    }

}
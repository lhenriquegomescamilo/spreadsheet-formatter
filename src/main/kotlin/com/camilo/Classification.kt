package com.camilo

import com.camilo.CreateExpenseMapperUseCaseErrors.InputDataIsEmpty

sealed class CreateExpenseMapperUseCaseErrors(override val message: String) : RuntimeException(message) {
    data object InputDataIsEmpty : CreateExpenseMapperUseCaseErrors("Input Date is empty") {
        private fun readResolve(): Any = InputDataIsEmpty
    }
}


class Classification(private val mapper: Map<String, String>): ClassificationGateway {
    override fun run(cell: Map<String, String>): Map<String, String> {
        return mapper.map { (key, value) ->
            if (cell.containsKey(key)) value to cell.getValue(key)
            else "" to ""
        }.toMap()

    }

}
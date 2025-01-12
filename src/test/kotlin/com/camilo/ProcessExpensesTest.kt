package com.camilo

import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.common.runBlocking
import io.kotest.matchers.equals.shouldBeEqual
import kotlin.test.Test

class ProcessExpensesTest {

    @Test
    fun `it should generate output file based on mapper`(): Unit = runBlocking {
        val path = "input.transaction.csv".getPathAsString()
        val classification = Classification(
            mapper = mapOf(
                "Montante( EUR )" to "Valor",
                "Descrição" to "Observações",
                "Saldo Contabilístico( EUR )" to "Saldo",
                "Data valor" to "Data Pagamento",
            )
        )
        val output = ProcessExpenses(
            classification = classification,
            sourceData = CsvReader(),
            outputDataGateway = CsvWriter()
        )
            .execute(inputFilePath = path, outputFilePath = "./src/test/resources/output.process.expense.csv")

        val lines = output.shouldBeRight().readLines()
        lines shouldBeEqual listOf(
            "Valor;Observações;Saldo;Data Pagamento",
            "16.00;Transferência para Nr. XXXXX2897;82.00;23-09-2024",
            "7.99;Google *youtube Member;90.00;20-09-2024",
            "10;Pagamento, S.a.;100;18-09-2024"
        )
    }
}

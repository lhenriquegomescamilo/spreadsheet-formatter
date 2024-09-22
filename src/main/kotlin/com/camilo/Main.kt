package com.camilo

import kotlinx.coroutines.runBlocking
import mu.KotlinLogging

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val logger = KotlinLogging.logger { }
            val inputPath = args.firstOrNull()
            val outputFile = args.lastOrNull()

            if (inputPath == null) {
                logger.error { "Input file not defined or not found $inputPath" }
                return
            }

            if (outputFile == null) {
                logger.error { "Output file not defined $outputFile" }
                return
            }
            val process = ProcessExpenses(
                sourceData = CsvReader(),
                outputDataGateway = CsvWriter(),
                classification = Classification(
                    mapper = mapOf(
                        "Montante( EUR )" to "Valor",
                        "Descrição" to "Observações",
                        "Saldo Contabilístico( EUR )" to "Saldo",
                        "Data valor" to "Data Pagamento",
                    )
                )
            )

            runBlocking {
                process.execute(inputPath, outputFile)
            }
        }
    }
}

package com.camilo

fun interface ClassificationGateway {
    fun run(cell: Map<String, String>): Map<String, String>
}